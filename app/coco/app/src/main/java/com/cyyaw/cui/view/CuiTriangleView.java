package com.cyyaw.cui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CuiTriangleView extends View {

    private Paint paint;
    private Path path;

    private int width;
    private int height;

    public CuiTriangleView(Context context) {
        super(context);
        init();
    }

    public CuiTriangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CuiTriangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CuiTriangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        paint = new Paint();
        paint.setColor(0xFF000000); // 黑色
        paint.setStyle(Paint.Style.FILL);
        path = new Path();
        width = 100;
        height = 100;
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        path.reset();
        path.moveTo(width / 2f, 0); // 顶点
        path.lineTo(0, height); // 左下角
        path.lineTo(width, height); // 右下角
        path.close(); // 闭合路径
        canvas.drawPath(path, paint);
    }


}
