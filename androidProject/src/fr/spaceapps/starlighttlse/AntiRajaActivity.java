package fr.spaceapps.starlighttlse;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.FrameLayout;
import rajawali.RajawaliActivity;

public class AntiRajaActivity extends RajawaliActivity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mSurfaceView = new TouchSurfaceView(this);
	        
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
	    }
}
