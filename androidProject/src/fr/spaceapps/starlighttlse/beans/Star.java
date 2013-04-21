package fr.spaceapps.starlighttlse.beans;

import java.util.ArrayList;

public class Star {
	private String name;
	private int temperature;
	private float mass;
	private int distance; 
	private float size;
	private float [] rightAscention;
	private float [] declinaison;
	private ArrayList<Planet> plan;
	 
	public Star (){
		 
	 }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float[] getRightAscention() {
		return rightAscention;
	}

	public void setRightAscention(float[] rightAscention) {
		this.rightAscention = rightAscention;
	}

	public float[] getDeclinaison() {
		return declinaison;
	}

	public void setDeclinaison(float[] declinaison) {
		this.declinaison = declinaison;
	}

	public ArrayList<Planet> getPlan() {
		return plan;
	}

	public void setPlan(ArrayList<Planet> plan) {
		this.plan = plan;
	}
	
}
