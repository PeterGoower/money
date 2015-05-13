package com.guu.money.views;

import com.guu.money.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

public class EditListView extends ListView {
	private Context context;

	public EditListView(Context context) {
		super(context);
		this.context = context;
	}

	public EditListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	public boolean dispatchKeyEventPreIme(KeyEvent event) {
		if (context != null) {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm.isActive() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				for (int i = 0; i < getChildCount(); i++) {
					View view = getChildAt(i);
					EditText editText = (EditText) view
							.findViewById(R.id.name);
					editText.clearFocus();
				}
			}
		}
		return super.dispatchKeyEventPreIme(event);
	}

}
