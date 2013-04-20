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

		Bitmap bg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.earthmap_nasa);
		DiffuseMaterial material = new DiffuseMaterial();
		mSphere = new Sphere(1, 20, 20);
		mSphere.setMaterial(material);
		mSphere.addLight(mLight);
		mSphere.addTexture(mTextureManager.addTexture(bg));
		addChild(mSphere);
		BezierPath3D bezierPath = new BezierPath3D();
		bezierPath.addPoint(new Number3D(0, 0.5f, 0), new Number3D(1, 0, 0), new Number3D(0, -0.5f, 0), new Number3D(-1, 0, 0));
		bezierPath.addPoint(new Number3D(-2, 4, 4.5f), new Number3D(2, -2, -2), new Number3D(4, 4, 4), new Number3D(-2, 4, 4.5f));

		// -- again, create a Stack of Number3Ds
		Stack points = new Stack();

		// -- the more segments, the smoother the line
		int numLineSegments = 100;

		for(int i=0; i < numLineSegments; i++) {
			// -- BezierPath3D's calculatePoint() method calculates and interpolated
			//     Number3D. It accepts values between 0 and 1, hence i / numLineSegments
			points.push(bezierPath.calculatePoint(i / (float)numLineSegments));
		}

		// -- create out bezier curve
		Line3D line = new Line3D(points, 1, 0x00ff00);
		SimpleMaterial materialLine = new SimpleMaterial();
		materialLine.setUseColor(true);
		line.setMaterial(materialLine);
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
	
	public Camera getCamera(){
		return mCamera;
	}
	
}