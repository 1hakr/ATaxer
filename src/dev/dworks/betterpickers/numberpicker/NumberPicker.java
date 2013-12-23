package dev.dworks.betterpickers.numberpicker;

import java.math.BigDecimal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import dev.dworks.apps.ataxer.R;

public class NumberPicker extends LinearLayout implements Button.OnClickListener,
        Button.OnLongClickListener {

    protected int mInputSize = 20;
    protected final Button mNumbers[] = new Button[10];
    protected int mInput[] = new int[mInputSize];
    protected int mInputPointer = -1;
    protected Button mLeft, mRight;
    protected ImageButton mDelete;
    protected NumberView mEnteredNumber;
    protected final Context mContext;

    private TextView mLabel;
    private NumberPickerErrorTextView mError;
    private int mSign;
    private String mLabelText = "";
    private Button mSetButton;
    private static final int CLICKED_DECIMAL = 10;

    private static final int SIGN_POSITIVE = 0;
    private static final int SIGN_NEGATIVE = 1;

    protected View mDivider;
    private ColorStateList mTextColor;
    private int mKeyBackgroundResId;
    private int mButtonBackgroundResId;
    private int mDividerColor;
    private int mDeleteDrawableSrcResId;
    private int mTheme = -1;

    @SuppressWarnings("unused")
	private Integer mMinNumber = null;
    private Integer mMaxNumber = null;

    public NumberPicker(Context context) {
        this(context, null);
    }

    public NumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(getLayoutId(), this);

        // Init defaults
        mTextColor = getResources().getColorStateList(R.color.dialog_text_color_holo_dark);
        mKeyBackgroundResId = R.drawable.bp_key_background_dark;
        mButtonBackgroundResId = R.drawable.bp_button_background_dark;
        mDeleteDrawableSrcResId = R.drawable.bp_ic_backspace_dark;
        mDividerColor = getResources().getColor(R.color.default_divider_color_dark);
    }

    protected int getLayoutId() {
        return R.layout.number_picker_view;
    }

    public void setTheme(int themeResId) {
        mTheme = themeResId;
        if (mTheme != -1) {
            TypedArray a = getContext().obtainStyledAttributes(themeResId, R.styleable.BetterPickersDialogFragment);

            mTextColor = a.getColorStateList(R.styleable.BetterPickersDialogFragment_bpTextColor);
            mKeyBackgroundResId = a.getResourceId(R.styleable.BetterPickersDialogFragment_bpKeyBackground,
                    mKeyBackgroundResId);
            mButtonBackgroundResId = a.getResourceId(R.styleable.BetterPickersDialogFragment_bpButtonBackground,
                    mButtonBackgroundResId);
            mDividerColor = a.getColor(R.styleable.BetterPickersDialogFragment_bpDividerColor, mDividerColor);
            mDeleteDrawableSrcResId = a.getResourceId(R.styleable.BetterPickersDialogFragment_bpDeleteIcon,
                    mDeleteDrawableSrcResId);
            a.recycle();
        }

        restyleViews();
    }

    private void restyleViews() {
        for (Button number : mNumbers) {
            if (number != null) {
                number.setTextColor(mTextColor);
                number.setBackgroundResource(mKeyBackgroundResId);
            }
        }
        if (mDivider != null) {
            mDivider.setBackgroundColor(mDividerColor);
        }
        if (mLeft != null) {
            mLeft.setTextColor(mTextColor);
            mLeft.setBackgroundResource(mKeyBackgroundResId);
        }
        if (mRight != null) {
            mRight.setTextColor(mTextColor);
            mRight.setBackgroundResource(mKeyBackgroundResId);
        }
        if (mDelete != null) {
            mDelete.setBackgroundResource(mButtonBackgroundResId);
            mDelete.setImageDrawable(getResources().getDrawable(mDeleteDrawableSrcResId));
        }
        if (mEnteredNumber != null) {
            mEnteredNumber.setTheme(mTheme);
        }
        if (mLabel != null) {
            mLabel.setTextColor(mTextColor);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDivider = findViewById(R.id.divider);
        mError = (NumberPickerErrorTextView) findViewById(R.id.error);
        
        for (int i = 0; i < mInput.length; i++) {
            mInput[i] = -1;
        }

        View v1 = findViewById(R.id.first);
        View v2 = findViewById(R.id.second);
        View v3 = findViewById(R.id.third);
        View v4 = findViewById(R.id.fourth);
        mEnteredNumber = (NumberView) findViewById(R.id.number_text);
        mDelete = (ImageButton) findViewById(R.id.delete);
        mDelete.setOnClickListener(this);
        mDelete.setOnLongClickListener(this);

        mNumbers[1] = (Button) v1.findViewById(R.id.key_left);
        mNumbers[2] = (Button) v1.findViewById(R.id.key_middle);
        mNumbers[3] = (Button) v1.findViewById(R.id.key_right);

        mNumbers[4] = (Button) v2.findViewById(R.id.key_left);
        mNumbers[5] = (Button) v2.findViewById(R.id.key_middle);
        mNumbers[6] = (Button) v2.findViewById(R.id.key_right);

        mNumbers[7] = (Button) v3.findViewById(R.id.key_left);
        mNumbers[8] = (Button) v3.findViewById(R.id.key_middle);
        mNumbers[9] = (Button) v3.findViewById(R.id.key_right);

        mLeft = (Button) v4.findViewById(R.id.key_left);
        mNumbers[0] = (Button) v4.findViewById(R.id.key_middle);
        mRight = (Button) v4.findViewById(R.id.key_right);
        setLeftRightEnabled();

        for (int i = 0; i < 10; i++) {
            mNumbers[i].setOnClickListener(this);
            mNumbers[i].setText(String.format("%d", i));
            mNumbers[i].setTag(R.id.numbers_key, Integer.valueOf(i));
        }
        updateNumber();

        Resources res = mContext.getResources();
        mLeft.setText(res.getString(R.string.number_picker_plus_minus));
        mRight.setText(res.getString(R.string.number_picker_max));
        mLeft.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mLabel = (TextView) findViewById(R.id.label);
        mSign = SIGN_POSITIVE;

        // Set the correct label state
        showLabel();

        restyleViews();
        updateKeypad();
    }

    public void setPlusMinusVisibility(int visiblity) {
        if (mLeft != null) {
            mLeft.setVisibility(visiblity);
        }
    }

    public void setDecimalVisibility(int visiblity) {
        if (mRight != null) {
            mRight.setVisibility(visiblity);
        }
    }

    public void setMaxVisibility(int visiblity) {
        if (mRight != null) {
            mRight.setVisibility(visiblity);
        }
    }
    public void setMin(int min) {
        mMinNumber = min;
        // TODO
    }

    public void setMax(int max) {
        mMaxNumber = max;
        if (mRight != null) {
            mRight.setVisibility(View.VISIBLE);
        }
        // TODO
    }

    public void updateDeleteButton() {
        boolean enabled = mInputPointer != -1;
        if (mDelete != null) {
            mDelete.setEnabled(enabled);
        }
    }
    
    /**
     * Expose the NumberView in order to set errors
     *
     * @return the NumberView
     */
    public NumberPickerErrorTextView getErrorView() {
        return mError;
    }

    @Override
    public void onClick(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        mError.hideImmediately();
        doOnClick(v);
        updateDeleteButton();
    }

    protected void doOnClick(View v) {
        Integer val = (Integer) v.getTag(R.id.numbers_key);
        if (val != null) {
            // A number was pressed
            addClickedNumber(val);
        } else if (v == mDelete) {
            if (mInputPointer >= 0) {
                for (int i = 0; i < mInputPointer; i++) {
                    mInput[i] = mInput[i + 1];
                }
                mInput[mInputPointer] = -1;
                mInputPointer--;
            }
        } else if (v == mLeft) {
            onLeftClicked();
        } else if (v == mRight) {
            onRightClicked();
        }
        updateKeypad();
    }

    @Override
    public boolean onLongClick(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
        mError.hideImmediately();
        if (v == mDelete) {
            mDelete.setPressed(false);
            reset();
            updateKeypad();
            return true;
        }
        return false;
    }

    private void updateKeypad() {
        // Update state of keypad
        // Update the number
        updateLeftRightButtons();
        updateNumber();
        // enable/disable the "set" key
        enableSetButton();
        // Update the backspace button
        updateDeleteButton();
    }

    public void setLabelText(String labelText) {
        mLabelText = labelText;
        showLabel();
    }

    private void showLabel() {
        if (mLabel != null) {
            mLabel.setText(mLabelText);
        }
    }

    public void reset() {
        for (int i = 0; i < mInputSize; i++) {
            mInput[i] = -1;
        }
        mInputPointer = -1;
        updateNumber();
    }

    // Update the number displayed in the picker:
    protected void updateNumber() {
        String numberString = getEnteredNumberString();
        numberString = numberString.replaceAll("\\-", "");
        String[] split = numberString.split("\\.");
        if (split.length >= 2) {
            if (split[0].equals("")) {
                mEnteredNumber.setNumber("0", split[1], containsDecimal(),
                        mSign == SIGN_NEGATIVE);
            } else {
                mEnteredNumber.setNumber(split[0], split[1], containsDecimal(),
                        mSign == SIGN_NEGATIVE);
            }
        } else if (split.length == 1) {
            mEnteredNumber.setNumber(split[0], "", containsDecimal(),
                    mSign == SIGN_NEGATIVE);
        } else if (numberString.equals(".")) {
            mEnteredNumber.setNumber("0", "", true, mSign == SIGN_NEGATIVE);
        }
    }
    
    // set the number displayed in the picker:
    protected void setNumber(String number) {
    	if(null == number || !TextUtils.isDigitsOnly(number)){
    		return;
    	}
        String numberString = number;
        numberString = numberString.replaceAll("\\-", "");
        String[] split = numberString.split("(?!^)");
        for (String val : split) {
			addClickedNumber(Integer.valueOf(val));
		}
        updateNumber();
        updateDeleteButton();
    }

    protected void setLeftRightEnabled() {
        mLeft.setEnabled(true);
        mRight.setEnabled(canAddDecimal());
        if (!canAddDecimal()) {
            mRight.setContentDescription(null);
        }
    }

    private void addClickedNumber(int val) {
        if (mInputPointer < mInputSize - 1) {
            // For 0 we need to check if we have a value of zero or not
            if (mInput[0] == 0 && mInput[1] == -1 && !containsDecimal() && val != CLICKED_DECIMAL) {
                mInput[0] = val;
            } else {
                for (int i = mInputPointer; i >= 0; i--) {
                    mInput[i + 1] = mInput[i];
                }
                mInputPointer++;
                mInput[0] = val;
            }
        }
    }

    // Clicking on the bottom left button will toggle the sign
    private void onLeftClicked() {
        if (mSign == SIGN_POSITIVE) {
            mSign = SIGN_NEGATIVE;
        } else {
            mSign = SIGN_POSITIVE;
        }
    }

    // Clicking on the bottom right button will add a decimal point
    private void onRightClicked() {
        /*if (canAddDecimal()) {
            addClickedNumber(CLICKED_DECIMAL);
        }*/
    	
    	if(null != mMaxNumber){
    		reset();
    		setNumber(String.valueOf(mMaxNumber));
    	}
    }

    private boolean containsDecimal() {
        boolean containsDecimal = false;
        for (int i : mInput) {
            if (i == 10) {
                containsDecimal = true;
            }
        }
        return containsDecimal;
    }

    // Checks if the user allowed to click on the right button
    private boolean canAddDecimal() {
        return !containsDecimal();
    }

    private String getEnteredNumberString() {
        String value = "";
        for (int i = mInputPointer; i >= 0; i--) {
            if (mInput[i] == -1) {
                // Don't add
            } else if (mInput[i] == CLICKED_DECIMAL) {
                value += ".";
            } else {
                value += mInput[i];
            }
        }
        return value;
    }

    // Returns the number already entered
    public long getEnteredIntegerNumber() {
        String value = "0";
        for (int i = mInputPointer; i >= 0; i--) {
            if (mInput[i] == -1) {
                break;
            } else if (mInput[i] == CLICKED_DECIMAL) {
                value += ".";
            } else {
                value += mInput[i];
            }
        }
        if (mSign == SIGN_NEGATIVE) {
            value = "-" + value;
        }
        return Long.parseLong(value);
    }
    // Returns the number already entered
    public double getEnteredNumber() {
        String value = "0";
        for (int i = mInputPointer; i >= 0; i--) {
            if (mInput[i] == -1) {
                break;
            } else if (mInput[i] == CLICKED_DECIMAL) {
                value += ".";
            } else {
                value += mInput[i];
            }
        }
        if (mSign == SIGN_NEGATIVE) {
            value = "-" + value;
        }
        return Double.parseDouble(value);
    }

    private void updateLeftRightButtons() {
        mRight.setEnabled(canAddDecimal());
    }

    // Enable/disable the set key
    private void enableSetButton() {
        if (mSetButton == null) {
            return;
        }

        // Nothing entered - disable
        if (mInputPointer == -1) {
            mSetButton.setEnabled(false);
            return;
        }
        // If the user entered 1 digits or more
        mSetButton.setEnabled(mInputPointer >= 0);
    }

    public void setSetButton(Button b) {
        mSetButton = b;
        enableSetButton();
    }

    public long getNumber() {
        String numberString = Long.toString(getEnteredIntegerNumber());
        String[] split = numberString.split("\\.");
        return Long.parseLong(split[0]);
    }

    public double getDecimal() {
        double decimal = BigDecimal.valueOf(getEnteredNumber()).divideAndRemainder(BigDecimal.ONE)[1].doubleValue();
        return decimal;
    }

    public boolean getIsNegative() {
        return mSign == SIGN_NEGATIVE;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        final Parcelable parcel = super.onSaveInstanceState();
        final SavedState state = new SavedState(parcel);
        state.mInput = mInput;
        state.mSign = mSign;
        state.mInputPointer = mInputPointer;
        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        mInputPointer = savedState.mInputPointer;
        mInput = savedState.mInput;
        if (mInput == null) {
            mInput = new int[mInputSize];
            mInputPointer = -1;
        }
        mSign = savedState.mSign;
        updateKeypad();
    }

    private static class SavedState extends BaseSavedState {

        int mInputPointer;
        int[] mInput;
        int mSign;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            mInputPointer = in.readInt();
            in.readIntArray(mInput);
            mSign = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mInputPointer);
            dest.writeIntArray(mInput);
            dest.writeInt(mSign);
        }

        @SuppressWarnings("unused")
		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
