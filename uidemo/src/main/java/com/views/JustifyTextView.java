package com.views;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * By Jie 2017.7.9
 *
 * 首行缩进，左右对齐TextView
 */
public class JustifyTextView extends TextView {

    private int mLineY;//y轴绘制的坐标
    private int mViewWidth;//view总宽度
    private int mViewHeight;//view总高度

    public JustifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //如果设置的是确切值，那么就不再继续测量了
        if(MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.EXACTLY&&MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.EXACTLY){
            return;
        }
        //如果有的值测量的不准确，需要重新测量
        Layout layout = getLayout();//获取测量的文本布局
        mViewHeight =0;
        mViewWidth = layout.getWidth()+getPaddingLeft()+getPaddingRight();
        for (int i = 0; i < layout.getLineCount(); i++) {
            mViewHeight += getLineHeight();
        }
        mViewHeight = mViewHeight + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(mViewWidth,mViewHeight);//重新设置宽和高
    }


    /**
     *
     * 整体思路如下：
     *
     * 1  确定整个文本有多少行
     *
     * 2 重新绘制每一行的文本保证能左右对齐，或者首行缩进
     *
     * 3 最后一行正常绘制，因为他没有下一行，不一定要设置右对齐了
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        mViewWidth = getMeasuredWidth();//获取测量的实际的宽度
        String text = (String) getText();//获取字符串string
        mLineY = 0;
        mLineY += getTextSize()*1.5;//开始绘制的y轴坐标
        Layout layout = getLayout();
        for (int i = 0; i < layout.getLineCount(); i++) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            String line = text.substring(lineStart, lineEnd);

            float width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint());//每一行文本的宽度
            if (needScale(line) && i < layout.getLineCount()-1 ) {
                drawScaledText(canvas,line, width,i);//自己绘制，保证可以左右对齐或者首行缩进
            } else {
                canvas.drawText(line, 0, mLineY, paint);//不用自己绘制，系统绘制即可
            }
            mLineY += getLineHeight();//继续绘制下一行，找到下一行坐标
        }
    }

    /**
     * 思路如下：
     *
     * 举个例子，屏幕宽度100，文本90，要想让文本填充整个屏幕怎么办呢？
     * 很简单，让文本字符间隔填充剩下的10，还是不懂？
     * 假如文本有10个字符，那么我让每个字符绘制的时候都和上一个字符间隔多1，那么正好文本宽度就成了（90+10=100）可以左右对齐屏幕了
     * @param canvas
     * @param line
     * @param lineWidth
     * @param index
     */
    private void drawScaledText(Canvas canvas,String line, float lineWidth,int index) {
        float x = 0;
        if (isFirstLineOfParagraph(index)) {//如果是第一行就要加空格字符，保证可以首行缩进
            String blanks = "        ";
            canvas.drawText(blanks, x, mLineY, getPaint());
            float bw = StaticLayout.getDesiredWidth(blanks, getPaint());
            x += bw;
            lineWidth = lineWidth +bw;
        }

        float d = (mViewWidth-lineWidth) /line.length();//每个字符的额外间隔，专门是为了填充屏幕剩下的宽度
        for (int i = 0; i < line.length(); i++) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, mLineY, getPaint());
            x += cw+d;
        }
    }

    private boolean isFirstLineOfParagraph(int lineIndex) {
        return lineIndex==0;
    }

    private boolean needScale(String line) {
        if (line.length() == 0) {
            return false;
        } else {
            return line.charAt(line.length() - 1) != '\n';
        }
    }

}

