package com.dhl.shoppingCenter;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

public class ProcessDialog extends Dialog {
	private TextView title;

	public ProcessDialog(Context context, String strtitle, String strbody) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.customdialog);
		setCancelable(true);
		title = (TextView) findViewById(R.id.txtdialogtext);
		title.setText(strbody.toString());
		show();
	}
}
