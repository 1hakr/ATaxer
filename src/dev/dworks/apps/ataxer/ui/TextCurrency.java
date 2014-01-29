package dev.dworks.apps.ataxer.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.dworks.apps.ataxer.R;
import dev.dworks.libs.actionbarplus.dialog.SimpleDialogFragment;

public class TextCurrency extends LinearLayout implements View.OnClickListener {
	private FragmentActivity mActivity;
	private Context mContext;
	private String mDetails;
	private int mExampleColor = 0;
	private float mExampleDimension = 0.0F;
	private Drawable mExampleDrawable;
	private TextView mHeader;
	private String mHeaderText;
	private ImageView mInfo;
	//private float mTextHeight;
	private TextPaint mTextPaint;
	//private float mTextWidth;
	private String mTitle;
	private TextPlus mValue;

	public TextCurrency(Context paramContext) {
		super(paramContext);
		init(paramContext, null, 0);
	}

	public TextCurrency(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init(paramContext, paramAttributeSet, 0);
	}

	public TextCurrency(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		init(paramContext, paramAttributeSet, paramInt);
	}

	private void init(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		mContext = paramContext;
		mActivity = ((FragmentActivity) paramContext);
		TypedArray a = getContext().obtainStyledAttributes(
				paramAttributeSet, R.styleable.TextCurrency, paramInt, 0);
		mHeaderText = a.getString(R.styleable.TextCurrency_headerText);
		mExampleColor = a.getColor(R.styleable.TextCurrency_prefixColor, mExampleColor);
		mExampleDimension = a.getDimension(R.styleable.TextCurrency_prefixDimension, mExampleDimension);
		if (a.hasValue(R.styleable.TextCurrency_prefixDrawable)) {
			mExampleDrawable = a.getDrawable(R.styleable.TextCurrency_prefixDrawable);
			mExampleDrawable.setCallback(this);
		}
		a.recycle();
		LayoutInflater.from(paramContext).inflate(R.layout.view_text_currency, this, true);
		mHeader = ((TextView) findViewById(R.id.header));
		if ((mHeader != null) && (!TextUtils.isEmpty(mHeaderText)))
			mHeader.setText(mHeaderText);
		mValue = ((TextPlus) findViewById(R.id.value));
		if (mValue != null)
			mValue.setPrefix(paramContext.getString(R.string.rupee), mExampleColor);
		mInfo = ((ImageView) findViewById(R.id.info));
		mInfo.setOnClickListener(this);
		mTextPaint = new TextPaint();
		mTextPaint.setFlags(1);
		mTextPaint.setTextAlign(Paint.Align.LEFT);
		invalidateTextPaintAndMeasurements();
	}

	private void invalidateTextPaintAndMeasurements() {
		mTextPaint.setTextSize(mExampleDimension);
		mTextPaint.setColor(mExampleColor);
		//mTextHeight = mTextPaint.getFontMetrics().bottom;
	}

	public int getExampleColor() {
		return mExampleColor;
	}

	public float getExampleDimension() {
		return mExampleDimension;
	}

	public Drawable getExampleDrawable() {
		return mExampleDrawable;
	}

	public String getExampleString() {
		return mHeaderText;
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		default:
			SimpleDialogFragment.createBuilder(mContext,mActivity.getSupportFragmentManager())
			.setTitle(mTitle)
			.setMessage(mDetails)
			.hideDefaultButton(true)
			.show();
			break;
		}
	}

	protected void onDraw(Canvas paramCanvas) {
		super.onDraw(paramCanvas);
	}

	public void setExampleColor(int paramInt) {
		mExampleColor = paramInt;
		invalidateTextPaintAndMeasurements();
	}

	public void setExampleDimension(float paramFloat) {
		mExampleDimension = paramFloat;
		invalidateTextPaintAndMeasurements();
	}

	public void setExampleDrawable(Drawable paramDrawable) {
		mExampleDrawable = paramDrawable;
	}

	public void setExampleString(String paramString) {
		mHeaderText = paramString;
		invalidateTextPaintAndMeasurements();
	}

	public void setHeaderText(String paramString) {
		if ((mHeader != null) && (!TextUtils.isEmpty(mHeaderText)))
			mHeader.setText(mHeaderText);
	}

	public void setInfo(String paramString1, String paramString2) {
		mTitle = paramString1;
		mDetails = paramString2;
	}

	public void setValueText(String paramString) {
		if ((mValue != null) && (!TextUtils.isEmpty(paramString)))
			mValue.setText(paramString);
	}
}