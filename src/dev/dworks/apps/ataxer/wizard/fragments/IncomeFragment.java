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
import dev.dworks.apps.ataxer.ui.TextCurrency;
import dev.dworks.apps.ataxer.wizard.pages.DeductionThreePage;
import dev.dworks.apps.ataxer.wizard.pages.DeductionTwoPage;
import dev.dworks.apps.ataxer.wizard.pages.DetailsPage;
import dev.dworks.apps.ataxer.wizard.pages.ExemptionPage;
import dev.dworks.apps.ataxer.wizard.pages.IncomePage;
import dev.dworks.betterpickers.numberpicker.NumberPickerBuilder;
import dev.dworks.betterpickers.numberpicker.NumberPickerDialogFragment.NumberPickerDialogHandler;
import dev.dworks.libs.awizard.model.PageFragmentCallbacks;

public class IncomeFragment extends Fragment 
	implements NumberPickerDialogHandler, OnClickListener{
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private IncomePage mPage;
	private ExemptionPage mExemptionPage;
	private DeductionTwoPage mDeductionTwoPage;
	private DeductionThreePage mDeductionThreePage;
	private DetailsPage mDetailsPage;
	private NumberPickerBuilder npb;
    
    private TextCurrency income_salary_hra;
	private TextCurrency income_other1;
	private TextCurrency income_salary_conveyance;
	private TextCurrency income_salary_lta;
	private TextCurrency income_salary;
	private TextCurrency income_salary_basic;
	private TextCurrency income_salary_medical;
	private TextCurrency income_salary_pf;

    public static IncomeFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        IncomeFragment fragment = new IncomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public IncomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (IncomePage) mCallbacks.onGetPage(mKey);
        mDetailsPage = (DetailsPage) mCallbacks.onGetPage(DetailsPage.TITLE);
        mExemptionPage = (ExemptionPage) mCallbacks.onGetPage(ExemptionPage.TITLE);
        mDeductionTwoPage = (DeductionTwoPage) mCallbacks.onGetPage(DeductionTwoPage.TITLE);
        mDeductionThreePage = (DeductionThreePage) mCallbacks.onGetPage(DeductionThreePage.TITLE);
        
		npb = new NumberPickerBuilder()
		.setFragmentManager(getChildFragmentManager())
		.setStyleResId(R.style.BetterPickersDialogFragment_Light)
		.setTargetFragment(IncomeFragment.this)
		.setPlusMinusVisibility(View.INVISIBLE)
		.setDecimalVisibility(View.INVISIBLE)
		.setLabelText(getString(R.string.rupee));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_income, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        income_salary = (TextCurrency) rootView.findViewById(R.id.income_salary);
        income_salary.setValueText(getFormattedNumber(IncomePage.INCOME_SALARY_DATA_KEY));
        income_salary.setInfo(getString(R.string.salary_title), getString(R.string.salary_info));
        
        income_salary_basic = (TextCurrency) rootView.findViewById(R.id.income_salary_basic);
        income_salary_basic.setValueText(getFormattedNumber(IncomePage.INCOME_SALARY_BASIC_DATA_KEY));
        income_salary_basic.setInfo(getString(R.string.basic_title), getString(R.string.basic_info));
        
        income_salary_hra = ((TextCurrency) rootView.findViewById(R.id.income_salary_hra));
        income_salary_hra.setValueText(getFormattedNumber(IncomePage.INCOME_SALARY_HRA_DATA_KEY));
        income_salary_hra.setInfo(getString(R.string.hra_title), getString(R.string.hra_info));
        
        income_salary_conveyance = ((TextCurrency) rootView.findViewById(R.id.income_salary_conveyance));
        income_salary_conveyance.setValueText(getFormattedNumber(IncomePage.INCOME_SALARY_CONVEYANCE_DATA_KEY));
        income_salary_conveyance.setInfo(getString(R.string.conveyance_title), getString(R.string.conveyance_info));
        
        income_salary_lta = ((TextCurrency) rootView.findViewById(R.id.income_salary_lta));
        income_salary_lta.setValueText(getFormattedNumber(IncomePage.INCOME_SALARY_LTA_DATA_KEY));
        income_salary_lta.setInfo(getString(R.string.lta_title), getString(R.string.lta_info));
        
        income_salary_medical = ((TextCurrency) rootView.findViewById(R.id.income_salary_medical));
        income_salary_medical.setValueText(getFormattedNumber(IncomePage.INCOME_SALARY_MEDICAL_DATA_KEY));
        income_salary_medical.setInfo(getString(R.string.medical_title), getString(R.string.medical_info));
        
        income_salary_pf = ((TextCurrency) rootView.findViewById(R.id.income_salary_pf));
        income_salary_pf.setValueText(getFormattedNumber(IncomePage.INCOME_SALARY_PF_DATA_KEY));
        income_salary_pf.setInfo(getString(R.string.pf_title), getString(R.string.pf_info));
        
        income_other1 = ((TextCurrency) rootView.findViewById(R.id.income_other1));
        income_other1.setValueText(getFormattedNumber(IncomePage.INCOME_OTHER_DATA_KEY));
        income_other1.setInfo(getString(R.string.other_title), getString(R.string.other_info));
        
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        income_salary.setOnClickListener(this);
        income_salary_basic.setOnClickListener(this);
        income_salary_hra.setOnClickListener(this);
        income_salary_conveyance.setOnClickListener(this);
        income_salary_lta.setOnClickListener(this);
        income_salary_medical.setOnClickListener(this);
        income_salary_pf.setOnClickListener(this);
        income_other1.setOnClickListener(this);
    }

	@Override
	public void onDialogNumberSet(int reference, long number, double decimal,
			boolean isNegative, double fullNumber) {
		
		switch (reference) {
		case R.id.income_salary:
			income_salary.setValueText(getFormattedString(number));
			mPage.getData().putLong(IncomePage.INCOME_SALARY_DATA_KEY, (number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;

		case R.id.income_salary_basic:
			income_salary_basic.setValueText(getFormattedString(number));
			mPage.getData().putLong(IncomePage.INCOME_SALARY_BASIC_DATA_KEY, (number != 0) ? number : 0);
			boolean isMetro = mDetailsPage.getData().getInt(DetailsPage.METRO_DATA_KEY) == 1;
			long income_salary = mPage.getData().getLong(IncomePage.INCOME_SALARY_DATA_KEY, 0);
			long income_hra = (number * (isMetro ? 50 : 40)) / 100;
			if (income_salary >= number + income_hra) {
				income_salary_hra.setValueText(getFormattedString(income_hra));
				mPage.getData().putLong(IncomePage.INCOME_SALARY_HRA_DATA_KEY, (income_hra != 0) ? income_hra : 0);
				mPage.notifyDataChanged();
			}
			break;

		case R.id.income_salary_hra:
			income_salary_hra.setValueText(getFormattedString(number));
			mPage.getData().putLong(IncomePage.INCOME_SALARY_HRA_DATA_KEY, (number != 0) ? number : 0);
			mPage.notifyDataChanged();

			// FIXME: Calculate HRA Exemption
			break;

		case R.id.income_salary_conveyance:
			income_salary_conveyance.setValueText(getFormattedString(number));
			mPage.getData().putLong(IncomePage.INCOME_SALARY_CONVEYANCE_DATA_KEY, (number != 0) ? number : 0);
			mPage.notifyDataChanged();

			long income_conveyance = number;
			if (number > 96000) {
				income_conveyance = 96000;
			}
			mExemptionPage.getData().putLong(ExemptionPage.ALLOWANCE_TRANSPORT_DATA_KEY, (income_conveyance != 0) ? income_conveyance : 0);
			mExemptionPage.notifyDataChanged();

			ExemptionFragment.getTotal(mExemptionPage);
			break;

		case R.id.income_salary_lta:
			income_salary_lta.setValueText(getFormattedString(number));
			mPage.getData().putLong(IncomePage.INCOME_SALARY_LTA_DATA_KEY, (number != 0) ? number : 0);
			mPage.notifyDataChanged();

			long income_lta = number;
			if (number > 50000) {
				income_lta = 50000;
			}
			mExemptionPage.getData().putLong(ExemptionPage.ALLOWANCE_LTA_DATA_KEY, (income_lta != 0) ? income_lta : 0);
			mExemptionPage.notifyDataChanged();
			ExemptionFragment.getTotal(mExemptionPage);
			break;

		case R.id.income_salary_medical:
			income_salary_medical.setValueText(getFormattedString(number));
			mPage.getData().putLong(IncomePage.INCOME_SALARY_MEDICAL_DATA_KEY, (number != 0) ? number : 0);
			mPage.notifyDataChanged();

			long income_medical = number;
			if (number > 15000) {
				income_medical = 15000;
			}
			mDeductionThreePage.getData().putLong(DeductionThreePage.DEDUCTION_80DDB_DATA_KEY, (income_medical != 0) ? income_medical : 0);
			mDeductionThreePage.notifyDataChanged();
			DeductionThreeFragment.getTotal(mDeductionThreePage);
			break;

		case R.id.income_salary_pf:
			income_salary_pf.setValueText(getFormattedString(number));
			mPage.getData().putLong(IncomePage.INCOME_SALARY_PF_DATA_KEY, (number != 0) ? number : 0);
			mPage.notifyDataChanged();

			mDeductionTwoPage.getData().putLong(DeductionTwoPage.DEDUCTION_EPF_DATA_KEY, (number != 0) ? number : 0);
			//mDeductionTwoPage.getData().putLong(DeductionTwoPage.DEDUCTION_PPF_DATA_KEY, (number != 0) ? number / 2 : 0);
			mDeductionTwoPage.notifyDataChanged();
			DeductionTwoFragment.getTotal(mDeductionTwoPage);
			break;

		case R.id.income_other1:
			income_other1.setValueText(getFormattedString(number));
			mPage.getData().putLong(IncomePage.INCOME_OTHER_DATA_KEY, (number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;

		}
	}

	@Override
	public void onClick(View v) {
		npb.setReference(v.getId());
		npb.setMaxNumber((int) (mPage.getData().getLong(IncomePage.INCOME_SALARY_DATA_KEY, 0) - getTotal()));
		npb.setMaxVisibility(View.INVISIBLE);
		switch (v.getId()) {
		case R.id.income_salary:
			npb.setMaxNumber(null);
			npb.setNumber(getNumber(IncomePage.INCOME_SALARY_DATA_KEY)).show();
			break;

		case R.id.income_salary_basic:
			npb.setNumber(getNumber(IncomePage.INCOME_SALARY_BASIC_DATA_KEY)).show();
			break;

		case R.id.income_salary_hra:
			npb.setNumber(getNumber(IncomePage.INCOME_SALARY_HRA_DATA_KEY)).show();
			break;

		case R.id.income_salary_conveyance:
			npb.setNumber(getNumber(IncomePage.INCOME_SALARY_CONVEYANCE_DATA_KEY)).show();
			break;
		case R.id.income_salary_lta:
			npb.setNumber(getNumber(IncomePage.INCOME_SALARY_LTA_DATA_KEY)).show();
			break;
		case R.id.income_salary_medical:
			npb.setNumber(getNumber(IncomePage.INCOME_SALARY_MEDICAL_DATA_KEY)).show();
			break;

		case R.id.income_salary_pf:
			npb.setNumber(getNumber(IncomePage.INCOME_SALARY_PF_DATA_KEY)).show();
			break;

		case R.id.income_other1:
			npb.setMaxNumber(null);
			npb.setNumber(getNumber(IncomePage.INCOME_OTHER_DATA_KEY)).show();
			break;
		}
	}
	
	public String getFormattedNumber(String key){
		return getFormattedString(mPage.getData().getLong(key));
	}
	
	public String getNumber(String key){
		return String.valueOf(mPage.getData().getLong(key));
	}
	
	public int getTotal(){
		int total = 0;
        for (String key : mPage.getData().keySet()) {
        	if(key == IncomePage.INCOME_OTHER_DATA_KEY || key == IncomePage.INCOME_SALARY_DATA_KEY){
        		continue;
        	}
        	total += mPage.getData().getLong(key, 0);
        }
		return total;
	}
}