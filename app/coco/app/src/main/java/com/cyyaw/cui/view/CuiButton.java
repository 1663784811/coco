package com.cyyaw.cui.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.cyyaw.coco.R;

public class CuiButton extends View {

    private static final String TAG = CuiButton.class.getName();

    private int textColor;
    private int backgroundNormal;
    private float textSize;
    private String content;
    private Bitmap bitmap;
    private int default_padding;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private Paint paintRect;
    private Paint paintTxt;
    private RectF rect;
    private float txtHeight;
    private Matrix matrix;
    private ObjectAnimator animator;
    private int mViewWidth;
    private int mViewHeight;
    private int STATE_NORMAL = 0;
    private int STATE_LOADING = 1;
    private int STATE_COMPLETED = 2;
    private int state = STATE_NORMAL;
    private String loadingTxt;
    private String contentNormal;
    private float corners;
    private int backgroundPressed;


    public CuiButton(Context context) {
        this(context, null, 0);
    }

    public CuiButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CuiButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initData(context);
    }

    /**
     * 用来测量视图的宽度和高度的
     * desiredWidth 和 desiredHeight 定义了期望的尺寸。
     * 通过 MeasureSpec.getMode 获取测量模式，并通过 MeasureSpec.getSize 获取测量尺寸。
     * 根据不同的测量模式，计算最终的宽度和高度。
     * 调用 setMeasuredDimension 方法设置测量后的尺寸。
     * MeasureSpec 的模式
     * 1. UNSPECIFIED：父视图对子视图没有任何约束，子视图可以是任意大小。
     * 2. EXACTLY：父视图已经确定了子视图的精确大小，子视图应该正好使用这个大小。
     * 3. AT_MOST：子视图可以是指定大小内的任意大小，但不能超过这个大小。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        initRect(measureWidth, measureHeight);
        setMeasuredDimension(measureWidth, measureHeight);
    }


    /**
     * 大小改变
     *
     * @param w    Current width of this view.
     * @param h    Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }


    /**
     * 画
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制带有圆角的矩形
        canvas.drawRoundRect(rect, corners, corners, paintRect);
        if (state == STATE_NORMAL) {
            //  正常状态
            // 绘制文字
            canvas.drawText(content, paddingLeft + rect.width() / 2 - paddingRight, rect.height() / 2 + txtHeight / 3, paintTxt);
        } else if (state == STATE_LOADING) {
            // 加载状态
            // 旋转的位置
            canvas.translate(mViewWidth / 2, mViewHeight / 2);
            //计算旋转中心点
            matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);
            // 绘制图片
            canvas.drawBitmap(bitmap, matrix, null);
        } else if (state == STATE_COMPLETED) {
            // 加载完成
            canvas.drawText(content, paddingLeft + rect.width() / 2 - paddingRight, rect.height() / 2 + txtHeight / 3, paintTxt);
        }
    }

    /**
     * 触摸事件处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (listener == null) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (loadingTxt == null) {
                    content = "";
                } else {
                    content = loadingTxt;
                }
                paintRect.setColor(backgroundPressed);
                state = STATE_NORMAL;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                paintRect.setColor(backgroundNormal);
                startAnim();
                state = STATE_LOADING;
                postInvalidate();
                listener.onLoadingClick(this);
                break;
        }
        return true;
    }


    /**
     * 方法描述：初始化属性值
     *
     * @param context 上下文
     * @param attrs   属性集合
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.cui_button);
        backgroundNormal = typeArray.getColor(R.styleable.cui_button_background_color_normal, Color.parseColor("#3A96FF"));
        backgroundPressed = typeArray.getColor(R.styleable.cui_button_background_color_pressed, Color.parseColor("#1E90FF"));
        textColor = typeArray.getColor(R.styleable.cui_button_text_color, Color.WHITE);
        textSize = typeArray.getDimension(R.styleable.cui_button_text_size, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        contentNormal = typeArray.getString(R.styleable.cui_button_text);
        loadingTxt = typeArray.getString(R.styleable.cui_button_loading_text);
        corners = typeArray.getDimension(R.styleable.cui_button_corners, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
        typeArray.recycle();
    }

    /**
     * 初始化数据
     */
    private void initData(Context context) {
        if (contentNormal != null) {
            content = contentNormal;
        } else {
            content = "";
        }
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cui_icon_loadding_24);
        default_padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        paddingBottom = getPaddingBottom();
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        //矩形画笔
        paintRect = new Paint();
        paintRect.setColor(backgroundNormal);
        paintRect.setAntiAlias(true);
        paintRect.setStyle(Paint.Style.FILL);
        //矩形对象
        rect = new RectF();
        //文字画笔
        paintTxt = new Paint();
        paintTxt.setColor(textColor);
        paintRect.setAntiAlias(true);
        paintTxt.setTextAlign(Paint.Align.CENTER);
        paintTxt.setTextSize(textSize);
        txtHeight = (float) getTxtHeight(paintTxt);
    }


    /**
     * 方法描述：矩形对象初始化
     */
    private void initRect(int measureWidth, int measureHeight) {
        rect.left = 0;
        rect.top = 0;
        rect.right = measureWidth;
        rect.bottom = measureHeight;
    }

    /**
     *
     */
    private int measureWidth(int measureSpec) {
        int width;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            width = specSize;
        } else {
            width = (int) paintTxt.measureText(content) + paddingLeft + paddingRight;
            if (specMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, specSize);
            }
        }
        return width;
    }

    /**
     * 测量高度大小
     */
    private int measureHeight(int measureSpec) {
        int height;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        } else {
            height = (int) txtHeight + paddingTop + paddingBottom + default_padding;
            if (specMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, specSize);
            }
        }
        return height;
    }


    /**
     * 方法描述：加载完成以后让Button复位
     */
    public void setCompleted() {
        content = contentNormal;
        state = STATE_COMPLETED;
        cancelAnim();
        postInvalidate();
    }


    /**
     * 方法描述：获取文本字符的高度
     *
     * @param mPaint 画文本字符的画笔
     * @return 文本字符的高度
     */
    public double getTxtHeight(Paint mPaint) {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * 方法描述：开启动画
     */
    private void startAnim() {
        if (animator == null) {
            initAnimator();
            animator.start();
            return;
        }
        if (!animator.isRunning()) {
            animator.start();
        }
    }

    /**
     * 方法描述：取消动画
     */
    private void cancelAnim() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    /**
     * 初始化动画
     */
    @SuppressLint("ObjectAnimatorBinding")
    private void initAnimator() {
        matrix = new Matrix();
        animator = ObjectAnimator.ofFloat(matrix, "rotation", 0, 360);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                matrix.setRotate((float) animation.getAnimatedValue());
                invalidate();
            }
        });
    }

    private LoadingListener listener;

    public void setLoadingListener(LoadingListener loginClickListener) {
        this.listener = loginClickListener;
    }

    public interface LoadingListener {
        void onLoadingClick(CuiButton view);
    }

}
