package fr.spaceapps.starlighttlse;


import rajawali.RajawaliActivity;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AndIHM1Activity extends AntiRajaActivity{
	
	private Renderer mRenderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mRenderer = new Renderer(this);
            mRenderer.setSurfaceView(mSurfaceView);
            super.setRenderer(mRenderer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            //getMenuInflater().inflate(R.menu.main, menu);
    	 	getMenuInflater().inflate(R.menu.main, menu);
            return true;
    }
	
	
	//private GLSurfaceView glView;
	//public WenderGLRenderer mRenderer;
	/*
	private float mPreviousX;
	private float mPreviousY;
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private final float TRACKBALL_SCALE_FACTOR = 36.0f;

	private boolean currentlyZooming = false;
	private float mPreviousDistance = 0.0f;
	
	private GLSurfaceView sfcvw2;*/
	
	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		/*glView = new TouchSurfaceView(this);  
//		ArrayList<View> touchables = new ArrayList<View>();
//		touchables.add(glView);*/
//		//setContentView(glView);
//		setContentView(R.layout.main);
//		/*TouchSurfaceView sfcvw = (TouchSurfaceView)findViewById(R.id.texteu);
//		Log.d("Activity", "sface1 : "+sfcvw);*/
//		sfcvw2 = (GLSurfaceView)findViewById(R.id.surfaceView2);
//		Log.d("Activity", "sface2 : "+sfcvw2);
//		TextView txt = (TextView)findViewById(R.id.texte);
//		Log.d("Activity", "txtvw : "+txt);
//		mRenderer = new WenderGLRenderer(this);
//		sfcvw2.setRenderer(mRenderer);
		/*((SurfaceView)findViewById(R.id.surfaceView1)).inflate(this, resource, this);
	
			LayoutInflater li = getLayoutInflater();
			li.i*/
		/*SurfaceView sv = (SurfaceView)findViewById(R.id.surfaceView1);
		
		glView.inflate(this, R.id.surfaceView1, null);*/
		/*
		super.onCreate(savedInstanceState);
        mSurfaceView = new GLSurfaceView(this);
        
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        if(checkOpenGLVersion) {
        	ConfigurationInfo info = am.getDeviceConfigurationInfo();
        	if(info.reqGlEsVersion < 0x20000)
        		throw new Error("OpenGL ES 2.0 is not supported by this device");
        }
        mSurfaceView.setEGLContextClientVersion(2);
        mLayout = new FrameLayout(this);
        mLayout.addView(mSurfaceView);
        
        if(mMultisamplingEnabled)
        	createMultisampleConfig();
        
        setContentView(mLayout);
		
	}*/
/*
	public boolean onCreateOptionsMenu(Menu menu) {
		((TextView)findViewById(R.id.texte)).setText("Menu");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) { 
		case R.id.ajouts:
			((TextView)findViewById(R.id.texte)).setText("Vous allez ajouter");
			return true;
		case R.id.retraits:
			((TextView)findViewById(R.id.texte)).setText("Vous allez retirer");
			return true;
		case R.id.addone:
			((TextView)findViewById(R.id.texte)).setText("1 ajouté !");
			//TouchSurfaceView sfcvw = (TouchSurfaceView)findViewById(R.id.texteu);
			//Log.d("Activity", "sface : "+sfcvw);
			mRenderer.addRetrieveTirages(1, 1);
			return true;
		case R.id.addten:
			((TextView)findViewById(R.id.texte)).setText("10 ajoutés !");
			mRenderer.addRetrieveTirages(1, 10);
			return true;
		case R.id.rmvone:
			((TextView)findViewById(R.id.texte)).setText("1 retiré !");
			mRenderer.addRetrieveTirages(-1, 1);
			return true;
		case R.id.rmvten:
			((TextView)findViewById(R.id.texte)).setText("10 retirés !");
			mRenderer.addRetrieveTirages(-1, 10);
			return true;
		case R.id.quitter:
			((TextView)findViewById(R.id.texte)).setText("Quitter");
			finish();
			return true;
		}
		return false;
	}
	
	
	
	@Override public boolean onTrackballEvent(MotionEvent e) {
		mRenderer.mAngleX += e.getX() * TRACKBALL_SCALE_FACTOR;
		mRenderer.mAngleY += e.getY() * TRACKBALL_SCALE_FACTOR;
		sfcvw2.requestRender();
		return true;
	}

	@Override public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			currentlyZooming = e.getPointerCount()>1;
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
						mRenderer.mDistance += zoomRatio/30.0f;
						Log.i("TouchSurfaceView", "dist = "+mRenderer.mDistance+ " |event_dist : "+mPreviousDistance+ " ratio -= "+zoomRatio/36.0f);
						break;
					case 1:
						// zoom proportionnel sur la distance
						zoomRatio = newDistance/mPreviousDistance;
						mPreviousDistance = newDistance;
						mRenderer.mDistance /= zoomRatio/2.0f;
						Log.i("TouchSurfaceView", "dist = "+mRenderer.mDistance+ " |event_dist : "+mPreviousDistance+ " ratio *= "+zoomRatio/2.0f);
						break;
					default:
						break;
					}
					
					if(mRenderer.mDistance > -10){
						mRenderer.mDistance = -10.0f;
					}
					
					
				}
			}else{
				float dx = x - mPreviousX;
				float dy = y - mPreviousY;
				mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
				mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
			}
			sfcvw2.requestRender();
			break;
		case MotionEvent.ACTION_POINTER_2_DOWN:
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
		if(mRenderer.mDistance > -10){
			mRenderer.mDistance = -10.0f;
		}
		
		return true;
	}*/
	
}
//class TouchSurfaceView extends GLSurfaceView {
//
//	MyGLRenderer mRenderer;
//	private float mPreviousX;
//	private float mPreviousY;
//	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
//	private final float TRACKBALL_SCALE_FACTOR = 36.0f;
//
//	public TouchSurfaceView(Context context) {
//		super(context);
//		mRenderer = new MyGLRenderer(context);
//		setRenderer(mRenderer);
//		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//	}
//
//	@Override public boolean onTrackballEvent(MotionEvent e) {
//		mRenderer.mAngleX += e.getX() * TRACKBALL_SCALE_FACTOR;
//		mRenderer.mAngleY += e.getY() * TRACKBALL_SCALE_FACTOR;
//		requestRender();
//		return true;
//	}
//
//	@Override public boolean onTouchEvent(MotionEvent e) {
//		float x = e.getX();
//		float y = e.getY();
//		switch (e.getAction()) {
//		case MotionEvent.ACTION_MOVE:
//			float dx = x - mPreviousX;
//			float dy = y - mPreviousY;
//			mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
//			mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
//			requestRender();
//		}
//		mPreviousX = x;
//		mPreviousY = y;
//		return true;
//	}
//}

