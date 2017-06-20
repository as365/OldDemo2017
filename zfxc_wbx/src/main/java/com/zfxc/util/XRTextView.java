package com.zfxc.util;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class XRTextView extends TextView{
	private final String namespace = "rong.android.TextView";
	private String text;
	private float textSize;
	private float paddingLeft;
	private float paddingRight;
	private float marginLeft;
	private float marginRight;
	private int textColor;
	private JSONArray colorIndex;
	private Paint paint1 = new Paint();
	private Paint paintColor = new Paint();
	private float textShowWidth;
	private float Spacing = 0;
	private float LineSpacing = 1.3f;//行与行的间距
	private boolean mEnabled = true;
	public XRTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		text = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/android", "text");
		textSize = attrs.getAttributeIntValue(namespace, "textSize", 25);//字体大小
		textColor = attrs.getAttributeIntValue(namespace, "textColor",Color.BLACK);//字体颜色
		paddingLeft = attrs.getAttributeIntValue(namespace, "paddingLeft", 0);
		paddingRight = attrs.getAttributeIntValue(namespace, "paddingRight", 0);
		marginLeft = attrs.getAttributeIntValue(namespace, "marginLeft", 0);
		marginRight = attrs.getAttributeIntValue(namespace, "marginRight", 0);
		paint1.setTextSize(textSize);
		paint1.setColor(textColor);
		paint1.setAntiAlias(true);
		paintColor.setAntiAlias(true);
		paintColor.setTextSize(textSize);
		paintColor.setColor(Color.BLACK);
	}
	public XRTextView(Context context, float textSize, int textColor, float paddingLeft, float paddingRight, float marginLeft, float marginRight){
		super(context);
		this.textSize = textSize;
		this.textColor = textColor;
		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		paint1.setTextSize(textSize);
		paint1.setColor(textColor);
		paint1.setAntiAlias(true); 
		paintColor.setAntiAlias(true);
		paintColor.setTextSize(textSize);
		paintColor.setColor(Color.BLUE);
	}
	  public void setAutoSplitEnabled(boolean enabled) {
	        mEnabled = enabled;
	    }
	
	public JSONArray getColorIndex() {
		return colorIndex;
	}

	public void setColorIndex(JSONArray colorIndex) {
		this.colorIndex = colorIndex;
	}
	/**
	 * 传入一个索引，判断当前字是否被高亮
	 * @param index
	 * @return
	 * @throws JSONException 
	 */
	public boolean isColor(int index) throws JSONException{
		if(colorIndex == null){
			return false;
		}
		for(int i = 0 ; i < colorIndex.length() ; i ++){
			JSONArray array = colorIndex.getJSONArray(i);
			int start = array.getInt(0);
			int end = array.getInt(1)-1;
			if(index >= start && index <= end){
				return true;
			}
			
		}
		
		
		return false;
	}
	

	@Override
//	protected void onDraw(Canvas canvas) {
////		super.onDraw(canvas);
//		View view=(View)this.getParent();
//		textShowWidth=view.getMeasuredWidth()-paddingLeft - paddingRight - marginLeft - marginRight;
//		int lineCount = 0;
//		
//		text = this.getText().toString();//.replaceAll("\n", "\r\n");
//		if(text==null)return;
//		char[] textCharArray = text.toCharArray();
//		// 已绘的宽度
//		float drawedWidth = 0;
//		float charWidth;
//		for (int i = 0; i < textCharArray.length; i++) {
//			charWidth = paint1.measureText(textCharArray, i, 1);
//			
//			if(textCharArray[i]=='\n'){
//				lineCount++;
//				drawedWidth = 0;
//				continue;
//			}
//			if (textShowWidth - drawedWidth < charWidth) {
//				lineCount++;
//				drawedWidth = 0;
//			}
//			boolean color = false;
//			try {
//				color = isColor(i);
//			} catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//			if(color){
//				
//				canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth,
//						(lineCount + 1) * textSize * LineSpacing, paintColor);
//			}else{
//				
//				canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth,
//						(lineCount + 1) * textSize * LineSpacing, paint1);
//			}
//			if(textCharArray[i] > 127 && textCharArray[i] != '、' && textCharArray[i] != '，' && textCharArray[i] != '。' && textCharArray[i] != '：' && textCharArray[i] != '！'){
//				drawedWidth += charWidth + Spacing;
//				
//			}else{
//				drawedWidth += charWidth;
//
//			}
//		}
//		setHeight((int) ((lineCount + 1) * (int) textSize * LineSpacing + 10));
//	}
	 protected void onDraw(Canvas canvas) {
	        textShowWidth = this.getMeasuredWidth() - paddingLeft - paddingRight;
	        int lineCount = 0;
	        text = this.getText().toString();
	        if (text == null)
	            return;
	        char[] textCharArray = text.toCharArray();
	        float drawedWidth = 0;
	        float charWidth;
	        for (int i = 0; i < textCharArray.length; i++) {
	            charWidth = paint1.measureText(textCharArray, i, 1);
	            if (textCharArray[i] == '\n') {
	                lineCount++;
	                drawedWidth = 0;
	                continue;
	            }
	            if (textShowWidth - drawedWidth < charWidth) {
	                lineCount++;
	                drawedWidth = 0;
	            }
	            canvas.drawText(textCharArray, i, 1, paddingLeft + drawedWidth,
	                    (lineCount + 1) * textSize, paint1);
	            drawedWidth += charWidth;
	        }
	        setHeight((int) ((lineCount + 1) * (int) textSize ));
	    }
//	 @Override
//	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//	        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY 
//	            && MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
//	            && getWidth() > 0 
//	            && getHeight() > 0
//	            && mEnabled) {
//	            String newText = autoSplitText(this);
//	            if (!TextUtils.isEmpty(newText)) {
//	                setText(newText);
//	            }
//	        }
//	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	    }
//	    
//	    private String autoSplitText(final TextView tv) {
//	        final String rawText = tv.getText().toString(); //原始文本
//	        final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
//	        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度
//	        
//	        //将原始文本按行拆分
//	        String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
//	        StringBuilder sbNewText = new StringBuilder();
//	        for (String rawTextLine : rawTextLines) {
//	            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
//	                //如果整行宽度在控件可用宽度之内，就不处理了
//	                sbNewText.append(rawTextLine);
//	            } else {
//	                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
//	                float lineWidth = 0;
//	                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
//	                    char ch = rawTextLine.charAt(cnt);
//	                    lineWidth += tvPaint.measureText(String.valueOf(ch));
//	                    if (lineWidth <= tvWidth) {
//	                        sbNewText.append(ch);
//	                    } else {
//	                        sbNewText.append("\n");
//	                        lineWidth = 0;
//	                        --cnt;
//	                    }
//	                }
//	            }
//	            sbNewText.append("\n");
//	        }
//	        
//	        //把结尾多余的\n去掉
//	        if (!rawText.endsWith("\n")) {
//	            sbNewText.deleteCharAt(sbNewText.length() - 1);
//	        }
//	        
//	        return sbNewText.toString();
//	    }
//	
	
	public float getSpacing() {
		return Spacing;
	}
	public void setSpacing(float spacing) {
		Spacing = spacing;
	}
	public float getMYLineSpacing() {
		return LineSpacing;
	}
	public void setMYLineSpacing(float lineSpacing) {
		LineSpacing = lineSpacing;
	}
	public float getMYTextSize() {
		return textSize;
	}
	public void setMYTextSize(float textSize) {
		this.textSize = textSize;
		paint1.setTextSize(textSize);
		paintColor.setTextSize(textSize);
	}
	
	
}
