package com.cyyaw.cui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class RippleEffectView extends View {

    private static final String TAG = RippleEffectView.class.getName();

    private Paint paint;
    private float radius = 0;
    private float x, y;
    private boolean isAnimating = false;

    public RippleEffectView(Context context) {
        super(context);
        init();
    }

    public RippleEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 路径
        paint = new Paint();
        // 颜色
        paint.setColor(Color.BLUE);
        // 填充
        paint.setStyle(Paint.Style.FILL);
        // 50% 透明
        paint.setAlpha(50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isAnimating) {
            // 画圆 x y r 路径
            canvas.drawCircle(x, y, radius, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            radius = 0;
            isAnimating = true;
            postInvalidate();
            animateRipple();
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void animateRipple() {
        final int maxRadius = Math.max(getWidth(), getHeight());
        Log.e(TAG, "animateRipple: " + maxRadius + "  " + radius);
        post(() -> {
            radius += 60;
            postInvalidate();
            if (radius > maxRadius) {
                isAnimating = false;
            } else {
                animateRipple();
            }
        }); // 大约每秒60帧
    }
}
