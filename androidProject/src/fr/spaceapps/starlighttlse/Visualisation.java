package fr.spaceapps.starlighttlse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Visualisation extends Activity {

	private final static String TAG = "Visualisation";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualisation);
		
		Button menubutton = (Button)findViewById(R.id.menuButton);
		menubutton.setOnClickListener(new MenuOnClickListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.visualisation, menu);
		return true;
	}
	
	private class MenuOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Log.d(TAG, "Menu button had been clicked");
		}
		
	}

}
