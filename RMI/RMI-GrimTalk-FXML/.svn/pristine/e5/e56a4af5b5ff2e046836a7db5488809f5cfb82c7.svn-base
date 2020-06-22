package hufs.ces.grimtalk.svg;

import java.awt.geom.Path2D;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class SVGGrimPolyline extends SVGGrimShape {

	public SVGGrimPolyline(Polyline poly) {
		super(poly);
	}
	@Override
	public String getSVGShapeString() {
		StringBuilder svgSB = new StringBuilder();
		
		svgSB.append("<polyline ");
		Polyline poly = (Polyline)getShape();
		
		svgSB.append("points=\"");
		ObservableList<Double> points = poly.getPoints();
		for (int i=0; i<points.size(); i+=2) {			
			String coords = String.format("%.1f,%.1f ", points.get(i), points.get(i+1));
			svgSB.append(coords);
		}
		svgSB.append('"');
		svgSB.append(' ');
		
		
		svgSB.append(SVGUtils.getSVGStyleAttribute(this.getShape()));
		svgSB.append(" />");
		return svgSB.toString();
	}
	@Override
	public Path2D getPath2DShape() {
		Path2D pa = new Path2D.Double();
		Polyline poly = (Polyline)getShape();
		ObservableList<Double> points = poly.getPoints();
		
		for (int i=0; i<points.size(); i+=2) {
			if (i==0) {
				pa.moveTo(points.get(i), points.get(i+1));
				continue;
			}
			pa.lineTo(points.get(i), points.get(i+1));
		}
		
		return pa;
	}
	
	public static void main(String[] args) {
		Polyline  sh = new Polyline(0,0, 10,0, 40,10, 50,30, 40,60, 10,20);
		sh.setFill(Color.TRANSPARENT);
		sh.setStrokeWidth(5.2);
		sh.setStroke(new Color(0.1, 0.1, 0.1, 0));

		System.out.println(new SVGGrimPolyline(sh).getSVGShapeString());
	}

}

