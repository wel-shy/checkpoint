package uk.ac.ncl.djwelsh.checkpoint;

import android.graphics.Color;
import android.graphics.Paint;

import com.androidplot.ui.SeriesRenderer;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.XYPlot;

/**
 * Created by Daniel on 17/04/16.
 *
 * Taken from stack overflow. This class helps format the graph showing quiz results.
 */
public class MyLineAndPointFormatter extends LineAndPointFormatter {

    private Paint strokePaint;


    public MyLineAndPointFormatter() {
        // Set line and marker colours.
        super(Color.WHITE, Color.WHITE, null, null);
        strokePaint = new Paint();
        strokePaint.setColor(Color.parseColor("#D1345B"));
        strokePaint.setStrokeWidth(PixelUtils.dpToPix(2));
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setAntiAlias(true);
    }

    public Paint getStrokePaint() {
        return strokePaint;
    }

    @Override
    public Class<? extends SeriesRenderer> getRendererClass() {
        return MyLineAndPointRenderer.class;
    }

    @Override
    public SeriesRenderer getRendererInstance(XYPlot plot) {
        return new MyLineAndPointRenderer(plot);
    }
}
