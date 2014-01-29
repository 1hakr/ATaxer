package dev.dworks.apps.ataxer.wizard.fragments;

import static dev.dworks.apps.ataxer.misc.Utils.getFormattedString;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import dev.dworks.apps.ataxer.R;
import dev.dworks.apps.ataxer.misc.Utils;
import dev.dworks.apps.ataxer.ui.TextCurrency;
import dev.dworks.apps.ataxer.wizard.pages.DeductionThreePage;
import dev.dworks.betterpickers.numberpicker.NumberPickerBuilder;
import dev.dworks.betterpickers.numberpicker.NumberPickerDialogFragment.NumberPickerDialogHandler;
import dev.dworks.libs.awizard.model.PageFragmentCallbacks;

public class DeductionThreeFragment extends Fragment implements NumberPickerDialogHandler, OnClickListener {
	private static final String ARG_KEY = "key";
	private TextCurrency deduction_80ccg;
	private TextCurrency deduction_80d;
	private TextCurrency deduction_80dd;
	private TextCurrency deduction_80ddb;
	private PageFragmentCallbacks mCallbacks;
	private String mKey;
	private DeductionThreePage mPage;
	private NumberPickerBuilder npb;

	public static DeductionThreeFragment create(String paramString) {
		Bundle localBundle = new Bundle();
		localBundle.putString(ARG_KEY, paramString);
		DeductionThreeFragment localDeductionThreeFragment = new DeductionThreeFragment();
		localDeductionThreeFragment.setArguments(localBundle);
		return localDeductionThreeFragment;
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		mKey = getArguments().getString(ARG_KEY);
		mPage = ((DeductionThreePage) mCallbacks.onGetPage(mKey));
		npb = new NumberPickerBuilder().setFragmentManager(getChildFragmentManager())
				.setStyleResId(R.style.BetterPickersDialogFragment_Light)
				.setTargetFragment(DeductionThreeFragment.this)
				.setPlusMinusVisibility(View.INVISIBLE)
				.setDecimalVisibility(View.INVISIBLE)
				.setLabelText(getString(R.string.rupee));
		getTotal(mPage);
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.fragment_page_deduction_three, paramViewGroup, false);
		((TextView) localView.findViewById(android.R.id.title)).setText(mPage.getTitle());
		deduction_80ccg = ((TextCurrency) localView.findViewById(R.id.deduction_80ccg));
		deduction_80ccg.setValueText(getFormattedNumber("deduction_80ccg"));
		deduction_80ccg.setInfo(getString(R.string.a80ccg_title), getString(R.string.a80ccg_info));
		
		deduction_80d = ((TextCurrency) localView.findViewById(R.id.deduction_80d));
		deduction_80d.setValueText(getFormattedNumber("deduction_80d"));
		deduction_80d.setInfo(getString(R.string.a80d_title), getString(R.string.a80d_info));
		
		deduction_80dd = ((TextCurrency) localView.findViewById(R.id.deduction_80dd));
		deduction_80dd.setValueText(getFormattedNumber("deduction_80dd"));
		deduction_80dd.setInfo(getString(R.string.a80dd_title), getString(R.string.a80dd_info));
		
		deduction_80ddb = ((TextCurrency) localView.findViewById(R.id.deduction_80ddb));
		deduction_80ddb.setValueText(getFormattedNumber("deduction_80ddb"));
		deduction_80ddb.setInfo(getString(R.string.a80ddb_title), getString(R.string.a80ddb_info));
		
		return localView;
	}

	public void onViewCreated(View paramView, Bundle paramBundle) {
		super.onViewCreated(paramView, paramBundle);
		deduction_80ccg.setOnClickListener(this);
		deduction_80d.setOnClickListener(this);
		deduction_80dd.setOnClickListener(this);
		deduction_80ddb.setOnClickListener(this);
	}

	public void setUserVisibleHint(boolean paramBoolean) {
		super.setUserVisibleHint(paramBoolean);
	}

	public void onAttach(Activity paramActivity) {
		super.onAttach(paramActivity);
		if (!(paramActivity instanceof PageFragmentCallbacks))
			throw new ClassCastException("Activity must implement PageFragmentCallbacks");
		mCallbacks = ((PageFragmentCallbacks) paramActivity);
	}

	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	public void onDialogNumberSet(int reference, long number, double decimal,
			boolean isNegative, double fullNumber) {
		switch (reference) {
		case R.id.deduction_80ccg:
			deduction_80ccg.setValueText(getFormattedString(number));
			mPage.getData().putLong("deduction_80ccg",
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		case R.id.deduction_80d:
			deduction_80d.setValueText(getFormattedString(number));
			mPage.getData().putLong("deduction_80d",
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		case R.id.deduction_80dd:
			deduction_80dd.setValueText(getFormattedString(number));
			mPage.getData().putLong("deduction_80dd",
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		case R.id.deduction_80ddb:
			deduction_80ddb.setValueText(getFormattedString(number));
			mPage.getData().putLong("deduction_80ddb",
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		}
		getTotal(mPage);
	}

	public void onClick(View view) {
		npb.setReference(view.getId());
		switch (view.getId()) {
		case R.id.deduction_80ccg:
			npb.setMaxNumber(50000);
			npb.setNumber(getNumber("deduction_80ccg")).show();
			break;
		case R.id.deduction_80d:
			npb.setMaxNumber(35000);
			npb.setNumber(getNumber("deduction_80d")).show();
			break;
		case R.id.deduction_80dd:
			npb.setMaxNumber(50000);
			npb.setNumber(getNumber("deduction_80dd")).show();
			break;
		case R.id.deduction_80ddb:
			npb.setMaxNumber(15000);
			npb.setNumber(getNumber("deduction_80ddb")).show();
			break;
		}
	}

	public String getFormattedNumber(String paramString) {
		return Utils.getFormattedString(mPage.getData().getLong(paramString));
	}

	public String getNumber(String paramString) {
		return String.valueOf(mPage.getData().getLong(paramString));
	}

	public static int getTotal(DeductionThreePage mPage) {
		int total = 0;
        for (String key : mPage.getData().keySet()) {
        	if(key == DeductionThreePage.DEDUCTION_THREE_TOTAL){
        		continue;
        	}
        	total += mPage.getData().getLong(key, 0);
        }
		mPage.getData().putLong(DeductionThreePage.DEDUCTION_THREE_TOTAL, total);
		mPage.notifyDataChanged();
		return total;
	}
}