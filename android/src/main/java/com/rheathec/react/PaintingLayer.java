package com.rheathec.react;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * Created by rheathec on 4.05.2017.
 */

public class PaintingLayer {

    private static final float TOUCH_TOLERANCE = 4;

    protected final ReactPainterView view;
    protected final Path path;
    protected final Paint paint;
    protected final PointF position = new PointF();

    public int getColor() {
        return paint.getColor();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public PaintingLayer(ReactPainterView view) {
        this.view = view;

        this.path = new Path();

        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setDither(true);
        this.paint.setColor(Color.parseColor("#f01900"));
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeJoin(Paint.Join.ROUND);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStrokeWidth(12);
    }

    private void touchStart(float x, float y) {
        this.path.reset();
        this.path.moveTo(x, y);
        this.position.set(x, y);
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - position.x);
        float dy = Math.abs(y - position.y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            this.path.quadTo(position.x, position.y, (x + position.x) / 2, (y + position.y) / 2);
            this.position.set(x, y);
        }
    }

    private void touchUp() {
        this.path.lineTo(position.x, position.y);
    }

    public void drawMe(Canvas canvas) {
        canvas.drawPath(this.path, this.paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                this.view.addLayer();
                break;
        }

        return true;
    }

}
