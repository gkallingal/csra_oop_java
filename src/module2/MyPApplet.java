package module2;

import processing.core.*;

public class MyPApplet extends PApplet {
	
	private String image = "palmTrees.jpg";
	
	private PImage backgroundImg;
	
	public void setup(){
		
		size(200, 200);
		backgroundImg = loadImage(image, "jpg");
	}

	public void draw(){
		backgroundImg.resize(width, height);
		image(backgroundImg, 0, 0);
		fill(255, 210, 0);
		ellipse(width/4, height/5, width/4, height/4);
		
	}
}
