package dev.dworks.betterpickers.numberpicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


import dev.dworks.apps.ataxer.R;
import dev.dworks.betterpickers.ZeroTopPaddingTextView;

public class NumberView extends LinearLayout {

    private ZeroTopPaddingTextView mNumber, mDecimal;
    private ZeroTopPaddingTextView mDecimalSeperator;
    private ZeroTopPaddingTextView mMinusLabel;
    private final Typeface mAndroidClockMonoThin;
    private Typeface mOriginalNumberTypeface;

    private ColorStateList mTextColor;

    public NumberView(Context context) {
        this(context, null);
    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mAndroidClockMonoThin =
                Typeface.createFromAsset(context.getAssets(), "fonts/AndroidClockMono-Thin.ttf");

        // Init defaults
        mTextColor = getResources().getColorStateList(R.color.dialog_text_color_holo_dark);
    }

    public void setTheme(int themeResId) {
        if (themeResId != -1) {
            TypedArray a = getContext().obtainStyledAttributes(themeResId, R.styleable.BetterPickersDialogFragment);

            mTextColor = a.getColorStateList(R.styleable.BetterPickersDialogFragment_bpTextColor);
        }

        restyleViews();
    }

    private void restyleViews() {
        if (mNumber != null) {
            mNumber.setTextColor(mTextColor);
        }
        if (mDecimal != null) {
            mDecimal.setTextColor(mTextColor);
        }
        if (mDecimalSeperator != null) {
            mDecimalSeperator.setTextColor(mTextColor);
        }
        if (mMinusLabel != null) {
            mMinusLabel.setTextColor(mTextColor);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mNumber = (ZeroTopPaddingTextView) findViewById(R.id.number);
        mDecimal = (ZeroTopPaddingTextView) findViewById(R.id.decimal);
        mDecimalSeperator = (ZeroTopPaddingTextView) findViewById(R.id.decimal_separator);
        mMinusLabel = (ZeroTopPaddingTextView) findViewById(R.id.minus_label);
        if (mNumber != null) {
            mOriginalNumberTypeface = mNumber.getTypeface();
        }
        // Set the lowest time unit with thin font
        if (mNumber != null) {
            mNumber.setTypeface(mAndroidClockMonoThin);
            mNumber.updatePadding();
        }
        if (mDecimal != null) {
            mDecimal.setTypeface(mAndroidClockMonoThin);
            mDecimal.updatePadding();
        }

        restyleViews();
    }

    public void setNumber(String numbersDigit, String decimalDigit, boolean showDecimal,
            boolean isNegative) {
        mMinusLabel.setVisibility(isNegative ? View.VISIBLE : View.GONE);
        if (mNumber != null) {
            if (numbersDigit.equals("")) {
                // Set to -
                mNumber.setText("-");
                mNumber.setTypeface(mAndroidClockMonoThin);
                mNumber.setEnabled(false);
                mNumber.updatePadding();
                mNumber.setVisibility(View.VISIBLE);
            } else if (showDecimal) {
                // Set to bold
                mNumber.setText(numbersDigit);
                mNumber.setTypeface(mOriginalNumberTypeface);
                mNumber.setEnabled(true);
                mNumber.updatePadding();
                mNumber.setVisibility(View.VISIBLE);
            } else {
                // Set to thin
                mNumber.setText(numbersDigit);
                mNumber.setTypeface(mAndroidClockMonoThin);
                mNumber.setEnabled(true);
                mNumber.updatePadding();
                mNumber.setVisibility(View.VISIBLE);
            }
        }
        if (mDecimal != null) {
            // Hide digit
            if (decimalDigit.equals("")) {
                mDecimal.setVisibility(View.GONE);
            } else {
                mDecimal.setText(decimalDigit);
                mDecimal.setTypeface(mAndroidClockMonoThin);
                mDecimal.setEnabled(true);
                mDecimal.updatePadding();
                mDecimal.setVisibility(View.VISIBLE);
            }
        }
        if (mDecimalSeperator != null) {
            // Hide separator
            mDecimalSeperator.setVisibility(showDecimal ? View.VISIBLE : View.GONE);
        }
    }
}