package com.rheathec.react;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Stack;

/**
 * Created by rheathec on 4.05.17.
 */

public class ReactPainterView extends View {

    private static final String JS_PARAM_TOUCHED = "touched";
    private static final String JS_EVENT_NAME = "topChange";

    private final Stack<PaintingLayer> mLayers;
    private PaintingLayer mActiveLayer;

    private Canvas mCanvas;
    private Bitmap mBitmap;

    protected void cleanup() {
        mLayers.clear();
        mBitmap.recycle();
    }

    public ReactPainterView(Context context) {
        super(context);

        this.mActiveLayer = new PaintingLayer(this);

        this.mLayers = new Stack<>();
        this.mLayers.push(this.mActiveLayer);
    }

    public void addLayer() {
        int color = mActiveLayer.getColor();
        mActiveLayer = new PaintingLayer(this);
        mActiveLayer.setColor(color);
        mLayers.push(mActiveLayer);
    }

    public Bitmap createBitmap() {
        Bitmap overlay = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
        Canvas canvas = new Canvas(overlay);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        drawLayers(canvas);
        return overlay;
    }

    @Override
    protected void onSizeChanged(int width, int height, int odlWidth, int oldHeight) {
        super.onSizeChanged(width, height, odlWidth, oldHeight);
        this.mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas.drawBitmap(mBitmap, 0, 0, null);
        drawLayers(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                emitPressedEvent(true);
                break;
            }
            case MotionEvent.ACTION_UP: {
                emitPressedEvent(false);
            }
        }

        mActiveLayer.onTouchEvent(event);

        invalidate();
        return true;
    }

    private void drawLayers(Canvas canvas) {
        for (PaintingLayer el : mLayers) {
            el.drawMe(canvas);
        }
    }

    private void emitPressedEvent(boolean pressed) {
        WritableMap event = Arguments.createMap();
        event.putBoolean(JS_PARAM_TOUCHED, pressed);

        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), JS_EVENT_NAME, event);
    }

    private void refreshView(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

}