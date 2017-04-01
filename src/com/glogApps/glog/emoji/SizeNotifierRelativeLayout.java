package com.glogApps.glog.emoji;

import com.glogApps.glog.utils.Utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.widget.RelativeLayout;



public class SizeNotifierRelativeLayout extends RelativeLayout {

    private Rect rect = new Rect();
    public SizeNotifierRelativeLayoutDelegate delegate;

    public abstract interface SizeNotifierRelativeLayoutDelegate {
        public abstract void onSizeChanged(int keyboardHeight);
    }

    public SizeNotifierRelativeLayout(Context context) {
        super(context);
    }

    public SizeNotifierRelativeLayout(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public SizeNotifierRelativeLayout(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (delegate != null) {
            int usableViewHeight = this.getRootView().getHeight() - Utils.statusBarHeight;
            this.getWindowVisibleDisplayFrame(rect);
            int keyboardHeight = usableViewHeight - (rect.bottom - rect.top);
            delegate.onSizeChanged(keyboardHeight);
        }
    }
}