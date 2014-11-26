package com.example.inventaria2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity implements OnKeyListener, OnTouchListener {
	
	/**
	 * The code to tell the handler to stop showing the splash 
	 */
	private static final int STOPSPLASH = 0;
	// time in milliseconds
	private static final long SPLASHTIME = 3000;

	/**
	 * The key that indicates if the app has been initiated
	 * before and has not been killed
	 */
	private static final String INICIADO = "INICIADO";
	private TableLayout inventariotable;
	private int idTouchedElement;
	// handler for splash screen
	private Handler splashHandler = new Handler() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STOPSPLASH:
				loadMainActivityXml();
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	// Is this necessary, or can we just use a preferences xml?
	private TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null
				|| !savedInstanceState.containsKey(INICIADO)) {

			setContentView(R.layout.splash);

			Message msg = new Message();
			msg.what = STOPSPLASH;
			splashHandler.sendMessageDelayed(msg, SPLASHTIME);
		} else {
			loadMainActivityXml();
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		outState.putBoolean(INICIADO, true);

		super.onSaveInstanceState(outState);
	}
	
	/**
	 * The code that is called both after the splash is killed, and
	 * during {@code #onCreate()}. This loads the activity's xml file
	 * and initializes some essencial global variables
	 */
	protected void loadMainActivityXml() {
		// remove SplashScreen from view
		// splash.setVisibility(View.GONE);
		setContentView(R.layout.activity_main);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();

		TabSpec tab = tabHost.newTabSpec("tabActivity");
		tab.setContent(R.id.tabActivity);
		tab.setIndicator("Inventario");
		tabHost.addTab(tab);

		tab = tabHost.newTabSpec("tabConfiguration");
		tab.setContent(R.id.tabConfiguration);
		tab.setIndicator("Configuration");
		tabHost.addTab(tab);
		// asigno nuestro objeto tablelayout a una variable
		inventariotable = (TableLayout) findViewById(R.id.tableLayout1);
		this.buildLine();

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if ((event.getAction() == KeyEvent.ACTION_DOWN)
				&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
			Toast.makeText(this, "" + idTouchedElement,
					Toast.LENGTH_LONG).show();
			inventariotable.setNextFocusRightId(inventariotable
					.getNextFocusRightId());
			return true;

		}
		return false;
	}

	private void buildTable(int rows, int cols) {

		// outer for loop
		for (int i = 1; i <= rows; i++) {

			TableRow row = new TableRow(this);
			row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			// inner for loop
			for (int j = 1; j <= cols; j++) {

				EditText et = new EditText(this);
				et.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				et.setBackgroundResource(R.drawable.cell_shape);
				et.setPadding(5, 5, 5, 5);
				et.setText("R " + i + ", C" + j);
				et.setOnKeyListener(this);

				row.addView(et);

			}

			inventariotable.addView(row);

		}
	}
	
	private void buildLine() {

		// outer for loop
	

			TableRow row = new TableRow(this);
			row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			// inner for loop
			for (int j = 1; j <= 5; j++) {

				EditText et = new EditText(this);
				et.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				et.setBackgroundResource(R.drawable.cell_shape);
				et.setPadding(5, 5, 5, 5);
				//et.setText("R " + i + ", C" + j);
				et.setOnKeyListener(this);

				row.addView(et);

			}

			inventariotable.addView(row);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			tabHost.setCurrentTabByTag("tabConfiguration");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		idTouchedElement = v.getId();
		return false;
	}
}
