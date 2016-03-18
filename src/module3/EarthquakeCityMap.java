package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;
import processing.core.PShape;
//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author GK
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	PShape rectangleKey;
	PShape circleMagFive,circleMagFour,circleMagBelowFour;
	
	public void setup() {
		size(1000, 800, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	PointFeature f = earthquakes.get(0);
	    	System.out.println(f.getProperties());
	    	Object magObj = f.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	// PointFeatures also have a getLocation method
	    }
	    
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
	    System.out.println("Color yellow is " + yellow);
	    int red = color(255, 0, 0);
	    int blue = color(0,0,255);
	    
	    //TODO: Add code here as appropriate
	    
	    for (PointFeature earth_pf:earthquakes) {
	    	
	    	SimplePointMarker pf = createMarker(earth_pf);
	    		System.out.println(earth_pf.getProperties());
	    	Object magnObj = earth_pf.getProperty("magnitude");
	    		System.out.println("Magnitude only: " + magnObj);
	    	float magFl = Float.parseFloat(magnObj.toString());
	    		System.out.println("Magnitude as float: " + magFl);
	    	pf.getLocation();
	    		System.out.println("Location: " + pf.getLocation());
	    	
	    	if (magFl >= 5.0) {
	    		pf.setColor(red);
	    		pf.setRadius(20);
	    		}
	    	else if (magFl >= 4.0) {
	    		pf.setColor(yellow);
	    		pf.setRadius(10);
	    		
			}
	    	else  {
	    		pf.setColor(blue);
	    		pf.setRadius(5);
	    	}
	    	
	    	map.addMarker(pf);
	    }
	    
	    rectangleKey = createShape(RECT,0,0,150,350);
	    circleMagFive = createShape(ELLIPSE, 20,20,20,20);
	    circleMagFive.setFill(red);
	    circleMagFour = createShape(ELLIPSE, 10,10,10,10);
	    circleMagFour.setFill(yellow);
	    circleMagBelowFour = createShape(ELLIPSE, 5,5,5,5);
	    circleMagBelowFour.setFill(blue);
	    

	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		
		shape(rectangleKey, 25, 50);
		fill(0, 0, 0);
		
		String keyTitle = "Earthquake Key";
		String magFive = "5.0+ Magnitude";
		String magFour = "4.0+ Magnitude";
		String magBelowFour = "Below 4.0";
		
		//textAlign(LEFT, TOP);
		text(keyTitle, 50, 70);
		text(magFive,60, 100);
		text(magFour,60, 150);
		text(magBelowFour, 60, 200);
		shape(circleMagFive,15,65);
		shape(circleMagFour,55,150);
		shape(circleMagBelowFour,59,200);
	}
}
