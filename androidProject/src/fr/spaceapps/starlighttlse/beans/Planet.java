package fr.spaceapps.starlighttlse.beans;


public class Planet {
	
	private String name;
	private float mass;
	private float size;
	private float density;
	private int temperature;
	private float period;
	private float semiAxis;
	private float inclinaison;
	
	public Planet(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public float getPeriod() {
		return period;
	}

	public void setPeriod(float period) {
		this.period = period;
	}

	public float getSemiAxis() {
		return semiAxis;
	}

	public void setSemiAxis(float semiAxis) {
		this.semiAxis = semiAxis;
	}

	public float getInclinaison() {
		return inclinaison;
	}

	public void setInclinaison(float inclinaison) {
		this.inclinaison = inclinaison;
	}
	
}