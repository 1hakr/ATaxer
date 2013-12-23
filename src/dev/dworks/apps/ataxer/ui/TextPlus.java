package dev.dworks.apps.ataxer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextPlus extends TextView {
	public TextPlus(Context paramContext) {
		super(paramContext);
	}

	public TextPlus(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public TextPlus(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public void setPrefix(String paramString) {
		setCompoundDrawables(new TextDrawable(paramString, 0), null, null, null);
	}
	
	public void setPrefix(String paramString, int color) {
		setCompoundDrawables(new TextDrawable(paramString, color), null, null, null);
	}

    private class TextDrawable extends Drawable {
        private String mText = "";
		private int mColor;
 
        public TextDrawable(String text, int color) {
            mText = text;
            mColor = color;
            setBounds(0, 0, (int) getPaint().measureText(mText) + 2, (int) getTextSize());
        }
 
        @Override
        public void draw(Canvas canvas) {
            Paint paint = getPaint();
            paint.setColor(mColor != 0 ? mColor : getCurrentHintTextColor());
            int lineBaseline = getLineBounds(0, null);
            canvas.drawText(mText, 0, canvas.getClipBounds().top + lineBaseline, paint);
        }
 
        @Override
        public void setAlpha(int alpha) {/* Not supported */}
 
        @Override
        public void setColorFilter(ColorFilter colorFilter) {/* Not supported */}
 
        @Override
        public int getOpacity() {
            return 1;
        }
    }
}