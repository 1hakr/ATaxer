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
import android.widget.Toast;


import dev.dworks.apps.ataxer.R;
import dev.dworks.apps.ataxer.misc.Utils;
import dev.dworks.apps.ataxer.ui.TextCurrency;
import dev.dworks.apps.ataxer.wizard.pages.DeductionFourPage;
import dev.dworks.apps.ataxer.wizard.pages.IncomePage;
import dev.dworks.betterpickers.numberpicker.NumberPickerBuilder;
import dev.dworks.betterpickers.numberpicker.NumberPickerDialogFragment.NumberPickerDialogHandler;
import dev.dworks.lib.wizard.model.PageFragmentCallbacks;

public class DeductionFourFragment extends Fragment implements NumberPickerDialogHandler, OnClickListener {
	private static final String ARG_KEY = "key";
	private TextCurrency deduction_80e;
	private TextCurrency deduction_80g;
	private TextCurrency deduction_80gg;
	private TextCurrency deduction_80tta;
	private TextCurrency deduction_80u;
	private PageFragmentCallbacks mCallbacks;
	private IncomePage mIncomePage;
	private String mKey;
	private DeductionFourPage mPage;
	private NumberPickerBuilder npb;
	private TextCurrency deduction_80ee;

	public static DeductionFourFragment create(String paramString) {
		Bundle localBundle = new Bundle();
		localBundle.putString(ARG_KEY, paramString);
		DeductionFourFragment localDeductionFourFragment = new DeductionFourFragment();
		localDeductionFourFragment.setArguments(localBundle);
		return localDeductionFourFragment;
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		mKey = getArguments().getString(ARG_KEY);
		mPage = ((DeductionFourPage) mCallbacks.onGetPage(mKey));
		mIncomePage = ((IncomePage) mCallbacks.onGetPage("Income"));
		npb = new NumberPickerBuilder().setFragmentManager(getChildFragmentManager())
				.setStyleResId(R.style.BetterPickersDialogFragment_Light)
				.setTargetFragment(DeductionFourFragment.this)
				.setPlusMinusVisibility(View.INVISIBLE)
				.setDecimalVisibility(View.INVISIBLE)
				.setLabelText(getString(R.string.rupee));
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.fragment_page_deduction_four, paramViewGroup, false);
		((TextView) localView.findViewById(android.R.id.title)).setText(mPage.getTitle());

		deduction_80e = ((TextCurrency) localView.findViewById(R.id.deduction_80e));
		deduction_80e.setValueText(getFormattedNumber("deduction_80e"));
		deduction_80e.setInfo(getString(R.string.a80e_title), getString(R.string.a80e_info));

		deduction_80ee = ((TextCurrency) localView.findViewById(R.id.deduction_80ee));
		deduction_80ee.setValueText(getFormattedNumber("deduction_80ee"));
		deduction_80ee.setInfo(getString(R.string.a80ee_title), getString(R.string.a80ee_info));
		
		deduction_80u = ((TextCurrency) localView.findViewById(R.id.deduction_80u));
		deduction_80u.setValueText(getFormattedNumber("deduction_80u"));
		deduction_80u.setInfo(getString(R.string.a80u_title), getString(R.string.a80u_info));
		
		deduction_80g = ((TextCurrency) localView.findViewById(R.id.deduction_80g));
		deduction_80g.setValueText(getFormattedNumber("deduction_80g"));
		deduction_80g.setInfo(getString(R.string.a80g_title), getString(R.string.a80g_info));
		
		deduction_80gg = ((TextCurrency) localView.findViewById(R.id.deduction_80gg));
		deduction_80gg.setValueText(getFormattedNumber("deduction_80gg"));
		deduction_80gg.setInfo(getString(R.string.a80gg_title), getString(R.string.a80gg_info));
		
		deduction_80tta = ((TextCurrency) localView.findViewById(R.id.deduction_80tta));
		deduction_80tta.setValueText(getFormattedNumber("deduction_80tta"));
		deduction_80tta.setInfo(getString(R.string.a80tta_title), getString(R.string.a80tta_info));
		
		return localView;
	}

	public void onViewCreated(View paramView, Bundle paramBundle) {
		super.onViewCreated(paramView, paramBundle);
		deduction_80e.setOnClickListener(this);
		deduction_80ee.setOnClickListener(this);
		deduction_80u.setOnClickListener(this);
		deduction_80g.setOnClickListener(this);
		deduction_80gg.setOnClickListener(this);
		deduction_80tta.setOnClickListener(this);
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
		case R.id.deduction_80e:
			deduction_80e.setValueText(getFormattedString(number));
			mPage.getData().putLong("deduction_80e",
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		case R.id.deduction_80ee:
			deduction_80ee.setValueText(getFormattedString(number));
			mPage.getData().putLong("deduction_80ee",
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		case R.id.deduction_80u:
			deduction_80u.setValueText(getFormattedString(number));
			mPage.getData().putLong("deduction_80u",
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		case R.id.deduction_80g:
			deduction_80g.setValueText(getFormattedString(number));
			mPage.getData().putLong("deduction_80g",
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		case R.id.deduction_80gg:
			deduction_80gg.setValueText(getFormattedString(number));
			mPage.getData().putLong("deduction_80gg",
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		case R.id.deduction_80tta:
			deduction_80tta.setValueText(getFormattedString(number));
			mPage.getData().putLong("deduction_80tta",
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		}
		mPage.getData().putLong("deduction_four_total", getTotal());
		mPage.notifyDataChanged();
	}
	
	public void onClick(View view) {
		npb.setReference(view.getId());
		switch (view.getId()) {
		case R.id.deduction_80e:
			npb.setMaxNumber(40000);
			npb.setNumber(getNumber("deduction_80e")).show();
			break;
		case R.id.deduction_80ee:
			npb.setMaxNumber(100000);
			npb.setNumber(getNumber("deduction_80ee")).show();
			break;
		case R.id.deduction_80u:
			npb.setMaxNumber(100000);
			npb.setNumber(getNumber("deduction_80u")).show();
			break;
		case R.id.deduction_80g:
			npb.setMaxNumber(5000);
			npb.setNumber(getNumber("deduction_80g")).show();
			break;
		case R.id.deduction_80gg:
			if (mIncomePage.getData().getLong(IncomePage.INCOME_SALARY_HRA_DATA_KEY) != 0) {
				Toast.makeText(getActivity(), "Not allowed. HRA is entered.", Toast.LENGTH_SHORT).show();
			} else {
				npb.setMaxNumber(24000);
				npb.setNumber(getNumber("deduction_80gg")).show();
			}
			break;
		case R.id.deduction_80tta:
			npb.setMaxNumber(10000);
			npb.setNumber(getNumber("deduction_80tta")).show();
			break;
		}
	}	

	public String getFormattedNumber(String paramString) {
		return Utils.getFormattedString(mPage.getData().getLong(paramString));
	}

	public String getNumber(String paramString) {
		return String.valueOf(mPage.getData().getLong(paramString));
	}

	public int getTotal() {
		int total = 0;
        for (String key : mPage.getData().keySet()) {
        	if(key == DeductionFourPage.DEDUCTION_FOUR_TOTAL){
        		continue;
        	}
        	total += mPage.getData().getLong(key, 0);
        }
		return total;
	}
}