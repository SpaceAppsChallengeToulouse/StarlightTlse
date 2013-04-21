package fr.spaceapps.starlighttlse;

import rajawali.Camera;
import rajawali.RajawaliActivity;
import rajawali.math.Number3D;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;
import fr.spaceapps.starlighttlse.beans.Planet;

public class Visualisation extends RajawaliActivity  implements OnTouchListener{

	private Renderer mRenderer;

	private boolean currentlyZooming;

	private float mPreviousDistance;

	private float mPreviousX;

	private float mPreviousY;
	private final float TRACKBALL_SCALE_FACTOR = 36.0f;
	private final float TOUCH_SCALE_FACTOR = 180f / 3200;

	private final static String TAG = "Visualisation";
	
	int presentationStep = 0;
	boolean hadSomethingBeenDone = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRenderer = new Renderer(this);
		mRenderer.setSurfaceView(mSurfaceView);
		mSurfaceView.setOnTouchListener(this);

		LayoutInflater i = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//CheckedTextView c = (CheckedTextView)i.inflate(R.layout.friend, null);
		View inflate = i.inflate(R.layout.activity_visualisation, null);


		mLayout.addView(inflate);


		ImageButton btn = (ImageButton)findViewById(R.id.menuButton);
		btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				displayDialog();
			}});

		super.setRenderer(mRenderer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.visualisation, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		switch (e.getAction()) {
		

		case MotionEvent.ACTION_DOWN:
			Log.d("DOWN", "DOWN DOWN DOWN");
			hadSomethingBeenDone = false;
			break;
		case MotionEvent.ACTION_UP:
			Log.d("UP", "UP UP UP");
			if(!hadSomethingBeenDone) {
				Log.d("CLICK", "clicked step : "+presentationStep);
				Planet planet = new Planet();
				planet.setX(-135.110709072558f);
				planet.setY(168.755371357685f);
				planet.setZ(-29.104238134762f);
				planet.setName("NAME NAME");
				planet.setSize(12);
				planet.setDensity(12315);
				planet.setTemperature(255);
				planet.setPeriod(555);
				planet.setSemiAxis(44);
				switch(presentationStep) {
				case 0:
					Number3D cameraPosition = planet.getNumber3D();
					cameraPosition.add(1, 2, 1);
					mRenderer.animateTo(cameraPosition, planet.getNumber3D());
					break;
				default:
					displayPlanetDialog(planet);
					break;
				}
				presentationStep++;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			hadSomethingBeenDone = true;
			currentlyZooming = e.getPointerCount()>1;

			Camera cam = mRenderer.getCamera();

			if(currentlyZooming){
				if(e.getPointerCount()<2){
					currentlyZooming = false;
				}else{
					float x2 = e.getX(1);
					float y2 = e.getY(1);
					float newDistance = (float) Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2));
					if(mPreviousDistance <=0){
						mPreviousDistance = newDistance;
					}

					int zoomMethod = 0;
					float zoomRatio = 1.0f;
					switch (zoomMethod) {
					case 0:
						// zoom additionnel sur la distance
						zoomRatio = newDistance-mPreviousDistance;
						mPreviousDistance = newDistance;

						//mRenderer.mDistance += zoomRatio/30.0f;
						//Log.i("TouchSurfaceView", "dist = "+mRenderer.mDistance+ " |event_dist : "+mPreviousDistance+ " ratio -= "+zoomRatio/36.0f);
						break;
					case 1:
						// zoom proportionnel sur la distance
						zoomRatio = newDistance/mPreviousDistance;
						mPreviousDistance = newDistance;
						//mRenderer.mDistance /= zoomRatio/2.0f;
						//Log.i("TouchSurfaceView", "dist = "+mRenderer.mDistance+ " |event_dist : "+mPreviousDistance+ " ratio *= "+zoomRatio/2.0f);
						break;
					default:
						break;
					}
					Log.d(TAG, "Zoom Ratio = " + zoomRatio);
					cam.setZ(cam.getZ() - zoomRatio/10);//TODO juste pour commencer, faut pas bouger que le Z au final
					//					if(mRenderer.mDistance > -10){
					//						mRenderer.mDistance = -10.0f;
					//					}


				}
			}else{
				Log.i("onTouchEvent"," not zooming");
				//dx and dy according to the screen referential, they need to be translated to dx, dy and dz in the referential of the world
				float dx = x - mPreviousX;
				float dy = y - mPreviousY;


//				cam.setRotX(cam.getRotX() + dx * TOUCH_SCALE_FACTOR);
//				cam.setRotY(cam.getRotY() + dy * TOUCH_SCALE_FACTOR);
				cam.setRotation(cam.getRotation().add(-dy * TOUCH_SCALE_FACTOR,-dx * TOUCH_SCALE_FACTOR,0));
				
				Log.d(TAG, "dx = "+dx+" , dy = "+dy);
			}
			//requestRender();
			break;
		case MotionEvent.ACTION_POINTER_2_DOWN:
			hadSomethingBeenDone = true;
			currentlyZooming = true;
			float x2 = e.getX(1);
			float y2 = e.getY(1);
			mPreviousDistance = (float) Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2));
			break;
		case MotionEvent.ACTION_POINTER_2_UP:
			currentlyZooming =  false;
			break;
		}

		mPreviousX = x;
		mPreviousY = y;
		//		if(mRenderer.mDistance > -10){
		//			mRenderer.mDistance = -10.0f;
		//		}

		return true;
	}

	private void displayDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.alert_dialog_menu, null))
	    // Add action buttons
	           .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   // sign in the user ...
	               }
	           })
	           .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   //LoginDialogFragment.this.getDialog().cancel();
	            	   //Dialog.this.
	            	   dialog.cancel();
	               }
	           });      
	    AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void displayPlanetDialog(Planet planet) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    // Get the layout inflater
	    LayoutInflater inflater = this.getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    View dialogView = inflater.inflate(R.layout.planet_display_popup, null);
	    builder.setView(dialogView)
	    // Add action buttons
	           .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   // sign in the user ...
	               }
	           })
	           .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   //LoginDialogFragment.this.getDialog().cancel();
	            	   //Dialog.this.
	            	   dialog.cancel();
	               }
	           });      
	    AlertDialog dialog = builder.create();
	    TextView name = (TextView) dialogView.findViewById(R.id.text_name);
	    name.setText(planet.getName());
	    
	    TextView mass = (TextView) dialogView.findViewById(R.id.text_mass);
	    mass.setText(Float.toString(planet.getMass()));
	    
	    TextView size = (TextView) dialogView.findViewById(R.id.text_size);
	    size.setText(Float.toString(planet.getSize()));
	    
	    TextView temperature = (TextView) dialogView.findViewById(R.id.text_temperature);
	    temperature.setText(planet.getTemperature() + "°K");
	    
	    TextView period = (TextView) dialogView.findViewById(R.id.text_period);
	    period.setText(Float.toString(planet.getPeriod()));
	    
	    TextView semiAxis = (TextView) dialogView.findViewById(R.id.text_semi_axis);
	    semiAxis.setText(Float.toString(planet.getSemiAxis()));
	    
	    
		dialog.show();
	}

}
