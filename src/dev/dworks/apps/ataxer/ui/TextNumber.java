package dev.dworks.apps.ataxer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher.ViewFactory;
import dev.dworks.apps.ataxer.R;

public class TextNumber extends LinearLayout {
	private Context mContext;
	private TextSwitcher mSwitcher;

	public TextNumber(Context context) {
		super(context);
		init(context, null, 0);
	}

	public TextNumber(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public TextNumber(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		mContext = context;
		
		LayoutInflater.from(context).inflate(R.layout.view_text_number, this, true);
/*		if(isInEditMode()){
			return;
		}*/
		
        mSwitcher = (TextSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(new ViewFactory() {
			
			@Override
			public View makeView() {
				
				LayoutInflater inflater = LayoutInflater.from(mContext);
				TextPlus textView = (TextPlus) inflater.inflate(R.layout.view_text_plus, null);
	            textView.setPrefix(mContext.getString(R.string.rupee), 0);
		        return textView;
			}
		});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	public void setValueText(String valueText) {
		if(null == valueText){
			return;
		}
		
		mSwitcher.setText(valueText);
		/*if(!TextUtils.isDigitsOnly(valueText)){
			mSwitcher.setText(valueText);
			return;
		}
		final int number = Integer.valueOf(valueText);
		if(null != mSwitcher && !TextUtils.isEmpty(valueText)){
	        new AsyncTask<Void, Void, Void>(){
	        	
				private int mCounter;

				@Override
				protected Void doInBackground(Void... params) {
	            	for (int i = 0; i <= number; i+=100) {
	            		try {
	            			mCounter+=100;
							Thread.sleep(0, 300000);
	            			publishProgress();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					return null;
				}
				
	        	@Override
	        	protected void onProgressUpdate(Void... values) {
	    			mSwitcher.setText(String.valueOf(mCounter));
	        		super.onProgressUpdate(values);
	        	}
	        	
	        	@Override
	        	protected void onPostExecute(Void result) {
	        		mSwitcher.setText(String.valueOf(mCounter));
	        		super.onPostExecute(result);
	        	}
	        	
	        }.execute();
		}*/
	}
}