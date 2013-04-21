package fr.spaceapps.starlighttlse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startVisualisationActivity();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void startVisualisationActivity() {
    	Intent intent = new Intent(this, Visualisation.class);
        
        startActivity(intent);
        finish();
    }
    
    
    //Buttons
    public void startGame(View v) {
    	startVisualisationActivity();
    }
    public void startOptions(View v) {
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(this, R.string.not_implemented, duration);
    	toast.show();
    }
    public void startStory(View v) {
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(this, R.string.not_implemented, duration);
    	toast.show();
    }
}
