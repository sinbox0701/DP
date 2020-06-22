package hufs.ces.cirbuf.gui;

import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Utils {

	public static HashMap<Color,String> colorMap = new HashMap<Color,String>() {{
		put(Color.RED, "RED");
		put(Color.PINK, "PINK");
		put(Color.CORAL, "CORAL");
		put(Color.GOLD, "GOLD");
		put(Color.YELLOW, "YELLOW");
		put(Color.KHAKI, "KHAKI");
		put(Color.MAGENTA, "MAGENTA");
		put(Color.PURPLE, "PURPLE");
		put(Color.LIME, "LIME");
		put(Color.GREEN, "GREEN");
		put(Color.TEAL, "TEAL");
		put(Color.CYAN, "CYAN");
		put(Color.BLUE, "BLUE");
		put(Color.NAVY, "NAVY");
		put(Color.TAN, "TAN");
		put(Color.BROWN, "BROWN");
		put(Color.MAROON, "MAROON");
		put(Color.BEIGE, "BEIGE");
		put(Color.IVORY, "IVORY");
		put(Color.AZURE, "AZURE");
		put(Color.SNOW, "SNOW");
		put(Color.WHITE, "WHITE");
		put(Color.LIGHTGRAY, "LIGHTGRAY");
		put(Color.SILVER, "SILVER");
		put(Color.GRAY, "GRAY");
		put(Color.BLACK, "BLACK");
	}};
	
	public static String color2RGBString(Color color) {
		return String.format("rgb(%d,%d,%d)", 
				(int)(color.getRed()*255), (int)(color.getGreen()*255), (int)(color.getBlue()*255));

	}
	
	public static void setBackground(Node node, Color bg) {
		if (colorMap.keySet().contains(bg)) {
			node.setStyle(String.format("-fx-background-color: %s", colorMap.get(bg)));
		}
		else {
			node.setStyle(String.format("-fx-background-color: %s", color2RGBString(bg)));
		}
	}
	
	public static Color getRatioColor(double ratio) {
		int ired = ratio >= 0.5 ? 255 : (int)(255*ratio);
		int igreen = ratio <= 0.5 ? 255 : (int)(255*(1-ratio));
		return Color.rgb(ired, igreen, 0);
	}

}
