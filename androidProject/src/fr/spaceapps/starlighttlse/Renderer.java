package fr.spaceapps.starlighttlse;

import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.Camera;
import rajawali.animation.BezierPath3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.SimpleMaterial;
import rajawali.math.Number3D;
import rajawali.math.Quaternion;
import rajawali.primitives.Line3D;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Renderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mSphere;

	public Renderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(2);
		
		Number3D earthCenter = new Number3D(0, 0, 0);
		
		mSphere = getSphere(earthCenter, 1, 20, R.drawable.earthmap_nasa);

		addChild(mSphere);
		
		Line3D line = getCircle(earthCenter, earthCenter, 1.5, 75, 1, 0xffffff00);
		addChild(line);
		
		mCamera.setZ(4.2f);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//((MainActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		//((MainActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mSphere.setRotY(mSphere.getRotY() + 1);
	}
	
	private Line3D getCircle(Number3D center, Number3D earthPosition, double ray, int nbPoints, float thickness, int color) {
		Stack<Number3D> points = new Stack<Number3D>();
		
		double step = 2*Math.PI/nbPoints;
		Number3D firstPoint = new Number3D(center.x + ray, center.y, center.y);
		for(double currentAngle = 0; currentAngle < Math.PI*2; currentAngle += step) {
			double x = Math.cos(currentAngle) * ray + center.x;
			double y = center.y;//always 0
			double z = Math.sin(currentAngle) * ray + center.z;
			points.add(new Number3D(x, y, z));
		}
		points.add(firstPoint);
		
		Line3D line = new Line3D(points, 1, 0xffffff00);
		SimpleMaterial materialLine = new SimpleMaterial();
		materialLine.setUseColor(true);
		line.setMaterial(materialLine);
		
		//TODO rotate the axis of the ellipsis of the planet to match the real axis
		/*if(earthPosition.x != center.x) {
			float rotY = (float) Math.atan2(center.z-earthPosition.z, center.x-earthPosition.x);
			float rotZ = (float) Math.atan2(center.y-earthPosition.y, center.x-earthPosition.x);
			//rotX = O

			Log.d(TAG, "rotY = "+rotY+", rotZ = "+rotZ);
			
			//line.setRotX(0);

			line.setRotZ((float) Math.toDegrees(rotZ));
			line.setRotY((float) Math.toDegrees(rotY));
		}*/
		
		return line;
	}
	
	private Sphere getSphere(Number3D center, float ray, int nbPoint, int texture) {
		Bitmap bg = BitmapFactory.decodeResource(mContext.getResources(), texture);
		DiffuseMaterial material = new DiffuseMaterial();
		
		Sphere sphere = new Sphere(ray, nbPoint, nbPoint);
		sphere.setMaterial(material);
		sphere.addLight(mLight);
		sphere.addTexture(mTextureManager.addTexture(bg));
		
		sphere.setPosition(center);
		
		return sphere;
	}
	
	
	public Camera getCamera(){
		return mCamera;
	}
	
}