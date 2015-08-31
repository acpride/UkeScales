package com.ride.ukescales.render;

import java.util.List;

public class Fretboard {

	protected int number_of_strings = 4;
	protected int number_of_frets = 15;
	private int width = 6093;
	private int widthHeadstock = 707;
	private int height = 1000;
	private int heightHeadstock = 1539;
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
	public int getWidthHeadstock() {
		return widthHeadstock;
	}
	public void setWidthHeadstock(int widthHeadstock) {
		this.widthHeadstock = widthHeadstock;
	}
	public int getHeightHeadstock() {
		return heightHeadstock;
	}
	public void setHeightHeadstock(int heightHeadstock) {
		this.heightHeadstock = heightHeadstock;
	}
	
}
