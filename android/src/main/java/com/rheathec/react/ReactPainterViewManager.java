package com.rheathec.react;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class ReactPainterViewManager extends SimpleViewManager<ReactPainterView> {

    public static final String REACT_CLASS = "RCTPainter";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ReactPainterView createViewInstance(ThemedReactContext themedReactContext) {
        return new ReactPainterView(themedReactContext);
    }

    @Override
    public void onDropViewInstance(ReactPainterView view) {
        super.onDropViewInstance(view);
        view.cleanup();
    }


}
