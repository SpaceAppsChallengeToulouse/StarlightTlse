package fr.spaceapps.starlighttlse;

import rajawali.math.Quaternion;
import android.util.Log;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TouchSurfaceView extends GLSurfaceView {

	public fr.spaceapps.starlighttlse.Renderer mRenderer;
	private float mPreviousX;
	private float mPreviousY;
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private final float TRACKBALL_SCALE_FACTOR = 36.0f;

	private boolean currentlyZooming = false;
	private float mPreviousDistance = 0.0f;

	public TouchSurfaceView(Context context) {
		super(context);
		
		//mRenderer = new MyGLRenderer(context);
		
		mRenderer = new fr.spaceapps.starlighttlse.Renderer(context);
		//setRenderer(mRenderer);
		//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	public TouchSurfaceView(Context context, AttributeSet attr){
		this(context);
	}

	/*@Override 
	public boolean onTrackballEvent(MotionEvent e) {
		Log.i("testdev JEFF","onTrackballEvent");
		Quaternion orientation = mRenderer.getCamera().getOrientation();
		Quaternion deltaOrientation = new Quaternion(
				1.0f, 
				e.getX() * TRACKBALL_SCALE_FACTOR, 
				e.getY() * TRACKBALL_SCALE_FACTOR, 
				0.0f);
		orientation.add(deltaOrientation);
		mRenderer.getCamera().setOrientation(orientation);
		
		//mRenderer.mAngleX += e.getX() * TRACKBALL_SCALE_FACTOR;
		//mRenderer.mAngleY += e.getY() * TRACKBALL_SCALE_FACTOR;
		
		requestRender();
		return true;
	}*/

	@Override
	public boolean onTouchEvent(MotionEvent e) {
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
					
//					if(mRenderer.mDistance > -10){
//						mRenderer.mDistance = -10.0f;
//					}
					
					
				}
			}else{
				Log.i("onTouchEvent"," not zooming");
				float dx = x - mPreviousX;
				float dy = y - mPreviousY;
				
				Quaternion orientation = mRenderer.getCamera().getOrientation();
				Quaternion deltaOrientation = new Quaternion(
						1.0f, 
						e.getX() * TRACKBALL_SCALE_FACTOR, 
						e.getY() * TRACKBALL_SCALE_FACTOR, 
						0.0f);
				orientation.add(deltaOrientation);
				mRenderer.getCamera().setOrientation(orientation);
				
//				mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
//				mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
			}
			requestRender();
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
//		if(mRenderer.mDistance > -10){
//			mRenderer.mDistance = -10.0f;
//		}
		
		return true;
	}
}
