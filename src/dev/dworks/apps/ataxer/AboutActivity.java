/*
 * Copyright 2013 Hari Krishna Dulipudi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.dworks.apps.ataxer;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends ActionBarActivity {

	private String APP_VERSION;
	private int APP_VERSION_CODE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		

    	try {
            final PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
    		APP_VERSION = info.versionName;
    		APP_VERSION_CODE = info.versionCode;
		} catch (NameNotFoundException e) {
			APP_VERSION = "Unknown";
			APP_VERSION_CODE = 0;
			e.printStackTrace();
		}
		TextView logo = (TextView)findViewById(R.id.logo);
		logo.setText(logo.getText() + " v" + APP_VERSION);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_github:
			startActivity(new Intent("android.intent.action.VIEW",
					Uri.parse("https://github.com/DWorkS")));
			break;
		case R.id.action_gplus:
			startActivity(new Intent("android.intent.action.VIEW",
					Uri.parse("https://plus.google.com/109240246596102887385")));
			break;
		case R.id.action_twitter:
			startActivity(new Intent("android.intent.action.VIEW",
					Uri.parse("https://twitter.com/1HaKr")));
			break;
		case R.id.action_feedback:
			Intent intentFeedback = new Intent("android.intent.action.SEND");
			intentFeedback.setType("text/email");
			intentFeedback.putExtra("android.intent.extra.EMAIL", new String[]{"hakr@dworks.in"});
			intentFeedback.putExtra("android.intent.extra.SUBJECT", "ATaxer Feedback");
			startActivity(Intent.createChooser(intentFeedback, "Send Feedback"));
			break;
		case R.id.action_rate:
			Intent intentMarket = new Intent("android.intent.action.VIEW");
			intentMarket.setData(Uri.parse("market://details?id=dev.dworks.apps.ataxer"));
			startActivity(intentMarket);
			break;
		case R.id.action_support:
			Intent intentMarketAll = new Intent("android.intent.action.VIEW");
			intentMarketAll.setData(Uri.parse("market://search?q=pub:D WorkS"));
			startActivity(intentMarketAll);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}