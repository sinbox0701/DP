package hufs.ces.grimtalk.svg;

import java.awt.geom.Path2D;

import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.VLineTo;

public class SVGGrimPath extends SVGGrimShape {

	public SVGGrimPath(Path path) {
		super(path);
	}
	@Override
	public String getSVGShapeString() {
		StringBuilder svgSB = new StringBuilder();
		
		svgSB.append("<path ");
		Path path = (Path)getShape();
		
		svgSB.append("d=\"");
		ObservableList<PathElement> peList = path.getElements();
		for (PathElement pe : peList) {
			String coords = null;
			if (pe instanceof MoveTo) {
				coords = String.format("M%.1f %.1f ", ((MoveTo) pe).getX(), ((MoveTo) pe).getY());
			}
			else if (pe instanceof LineTo) {
				coords = String.format("L%.1f %.1f ", ((LineTo) pe).getX(), ((LineTo) pe).getY());
			}
			else if (pe instanceof HLineTo) {
				coords = String.format("H%.1f ", ((HLineTo) pe).getX());
			}
			else if (pe instanceof VLineTo) {
				coords = String.format("V%.1f ", ((VLineTo) pe).getY());
			}
			else if (pe instanceof ArcTo) {
				ArcTo a = (ArcTo)pe;
				String largeFlag = a.isLargeArcFlag() ? "1" : "0";
				String sweepFlag = a.isSweepFlag() ? "1" : "0";
				coords = String.format("A%.1f %.1f %.1f %s %s %.1f %.1f ",
						a.getRadiusX(), a.getRadiusY(), a.getXAxisRotation(), largeFlag, sweepFlag, a.getX(), a.getY()); 
			}
			else if (pe instanceof CubicCurveTo) {
				CubicCurveTo c = (CubicCurveTo)pe;
				coords = String.format("C%.1f %.1f %.1f %.1f %.1f %.1f ",
						c.getControlX1(), c.getControlY1(), c.getControlX2(), c.getControlY2(), c.getX(), c.getY()); 
			}
			else if (pe instanceof QuadCurveTo) {
				QuadCurveTo q = (QuadCurveTo)pe;
				coords = String.format("Q%.1f %.1f %.1f %.1f ",
						q.getControlX(), q.getControlY(), q.getX(), q.getY()); 
			}
			else if (pe instanceof ClosePath) {
				coords = "Z";
			}

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
		Path path = (Path)getShape();

		ObservableList<PathElement> peList = path.getElements();
		for (PathElement pe : peList) {
			if (pe instanceof MoveTo) {
				pa.moveTo(((MoveTo) pe).getX(), ((MoveTo) pe).getY());
			}
			else if (pe instanceof LineTo) {
				pa.lineTo(((LineTo) pe).getX(), ((LineTo) pe).getY());
			}
			else if (pe instanceof HLineTo) {
				pa.lineTo(((HLineTo) pe).getX(), 0);
			}
			else if (pe instanceof VLineTo) {
				pa.lineTo(0, ((VLineTo) pe).getY());
			}
			else if (pe instanceof ArcTo) {
				ArcTo a = (ArcTo)pe;
				//String largeFlag = a.isLargeArcFlag() ? "1" : "0";
				//String sweepFlag = a.isSweepFlag() ? "1" : "0";
				//coords = String.format("A%.1f %.1f %.1f %s %s %.1f %.1f ",
				//		a.getRadiusX(), a.getRadiusY(), a.getXAxisRotation(), largeFlag, sweepFlag, a.getX(), a.getY());
				pa.lineTo(a.getX(), a.getY());
			}
			else if (pe instanceof CubicCurveTo) {
				CubicCurveTo c = (CubicCurveTo)pe;
				pa.curveTo(c.getControlX1(), c.getControlY1(), c.getControlX2(), c.getControlY2(), c.getX(), c.getY()); 
			}
			else if (pe instanceof QuadCurveTo) {
				QuadCurveTo q = (QuadCurveTo)pe;
				pa.quadTo(q.getControlX(), q.getControlY(), q.getX(), q.getY()); 
			}
			else if (pe instanceof ClosePath) {
				pa.closePath();
			}
		}
		return pa;
	}
	
	public static void main(String[] args) {
		Path  sh = new Path();
		// "M10 10 H 90 V 90 H 10 Z" 
		sh.getElements().add(new MoveTo(10,10));
		sh.getElements().add(new HLineTo(90));
		sh.getElements().add(new VLineTo(90));
		sh.getElements().add(new HLineTo(10));
		sh.getElements().add(new ClosePath());
		
		sh.setFill(Color.TRANSPARENT);
		sh.setStrokeWidth(5.2);
		sh.setStroke(new Color(0.1, 0.1, 0.1, 0));

		SVGGrimShape gsh = new SVGGrimPath(sh); 
		System.out.println(gsh.getSVGShapeString());
		System.out.println(SVGUtils.convertShapeToSVGPath(gsh));
	}
}
