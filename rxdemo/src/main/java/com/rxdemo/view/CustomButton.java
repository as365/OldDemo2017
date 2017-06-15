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
        int width = mmeasure(widthMeasureSpec, 100);
        int height = mmeasure(heightMeasureSpec, 100);

        if (width < height){
            height = width;
        }else{
            width = height;
        }

        setMeasuredDimension(width, height);
    }

    /**
     * 源码中已经含有measure所以需要自定义
     * @param measureSpec
     * @param defaultSize
     * @return
     */
    private int mmeasure(int measureSpec, int defaultSize) {
        int result = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch(mode){
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;
            default:
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
