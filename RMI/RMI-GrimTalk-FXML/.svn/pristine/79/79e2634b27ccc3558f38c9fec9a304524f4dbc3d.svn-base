package hufs.ces.grimtalk.svg;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class SVGGrimEllipse extends SVGGrimShape {

	public SVGGrimEllipse(Ellipse ellip) {
		super(ellip);
	}
	@Override
	public String getSVGShapeString() {
		StringBuilder svgSB = new StringBuilder();
		
		svgSB.append("<ellipse ");
		Ellipse ellip = (Ellipse)getShape();
		String coords = String.format("cx=\"%.1f\" cy=\"%.1f\" rx=\"%.1f\" ry=\"%.1f\" ", 
				ellip.getCenterX(), ellip.getCenterY(), ellip.getRadiusX(), ellip.getRadiusY());
		svgSB.append(coords);
		
		svgSB.append(SVGUtils.getSVGStyleAttribute(this.getShape()));
		svgSB.append(" />");
		return svgSB.toString();
	}
	@Override
	public Path2D getPath2DShape() {
		Ellipse el = (Ellipse)getShape();
		double x = el.getCenterX() - el.getRadiusX();
		double y = el.getCenterY() - el.getRadiusY();
		double w = el.getRadiusX() * 2;
		double h = el.getRadiusY() * 2;
		Ellipse2D ellipse = new Ellipse2D.Double(x, y, w, h);

		Path2D pa = SVGUtils.getPath2DFromSwingShape(ellipse);

		return pa;
	}
	
	public static void main(String[] args) {
		Ellipse  sh = new Ellipse(50, 50, 50, 60);
		sh.setFill(Color.TRANSPARENT);
		sh.setStrokeWidth(5.2);
		sh.setStroke(new Color(0.5, 0.4, 0.1, 0));

		SVGGrimShape gsh = new SVGGrimEllipse(sh); 
		System.out.println(gsh.getSVGShapeString());
		System.out.println(
				SVGUtils.getSVGElementFromSVGPath(SVGUtils.convertShapeToSVGPath(gsh)));
	}
}
