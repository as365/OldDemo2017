package com.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ToggleButton;

import com.uidemo.R;

/**
 * Created by Administrator on 2017/6/20.
 */
public class ProgressToggleButton extends ToggleButton {
    public interface onCheckChangesListener {
        void onchechkchanges(boolean isChecked);
    }

    private onCheckChangesListener listener;
    /**
     * 监听
     *
     * @param l
     */
    public void setOnCheckChangesListener(onCheckChangesListener l) {
        this.listener = l;
    }
    private int mainColor;//主体颜色
    private int circleTotalWidth, circleCurrentWidth;//总进度和当前进度的宽度
    private Paint mPaint = new Paint();
    private int mProgress = 1;// 当前进度
    public ProgressToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arry = context.obtainStyledAttributes(attrs,
                R.styleable.ProgressToggleButton);
        mainColor = arry.getColor(R.styleable.ProgressToggleButton_main_color,
                getResources().getColor(R.color.colorPrimary));//前面值就是xml文件指定的值，后者就是前者未指定的默认值
        circleTotalWidth = (int) arry.getDimension(
                R.styleable.ProgressToggleButton_progress_total_width,
                getResources().getDimension(R.dimen.progress_total_width));
        circleCurrentWidth = (int) arry.getDimension(
                R.styleable.ProgressToggleButton_progress_current_width,
                getResources().getDimension(R.dimen.progress_current_width));
        arry.recycle();//一定要让recycle 否则会出问题
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mainColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(0);
        int center = getWidth() / 2;// 三角形,圆圈中央
        int sideLength = center / 5 * 4; // 三角形边长
        if (this.isChecked()) {
            drawPlay(canvas, center, sideLength);//绘制两条竖线
        } else {
            drawStop(canvas, center, sideLength);//绘制正三角形
        }
        // 最外围的总进度
        mPaint.setStrokeWidth(circleTotalWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        int radius = center - circleTotalWidth;
        canvas.drawCircle(center, center, radius, mPaint);
        // 最当前进度
        RectF oval = new RectF(center - radius + circleTotalWidth, center
                - radius + circleTotalWidth,
                center + radius - circleTotalWidth, center + radius
                - circleTotalWidth);
        mPaint.setStrokeWidth(circleCurrentWidth);
        canvas.drawArc(oval, -90, mProgress, false, mPaint);//mProgress指圆弧的度数，这里就代表了我们的进度
    }

    /**
     * 画暂停状态
     *
     * @param canvas
     * @param center
     *            三角形中心横纵坐标
     * @param sideLength
     *            三角形边长
     */
    private void drawStop(Canvas canvas, int center, int sideLength) {
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        float genSan = (float) Math.sqrt(3);
        Path path2 = new Path();
        path2.moveTo((center - sideLength / (2 * genSan)), center - sideLength
                / 2);
        path2.lineTo((center + 2 * sideLength / (2 * genSan)), center);
        path2.lineTo((center - sideLength / (2 * genSan)), center + sideLength
                / 2);
        path2.close();
        canvas.drawPath(path2, mPaint);
    }

    /**
     * 画播放状态
     *
     * @param canvas
     * @param center
     *            两条线的对称轴中心横纵坐标
     * @param sideLength
     *            线的长度
     */

    private void drawPlay(Canvas canvas, int center, int sideLength) {
        float genSan = (float) Math.sqrt(3);
        float linesWidth = sideLength / 5;
        mPaint.setStrokeWidth(linesWidth);
        canvas.drawLine((center - sideLength / (2 * genSan)) + linesWidth / 2,
                center - sideLength / 2, (center - sideLength / (2 * genSan))
                        + linesWidth / 2, center + sideLength / 2, mPaint);
        canvas.drawLine((center + sideLength / (2 * genSan)) - linesWidth / 2,
                center - sideLength / 2, (center + sideLength / (2 * genSan))
                        - linesWidth / 2, center + sideLength / 2, mPaint);
    }

    /**
     * 设置进度
     *
     * @param progress
     */

    public void setProgress(int progress) {
        if (progress >= 100) {
            mProgress = 360 ;
        } else {
            mProgress = (int) ((progress + 1) * 3.6);
        }
        postInvalidate();
    }

    public int getProgress() {
        return (int) (mProgress / 3.6);
    }

    // 设置为wrap_content 时的控件高宽
    private int defultWidth = (int) getResources().getDimension(
            R.dimen.weidght_size);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int finalWidth = 0;
        int finaLHeight = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            finalWidth = widthSize;
        } else {
            finalWidth = (int) (getPaddingLeft() + defultWidth + getPaddingRight());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            finaLHeight = heightSize;
        } else {
            finaLHeight = (int) (getPaddingTop() + defultWidth + getPaddingBottom());
        }
        setMeasuredDimension(finalWidth, finaLHeight);
    }
}
