package dev.dworks.apps.ataxer.ui;

import android.content.Context;
import android.util.AttributeSet;

public class Spinner extends dev.dworks.libs.actionbarplus.widget.Spinner {

	public Spinner(Context context) {
		this(context, null);
	}

	public Spinner(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Spinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
}
