package dev.dworks.apps.ataxer;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import dev.dworks.apps.ataxer.entity.TaxCalculation;
import dev.dworks.apps.ataxer.entity.TaxCalculation.TaxCalculationColumns;
import dev.dworks.apps.ataxer.misc.Utils;
import dev.dworks.apps.ataxer.ui.TextPlus;
import dev.dworks.apps.ataxer.wizard.TaxCalculationActivity;
import dev.dworks.libs.actionbarplus.app.ActionBarListFragment;
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter;
import dev.dworks.libs.astickyheader.SimpleSectionedListAdapter.Section;

public class TaxListFragment extends ActionBarListFragment 
	implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
	//private static final String TAG = "TaxListFragment";
	private SimpleCursorAdapter mAdapter;
	private SimpleSectionedListAdapter mSimpleSectionedListAdapter;
	private SparseIntArray sectionDates = new SparseIntArray();
	private List<Section> sections = new ArrayList<Section>();

	private static final String[] PROJECTION = new String[]{
		TaxCalculationColumns._ID,
		TaxCalculationColumns.NAME,
		TaxCalculationColumns.CATEGORY,
		TaxCalculationColumns.FINANCIAL_YR,
		TaxCalculationColumns.TOTAL_TAX_PAYABLE,
	};
	
	private static final String[] FROM_COLUMNS = new String[]{
		TaxCalculationColumns.NAME,
		TaxCalculationColumns.CATEGORY,
		TaxCalculationColumns.FINANCIAL_YR,
		TaxCalculationColumns.TOTAL_TAX_PAYABLE,
	};
	
	private static final int[] TO_FIELDS = new int[]{
		R.id.name,
		R.id.category,
		R.id.financial_yr,
		R.id.tax_payable,
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tax_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		getView().findViewById(R.id.internalEmpty).setOnClickListener(this);
		mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.item_tax_list, null, FROM_COLUMNS, TO_FIELDS, 0);
		mAdapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View paramView, Cursor paramCursor, int paramInt) {
				if (paramInt == 2) {
					((TextView) paramView).setText(Utils.getCategory(paramCursor.getInt(paramInt)));
					return true;
				} else if (paramInt == 4) {
					TextPlus localTextViewPlus = (TextPlus) paramView;
					localTextViewPlus.setPrefix(getString(R.string.rupee));
					localTextViewPlus.setText(Utils.getFormattedString(paramCursor.getLong(paramInt)));
					return true;
				}
				return false;
			}
		});
		mSimpleSectionedListAdapter = new SimpleSectionedListAdapter(getActivity(), mAdapter, R.layout.header, R.id.header);
		setListAdapter(mSimpleSectionedListAdapter);
		setEmptyText(getText(R.string.empty_list));
		setListShown(false);
		if (savedInstanceState == null){
			getLoaderManager().initLoader(0, null, this);
		}
		super.onActivityCreated(savedInstanceState);
	}

	private void assignSeparatorPositions(Cursor cursor) {
		sections = new ArrayList<Section>();
		sectionDates = new SparseIntArray();
		if (cursor != null && cursor.getCount() > 0){
			cursor.moveToFirst();
			do{
				int year = cursor.getInt(cursor.getColumnIndex(TaxCalculationColumns.FINANCIAL_YR));
				String str = Utils.getFinancialYr(year);
				if (sectionDates.get(Integer.valueOf(year), -1) == -1) {
					sectionDates.put(Integer.valueOf(year), Integer.valueOf(cursor.getPosition()));
					sections.add(new Section(cursor.getPosition(), str));
				}
			}while (cursor.moveToNext());
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.tax_list, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			startActivity(new Intent(getActivity().getApplicationContext(), TaxCalculationActivity.class));
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onListItemClick(ListView listview, View view, int position, long id) {
		super.onListItemClick(listview, view, position, id);
		if (Build.VERSION.SDK_INT >= 16){
			ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view, Utils.drawViewOntoBitmap(view), 0, 0).toBundle();
		}
		Cursor localCursor = mAdapter.getCursor();
		if (localCursor != null){
			int i = localCursor.getInt(0);
			if (i == 0) {
				Log.e("TaxListFragment", "Attempt to launch entry with null link");
			} else {
				Log.i("TaxListFragment", "Opening URL: " + i);
				Intent localIntent = new Intent(getActivity(), TaxViewActivity.class);
				localIntent.setData(ContentUris.withAppendedId(TaxCalculation.CONTENT_URI, i));
				startActivity(localIntent);
			}	
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int paramInt, Bundle bundle) {
		return new CursorLoader(getActivity(),
				TaxCalculation.CONTENT_URI,
				PROJECTION,
				null,
				null,
				TaxCalculationColumns.FINANCIAL_YR + " ASC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		setEmptyText(getText(R.string.empty_list));
		assignSeparatorPositions(cursor);
		mSimpleSectionedListAdapter.setSections(sections.toArray(new Section[0]));
		mAdapter.changeCursor(cursor);
		setListShownNoAnimation(true);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.changeCursor(null);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.internalEmpty:
			startActivity(new Intent(getActivity().getApplicationContext(), TaxCalculationActivity.class));
		}
	}
}