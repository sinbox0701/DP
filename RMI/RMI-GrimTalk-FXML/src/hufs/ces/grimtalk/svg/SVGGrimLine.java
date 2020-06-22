package hufs.ces.grimtalk.svg;

import java.awt.geom.Path2D;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SVGGrimLine extends SVGGrimShape {

	public SVGGrimLine(Line line) {
		super(line);
	}
	@Override
	public String getSVGShapeString() {
		StringBuilder svgSB = new StringBuilder();
		
		svgSB.append("<line ");
		Line lin = (Line)getShape();
		String coords = String.format("x1=\"%.1f\" y1=\"%.1f\" x2=\"%.1f\" y2=\"%.1f\" ", 
				lin.getStartX(), lin.getStartY(), lin.getEndX(), lin.getEndY());
		svgSB.append(coords);
		
		svgSB.append(SVGUtils.getSVGStyleAttribute(this.getShape()));
		svgSB.append(" />");
		return svgSB.toString();
	}
	@Override
	public Path2D getPath2DShape() {
		Line lin = (Line)getShape();
		Path2D pa = new Path2D.Double();
		pa.moveTo(lin.getStartX(), lin.getStartY());
		pa.lineTo(lin.getEndX(), lin.getEndY());
		return pa;
	}
	
	public static void main(String[] args) {
		Line  li = new Line(0, 0, 50, 60);
		li.setFill(new Color(0.5, 0.4, 0.1, 0));
		li.setStrokeWidth(5.2);
		li.setStroke(new Color(0.5, 0.4, 0.1, 0));

		System.out.println(new SVGGrimLine(li).getSVGShapeString());
	}

}
