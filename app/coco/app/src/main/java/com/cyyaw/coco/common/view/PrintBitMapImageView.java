package com.cyyaw.coco.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.cyyaw.coco.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


/**
 * view有以下14个周期：
 * 1、onFinishInflate() 当View中所有的子控件均被映射成xml后触发 。
 * 2、onMeasure( int , int ) 确定所有子元素的大小 。
 * 3、onLayout( boolean , int , int , int , int ) 当View分配所有的子元素的大小和位置时触发 。
 * 4、onSizeChanged( int , int , int , int ) 当view的大小发生变化时触发 。
 * 5、onDraw(Canvas) view渲染内容的细节。
 * 6、onKeyDown( int , KeyEvent) 有按键按下后触发 。
 * 7、onKeyUp( int , KeyEvent) 有按键按下后弹起时触发 。
 * 8、onTrackballEvent(MotionEvent) 轨迹球事件 。
 * 9、onTouchEvent(MotionEvent) 触屏事件 。
 * 10、onFocusChanged( boolean , int , Rect) 当View获取或失去焦点时触发 。
 * 11、onWindowFocusChanged( boolean ) 当窗口包含的view获取或失去焦点时触发 。
 * 12、onAttachedToWindow() 当view被附着到一个窗口时触发 。
 * 13、onDetachedFromWindow() 当view离开附着的窗口时触发，Android123提示该方法和 onAttachedToWindow() 是相反的。
 * 14、onWindowVisibilityChanged( int ) 当窗口中包含的可见的view发生变化时触发。
 */
public class PrintBitMapImageView extends View {

    private int paperWidth = 58;
    private int printWidth = 48;


    // ==============================
    // 宽度比
    private BigDecimal printWidthRatio;
    // 显示的宽度
    private int showPrintWidth;
    private int showPrintX;
    private int showPrintY = 50;

    /**
     * 绘制列表
     */
    private List<PrintData> printDataList = new ArrayList<>();

    // ==============================
    Context context;
    private int width;
    private int height = 0;

    // ==== 背景
    private Paint bgRectFPaint;
    private RectF bgRectF;
    private float bgCornerRadius;


    public PrintBitMapImageView(Context context) {
        super(context);
        init(null);
    }

    public PrintBitMapImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public PrintBitMapImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    public PrintBitMapImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PrintBitMapImageView, 0, 0);
        int pw = typedArray.getInt(R.styleable.PrintBitMapImageView_paperWidth, 58);
        if (pw > 0) {
            paperWidth = pw;
        }
        int printW = typedArray.getInt(R.styleable.PrintBitMapImageView_printWidth, 48);
        if (printW > 0) {
            printWidth = printW;
        }
        // ===========================================================
        // 初始化画笔
        // 白色背景
        // setBackgroundColor(Color.WHITE);
        // 绘制带圆角的矩形背景
        bgRectFPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgRectFPaint.setColor(Color.WHITE); // 设置背景颜色为红色
        bgRectF = new RectF();
        bgCornerRadius = 10 * getResources().getDisplayMetrics().density; // 圆角半径
    }

    /**
     * 如果你的视图需要自定义尺寸，你可以覆写 onMeasure 方法。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        if (height < 300) {
            height = 300;
        }
        // 计算有效打印宽度比
        printWidthRatio = new BigDecimal(printWidth).divide(new BigDecimal(paperWidth), 18, RoundingMode.HALF_UP);
        showPrintWidth = printWidthRatio.multiply(new BigDecimal(width)).intValue();
        showPrintX = new BigDecimal(width - showPrintWidth).divide(new BigDecimal(2), 18, RoundingMode.HALF_UP).intValue();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 背景
        canvas.drawRoundRect(bgRectF, bgCornerRadius, bgCornerRadius, bgRectFPaint);
        // 画矩形
        drawData(canvas);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bgRectF.set(0, 0, w, h);
    }


    /**
     * 处理触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 处理按下事件
                return true;
            case MotionEvent.ACTION_MOVE:
                // 处理移动事件
                return true;
            case MotionEvent.ACTION_UP:
                // 处理抬起事件
                return true;
        }
        return super.onTouchEvent(event);
    }


    private void drawData(Canvas canvas) {
        boolean changeData = false;
        int h = showPrintY;
        for (int i = 0; i < printDataList.size(); i++) {
            PrintData printData = printDataList.get(i);
            Bitmap bitmap = printData.getBitmapData();
            if (null == bitmap) {
                int dataType = printData.getDataType();
                String source = printData.getSource();
                if (dataType == 1) {
                    bitmap = drawWord(source);
                    printData.setBitmapData(bitmap);
                    changeData = true;
                }
            }
            if (null != bitmap) {
                canvas.drawBitmap(bitmap, showPrintX, h, null);
                h += bitmap.getHeight();
            }
        }
        if (changeData) {
            height = (h + 100);
            requestLayout(); // 请求重新测量
        }
    }

    /**
     * 画文字
     */
    public void setWordData(String word) {
        PrintData printData = new PrintData();
        printData.setDataType(1);
        printData.setSource(word);
        printDataList.add(printData);
        invalidate(); // 请求重新绘制
    }

    /**
     * 绘制文字
     */
    private Bitmap drawWord(String word) {
        // 文字
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
        textPaint.setAntiAlias(true);
        StaticLayout staticLayout = StaticLayout.Builder.obtain(word, 0, word.length(), textPaint, showPrintWidth)
                //
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                //
                .setLineSpacing(0.0f, 1.0f)
                //
                .setIncludePad(false).build();
        int h = staticLayout.getHeight();
        // 创建一个 Bitmap
        Bitmap wordBitmap = Bitmap.createBitmap(showPrintWidth, h, Bitmap.Config.ARGB_8888);
        // 创建一个 Canvas 以 Bitmap 作为绘制目标
        Canvas canvas = new Canvas(wordBitmap);
        // 将文本绘制到 Canvas 上
        canvas.save();
        staticLayout.draw(canvas);
        canvas.restore();
        return wordBitmap;
    }


    class PrintData {
        /**
         * 类型
         * 1.字符串
         * 2.图片
         * 3.网络图片
         */
        private int dataType;

        private String source;
        private Bitmap bitmapData;

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Bitmap getBitmapData() {
            return bitmapData;
        }

        public void setBitmapData(Bitmap bitmapData) {
            this.bitmapData = bitmapData;
        }
    }
}
