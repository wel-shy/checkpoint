package uk.ac.ncl.djwelsh.checkpoint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.util.Pair;

import com.androidplot.util.ValPixConverter;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.LineAndPointRenderer;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.PointLabeler;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.ArrayList;

/**
 * Created by Daniel on 17/04/16.
 *
 * http://stackoverflow.com/questions/24283978/androidplot-customize-joints
 */
public class MyLineAndPointRenderer extends LineAndPointRenderer<MyLineAndPointFormatter>
{

    public MyLineAndPointRenderer(XYPlot plot) {
        super(plot);
    }

    /**
     * Overridden draw method to get the "vertex stroke" effect.  99% of this is copy/pasted from
     * the super class' implementation.
     * @param canvas
     * @param plotArea
     * @param series
     * @param formatter
     */
    @Override
    protected void drawSeries(Canvas canvas, RectF plotArea, XYSeries series, LineAndPointFormatter formatter) {
        PointF thisPoint;
        PointF lastPoint = null;
        PointF firstPoint = null;
        Paint  linePaint = formatter.getLinePaint();

        Path path = null;
        ArrayList<Pair<PointF, Integer>> points = new ArrayList<Pair<PointF, Integer>>(series.size());
        for (int i = 0; i < series.size(); i++) {
            Number y = series.getY(i);
            Number x = series.getX(i);

            if (y != null && x != null) {
                thisPoint = ValPixConverter.valToPix(
                        x,
                        y,
                        plotArea,
                        getPlot().getCalculatedMinX(),
                        getPlot().getCalculatedMaxX(),
                        getPlot().getCalculatedMinY(),
                        getPlot().getCalculatedMaxY());
                points.add(new Pair<PointF, Integer>(thisPoint, i));
            } else {
                thisPoint = null;
            }

            if(linePaint != null && thisPoint != null) {

                // record the first point of the new Path
                if(firstPoint == null) {
                    path = new Path();
                    firstPoint = thisPoint;
                    // create our first point at the bottom/x position so filling
                    // will look good
                    path.moveTo(firstPoint.x, firstPoint.y);
                }

                if(lastPoint != null) {
                    appendToPath(path, thisPoint, lastPoint);
                }

                lastPoint = thisPoint;
            } else {
                if(lastPoint != null) {
                    renderPath(canvas, plotArea, path, firstPoint, lastPoint, formatter);
                }
                firstPoint = null;
                lastPoint = null;
            }
        }
        if(linePaint != null && firstPoint != null) {
            renderPath(canvas, plotArea, path, firstPoint, lastPoint, formatter);
        }

        Paint vertexPaint = formatter.getVertexPaint();
        Paint strokePaint = ((MyLineAndPointFormatter)formatter).getStrokePaint();
        PointLabelFormatter plf = formatter.getPointLabelFormatter();
        if (vertexPaint != null || plf != null) {
            for (Pair<PointF, Integer> p : points) {
                PointLabeler pointLabeler = formatter.getPointLabeler();

                // if vertexPaint is available, draw vertex:
                if(vertexPaint != null) {
                    canvas.drawPoint(p.first.x, p.first.y, vertexPaint);
                }

                // if stroke is available, draw stroke:
                if(strokePaint != null) {
                    // you'll probably want to make the radius a configurable parameter
                    // instead of hard-coded like it is here.
                    canvas.drawCircle(p.first.x, p.first.y, 10, strokePaint);
                }

                // if textPaint and pointLabeler are available, draw point's text label:
                if(plf != null && pointLabeler != null) {
                    canvas.drawText(pointLabeler.getLabel(series, p.second), p.first.x + plf.hOffset, p.first.y + plf.vOffset, plf.getTextPaint());
                }
            }
        }
    }
}
