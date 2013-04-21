package fr.spaceapps.starlighttlse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import fr.spaceapps.starlighttlse.beans.Planet;
import fr.spaceapps.starlighttlse.beans.Star;

import rajawali.BaseObject3D;
import rajawali.Camera;
import rajawali.animation.BezierPath3D;
import rajawali.lights.DirectionalLight;
import rajawali.lights.PointLight;
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
import android.util.Log;

public class Renderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private BaseObject3D mSphere;
	private BaseObject3D mMoon;
	private PointLight mSunLight;
	
	private List<Star> universe;
	
	private List<BaseObject3D> mStars,mPlanets;

	public Renderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		loadUniverse();
		mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(2);
		
		mSunLight = new PointLight();
		mSunLight.setColor(1.0f, 1.0f, 1.0f);
		mSunLight.setPower(2);
		mSunLight.setPosition(1.1f, 0, 0);
		
		
		Number3D earthCenter = new Number3D(0, 0, 0);
		Number3D moonCenter = new Number3D(1.5f, 0, 0);
		
		mSphere = getSphere(earthCenter, 1, 20, R.drawable.sun);
		mMoon   = getSphere(moonCenter, 0.125f, 20, R.drawable.earthmap_nasa);
		
		addChild(mSphere);
		addChild(mMoon);
		Line3D line = getCircle(earthCenter, earthCenter, 1.5, 75, 1, 0xffffff00);
		addChild(line);
		

		initRenderuniverse();
		
		mCamera.setZ(4.2f);
		mCamera.setY(2.0f);
		//mCamera.setLookAt(0, 0, 0);
		mCamera.setFarPlane(Float.MAX_VALUE);
		Log.i("init cam","far plane :"+mCamera.getFarPlane());
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//((MainActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		//((MainActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mMoon.setRotY(mMoon.getRotY() + 1);
	}
	
	private Line3D getCircle(Number3D center, Number3D earthPosition, double ray, int nbPoints, float thickness, int color) {
		Stack<Number3D> points = new Stack<Number3D>();
		
		double step = 2*Math.PI/nbPoints;
		Number3D firstPoint = new Number3D(center.x + ray, center.y, center.z);
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
		sphere.addLight(mSunLight);
		sphere.addTexture(mTextureManager.addTexture(bg));
		
		sphere.setPosition(center);
		
		return sphere;
	}
	
	private void loadUniverse(){
		universe = new ArrayList<Star>();
		
		Planet planet1,planet2,planet3; 
		
		Star star1,star2;
		
		planet1 = new Planet();
		planet1.setX(-133.110709072558f);
		planet1.setY(168.755371357685f);
		planet1.setZ(-29.104238134762f);
		
		planet2 = new Planet();
		planet2.setX(-135.110709072558f);
		planet2.setY(168.755371357685f);
		planet2.setZ(-29.104238134762f);
		
		star1 = new Star();
		star1.setX(-138.110709072558f);
		star1.setY(168.755371357685f);
		star1.setZ(-29.104238134762f);
		star1.setPlan(new ArrayList<Planet>());
		star1.getPlan().add(planet1);
		star1.getPlan().add(planet2);
		
		universe.add(star1);
		mStars = new ArrayList<BaseObject3D>();
		mPlanets = new ArrayList<BaseObject3D>();
}
	
	
	private void initRenderuniverse(){
		for(Star aStar : universe){

			Number3D starCenter = new Number3D(aStar.getX(), aStar.getY(), aStar.getZ());
			BaseObject3D starObj = getSphere(starCenter, 1, 20, R.drawable.sun);
			addChild(starObj);
			
			
			for(Planet aPlanet : aStar.getPlan()){
				Number3D planetCenter = new Number3D(aPlanet.getX(), aPlanet.getY(), aPlanet.getZ());
				BaseObject3D planetObj  = getSphere(planetCenter, 0.125f, 20, R.drawable.earthmap_nasa);
				
				addChild(planetObj);
				
				Line3D line = getCircle(starCenter, starCenter, planetCenter.x-starCenter.x, 75, 1, 0xffffff00);
				addChild(line);
			}
		}
	}
	
	public Camera getCamera(){
		return mCamera;
	}
	
	public void animateTo(Number3D position, Number3D lookAt) {
		mCamera.setPosition(position);
		mCamera.setLookAt(lookAt);
	}
	
}