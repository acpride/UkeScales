package com.ride.ukescales;

public class Note {

	private String pitch = null;
	private boolean isRoot = false;
	//degree in scale
	private String degree = null;
	
	public Note(String pitch, String degree, boolean isRoot){
		this.pitch = pitch;
		this.degree = degree;
		this.isRoot = isRoot;
	}
	public String getPitch() {
		return pitch;
	}
	public void setPitch(String pitch) {
		this.pitch = pitch;
	}
	public boolean isRoot() {
		return isRoot;
	}
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String toString(){
	    return this.pitch + ((this.isRoot)?"!":"");
	}
	
}
