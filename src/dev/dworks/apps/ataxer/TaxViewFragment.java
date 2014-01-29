package dev.dworks.apps.ataxer;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import dev.dworks.apps.ataxer.entity.TaxCalculation.TaxCalculationColumns;
import dev.dworks.apps.ataxer.misc.Utils;
import dev.dworks.apps.ataxer.ui.TextNumber;
import dev.dworks.apps.ataxer.ui.TextPlus;
import dev.dworks.apps.ataxer.wizard.TaxCalculationActivity;
import dev.dworks.apps.ataxer.wizard.pages.TaxPage;
import dev.dworks.libs.awizard.model.PageFragmentCallbacks;
import dev.dworks.libs.actionbarplus.app.ActionBarContentFragment;

public class TaxViewFragment extends ActionBarContentFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final String ARG_KEY = "key";
	//private static final String TAG = "TaxViewFragment";
	private TextNumber allowance;
	private TextView category;
	private TextNumber deduction;
	private TextNumber educational_cess;
	private TextView financial_yr;
	private TextNumber income;
	private int layoutId;
	private PageFragmentCallbacks mCallbacks;
	private String mKey;
	private TaxPage mPage;
	private ProgressBar progressBar;
	private TextNumber rebate;
	private View rebate_container;
	private TextNumber surcharge;
	private View surcharge_container;
	private TextNumber tax_payable;
	private TextNumber taxable_income;
	private TextPlus total_tax_payable;
	private Uri uri;
	private View contents;
	private View seperator;
	private static final String[] PROJECTION = new String[]{
		TaxCalculationColumns._ID,
		TaxCalculationColumns.NAME,
		TaxCalculationColumns.CATEGORY,
		TaxCalculationColumns.FINANCIAL_YR,
		TaxCalculationColumns.TOTAL_INCOME,
		TaxCalculationColumns.TOTAL_ALLOWANCE,
		TaxCalculationColumns.TOTAL_DEDUCATION,
		TaxCalculationColumns.TOTAL_TAXABLE_INCOME,
		TaxCalculationColumns.TAX_PAYABLE,
		TaxCalculationColumns.SURCHARGE,
		TaxCalculationColumns.REBATE,
		TaxCalculationColumns.EDUCATIONAL_CESS,
		TaxCalculationColumns.TOTAL_TAX_PAYABLE,
	};

	public static TaxViewFragment create(String key) {
		Bundle bundle = new Bundle();
		bundle.putString(ARG_KEY, key);
		TaxViewFragment localTaxViewFragment = new TaxViewFragment();
		localTaxViewFragment.setArguments(bundle);
		return localTaxViewFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		uri = (Uri) bundle.getParcelable(Utils.DATA);
		setHasOptionsMenu(true);
		layoutId = R.layout.fragment_tax_view;
		if (uri == null) {
			mKey = bundle.getString(ARG_KEY);
			mPage = (TaxPage) mCallbacks.onGetPage(mKey);
			setHasOptionsMenu(false);
			layoutId = R.layout.fragment_page_tax;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(layoutId, container, false);
		financial_yr = ((TextView) root.findViewById(R.id.financial_yr));
		category = ((TextView) root.findViewById(R.id.category));
		income = ((TextNumber) root.findViewById(R.id.income));
		allowance = ((TextNumber) root.findViewById(R.id.allowance));
		deduction = ((TextNumber) root.findViewById(R.id.deduction));
		taxable_income = ((TextNumber) root.findViewById(R.id.taxable_income));
		tax_payable = ((TextNumber) root.findViewById(R.id.tax_payable));
		educational_cess = ((TextNumber) root.findViewById(R.id.educational_cess));
		total_tax_payable = ((TextPlus) root.findViewById(R.id.total_tax_payable));
		surcharge_container = root.findViewById(R.id.surcharge_container);
		rebate_container = root.findViewById(R.id.rebate_container);
		rebate = ((TextNumber) root.findViewById(R.id.rebate));
		surcharge = ((TextNumber) root.findViewById(R.id.surcharge));
		progressBar = ((ProgressBar) root.findViewById(R.id.progressBar));
		if (uri == null) {
			((TextView) root.findViewById(android.R.id.title)).setText(mPage.getTitle());
			progressBar.setVisibility(View.GONE);
		}
		progressBar.setVisibility(View.GONE);
		contents = root.findViewById(R.id.contents);
		seperator = root.findViewById(R.id.seperator);
		return root;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		total_tax_payable.setPrefix(getString(R.string.rupee), getResources().getColor(R.color.blue));
		if (uri != null) {
			setEmptyText(getText(R.string.empty_list));
			setContentShown(false);
			getLoaderManager().initLoader(0, null, this);
		}
		else{
			seperator.setVisibility(View.VISIBLE);
			contents.setBackgroundResource(R.color.transparent);
			ContentValues contentValues = ((TaxCalculationActivity) getActivity()).getContentValues();
			financial_yr.setText(Utils.getFinancialYr(contentValues.getAsInteger(TaxCalculationColumns.FINANCIAL_YR).intValue()));
			category.setText(Utils.getCategory(contentValues.getAsInteger(TaxCalculationColumns.CATEGORY).intValue()));
			income.setValueText(Utils.getFormattedString(contentValues.getAsLong(TaxCalculationColumns.TOTAL_INCOME).longValue()));
			allowance.setValueText(Utils.getFormattedString(contentValues.getAsLong(TaxCalculationColumns.TOTAL_ALLOWANCE).longValue()));
			deduction.setValueText(Utils.getFormattedString(contentValues.getAsLong(TaxCalculationColumns.TOTAL_DEDUCATION).longValue()));
			taxable_income.setValueText(Utils.getFormattedString(contentValues.getAsLong(TaxCalculationColumns.TOTAL_TAXABLE_INCOME).longValue()));
			tax_payable.setValueText(Utils.getFormattedString(contentValues.getAsLong(TaxCalculationColumns.TAX_PAYABLE).longValue()));
			rebate.setValueText(Utils.getFormattedString(contentValues.getAsLong(TaxCalculationColumns.REBATE).longValue()));
			educational_cess.setValueText(Utils.getFormattedString(contentValues.getAsLong(TaxCalculationColumns.EDUCATIONAL_CESS).longValue()));
			total_tax_payable.setText(Utils.getFormattedString(contentValues.getAsLong(TaxCalculationColumns.TOTAL_TAX_PAYABLE).longValue()));
			int surchargeValue = contentValues.getAsInteger(TaxCalculationColumns.SURCHARGE).intValue();
			int rebateValue = contentValues.getAsInteger(TaxCalculationColumns.REBATE).intValue();
			if (surchargeValue != 0) {
				surcharge_container.setVisibility(0);
				surcharge.setValueText(String.valueOf(surchargeValue));
			}
			if (rebateValue != 0) {
				rebate_container.setVisibility(0);
				rebate.setValueText(String.valueOf(rebateValue));
			}
			setContentShown(true);
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!(activity instanceof PageFragmentCallbacks)){
			throw new ClassCastException("Activity must implement PageFragmentCallbacks");
		}
		mCallbacks = ((PageFragmentCallbacks) activity);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		return new CursorLoader(getActivity(), uri, PROJECTION, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor.moveToFirst()) {
			getActionBarActivity().getSupportActionBar().setTitle(cursor.getString(cursor.getColumnIndex(TaxCalculationColumns.NAME)));
			getActionBarActivity().getSupportActionBar().setSubtitle("Tax Details");
			financial_yr.setText(Utils.getFinancialYr(cursor.getInt(cursor.getColumnIndex(TaxCalculationColumns.FINANCIAL_YR))));
			category.setText(Utils.getCategory(cursor.getInt(cursor.getColumnIndex(TaxCalculationColumns.CATEGORY))));
			income.setValueText(Utils.getFormattedString(cursor.getLong(cursor.getColumnIndex(TaxCalculationColumns.TOTAL_INCOME))));
			allowance.setValueText(Utils.getFormattedString(cursor.getLong(cursor.getColumnIndex(TaxCalculationColumns.TOTAL_ALLOWANCE))));
			deduction.setValueText(Utils.getFormattedString(cursor.getLong(cursor.getColumnIndex(TaxCalculationColumns.TOTAL_DEDUCATION))));
			taxable_income.setValueText(Utils.getFormattedString(cursor.getLong(cursor.getColumnIndex(TaxCalculationColumns.TOTAL_TAXABLE_INCOME))));
			tax_payable.setValueText(Utils.getFormattedString(cursor.getLong(cursor.getColumnIndex(TaxCalculationColumns.TAX_PAYABLE))));
			rebate.setValueText(Utils.getFormattedString(cursor.getLong(cursor.getColumnIndex(TaxCalculationColumns.REBATE))));
			educational_cess.setValueText(Utils.getFormattedString(cursor.getLong(cursor.getColumnIndex(TaxCalculationColumns.EDUCATIONAL_CESS))));
			total_tax_payable.setText(Utils.getFormattedString(cursor.getLong(cursor.getColumnIndex(TaxCalculationColumns.TOTAL_TAX_PAYABLE))));
			long surchargeValue = cursor.getLong(cursor.getColumnIndex(TaxCalculationColumns.SURCHARGE));
			long rebateValue = cursor.getLong(cursor.getColumnIndex(TaxCalculationColumns.REBATE));
			if (0 != surchargeValue) {
				surcharge_container.setVisibility(View.VISIBLE);
				surcharge.setValueText(Utils.getFormattedString(surchargeValue));
			}
			if (0 != rebateValue) {
				rebate_container.setVisibility(View.VISIBLE);
				rebate.setValueText(Utils.getFormattedString(rebateValue));
			}
			setContentShown(true);
		} else {
			setContentShown(false);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}
}