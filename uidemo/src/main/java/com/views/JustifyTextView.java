package com.views;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class JustifyTextView extends TextView {

    private int mLineY;
    private int mViewWidth;
    private int mViewHeight;

    public JustifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        mViewHeight =0;
        mViewWidth = layout.getWidth()+getPaddingLeft()+getPaddingRight();
        for (int i = 0; i < layout.getLineCount(); i++) {
            mViewHeight += getLineHeight();
        }
        mViewHeight = mViewHeight + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(mViewWidth,mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        mViewWidth = getMeasuredWidth();
        String text = (String) getText();
        mLineY = 0;
        mLineY += getTextSize() * 1.5;
        Layout layout = getLayout();
        for (int i = 0; i < layout.getLineCount(); i++) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            String line = text.substring(lineStart, lineEnd);

            float width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint());
            if (needScale(line) && i < layout.getLineCount()-1 ) {
                drawScaledText(canvas,line, width);
            } else {
                canvas.drawText(line, 0, mLineY, paint);
            }

            mLineY += getLineHeight();
        }
    }

    private void drawScaledText(Canvas canvas,String line, float lineWidth) {
        float x = 0;
        if (isFirstLineOfParagraph(line)) {
            String blanks = "  ";
            canvas.drawText(blanks, x, mLineY, getPaint());
            float bw = StaticLayout.getDesiredWidth(blanks, getPaint());
            x += bw;

            line = line.substring(3);
        }

        float d = (mViewWidth-lineWidth) /line.length();
        for (int i = 0; i < line.length(); i++) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, mLineY, getPaint());
            x += cw+d;
        }
    }

    private boolean isFirstLineOfParagraph(String line) {
        return line.length() > 3 && line.charAt(0) == ' ' && line.charAt(1) == ' ';
    }

    private boolean needScale(String line) {
        if (line.length() == 0) {
            return false;
        } else {
            return line.charAt(line.length() - 1) != '\n';
        }
    }

}

