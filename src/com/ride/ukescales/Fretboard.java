package com.ride.ukescales;

import java.util.List;

public class Fretboard {

	protected int number_of_strings = 4;
	protected int number_of_frets = 15;
	private int width = 6000;
	private int height = 1000;
	private List<Dot> scale = null;
	
	public int getNumber_of_strings() {
		return number_of_strings;
	}
	public void setNumber_of_strings(int number_of_strings) {
		this.number_of_strings = number_of_strings;
	}
	public int getNumber_of_frets() {
		return number_of_frets;
	}
	public void setNumber_of_frets(int number_of_frets) {
		this.number_of_frets = number_of_frets;
	}
	public List<Dot> getScale() {
		return scale;
	}
	public void setScale(List<Dot> scale) {
		this.scale = scale;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
