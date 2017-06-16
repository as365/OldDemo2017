package com.rxdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Administrator on 2017/6/15.
 */

public class CustomButton extends Button {
    public CustomButton(Context context) {
        this(context,null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureW(widthMeasureSpec, 100);
        int height = measureH(heightMeasureSpec, 100);

        if (width < height){
            height = width;
        }else{
            width = height;
        }

        setMeasuredDimension(width, height);
    }

    /**
     * 测量高度
     * @param heightMeasureSpec
     * @param i 最大值
     * @return
     */
    private int measureH(int heightMeasureSpec, int i) {
        int result = i;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(20 * 2,i);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return result;
    }

    /**
     * 测量宽度
     * @param widthMeasureSpec
     * @param i
     * @return
     */
    private int measureW(int widthMeasureSpec, int i) {
        int result = i;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(20* 2,i);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return result;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int r = getMeasuredWidth() / 2;
        int x = getLeft() + r;
        int y = getTop() + r;

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x, y, r, paint);
    }
}
