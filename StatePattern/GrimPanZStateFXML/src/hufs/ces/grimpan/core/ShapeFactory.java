package hufs.ces.grimpan.core;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Shape;
import javafx.scene.shape.VLineTo;

public class ShapeFactory {

	private volatile static ShapeFactory uniqueSFInstance;
	
	GrimPanModel model = null;

	private ShapeFactory(GrimPanModel model) {
		this.model = model;
	}
	public static ShapeFactory getInstance(GrimPanModel model) {
		if (uniqueSFInstance == null) {
			synchronized (GrimPanModel.class) {
				if (uniqueSFInstance == null) {
					uniqueSFInstance = new ShapeFactory(model);
				}
			}
		}
		return uniqueSFInstance;
	}
	public Shape createPaintedShape(Shape shape) {

		if (model.isShapeFill()){
			shape.setFill(model.getShapeFillColor());
		}
		else {
			shape.setFill(Color.TRANSPARENT);
		}

		if (model.isShapeStroke()){
			shape.setStrokeWidth(model.getShapeStrokeWidth());
			shape.setStroke(model.getShapeStrokeColor());
		}
		else {
			shape.setStroke(Color.TRANSPARENT);
		}
		return shape;
	}
	public Ellipse createPaintedEllipse() {
		Ellipse shape = new Ellipse();

		if (model.isShapeFill()){
			shape.setFill(model.getShapeFillColor());
		}
		else {
			shape.setFill(Color.TRANSPARENT);
		}
		if (model.isShapeStroke()){
			shape.setStrokeWidth(model.getShapeStrokeWidth());
			shape.setStroke(model.getShapeStrokeColor());
		}
		else {
			shape.setStroke(Color.TRANSPARENT);
		}
		return shape;
	}
	public Line createPaintedLine() {
		Line shape = new Line();

		shape.setStrokeWidth(model.getShapeStrokeWidth());
		shape.setStroke(model.getShapeStrokeColor());
		return shape;
	}
	public Path createPaintedPath() {
		Path shape = new Path();

		if (model.isShapeFill()){
			shape.setFill(model.getShapeFillColor());
		}
		else {
			shape.setFill(Color.TRANSPARENT);
		}

		if (model.isShapeStroke()){
			shape.setStrokeWidth(model.getShapeStrokeWidth());
			shape.setStroke(model.getShapeStrokeColor());
		}
		else {
			shape.setStroke(Color.TRANSPARENT);
		}
		return shape;
	}
	public Shape createMousePointedLine() {
		Point2D pstart = model.getStartMousePosition();
		Point2D pend = model.getCurrMousePosition();
		return createPaintedShape(new Line(pstart.getX(), pstart.getY(), pend.getX(), pend.getY()));

	}

	static public void copyShapeProperties(Shape fromShape, Shape toShape) {
		toShape.setFill(fromShape.getFill());
		toShape.setSmooth(fromShape.isSmooth());
		toShape.setStroke(fromShape.getStroke());
		toShape.setStrokeDashOffset(fromShape.getStrokeDashOffset());
		toShape.setStrokeLineCap(fromShape.getStrokeLineCap());
		toShape.setStrokeLineJoin(fromShape.getStrokeLineJoin());
		toShape.setStrokeMiterLimit(fromShape.getStrokeMiterLimit());
		toShape.setStrokeType(fromShape.getStrokeType());
		toShape.setStrokeWidth(fromShape.getStrokeWidth());
	}
	static public void translateShape(Shape shape, double dx, double dy) {

		if (shape instanceof Line) {
			Line line = (Line) shape;
			line.setStartX(line.getStartX()+dx);
			line.setStartY(line.getStartY()+dy);
			line.setEndX(line.getEndX()+dx);
			line.setEndY(line.getEndY()+dy);
		}
		else if (shape instanceof Ellipse) {
			Ellipse ellipse = (Ellipse) shape;
			ellipse.setCenterX(ellipse.getCenterX()+dx);
			ellipse.setCenterY(ellipse.getCenterY()+dy);
		}
		else if (shape instanceof Path) {
			Path path =(Path) shape;
			for (PathElement el:path.getElements()) {
				if (el instanceof MoveTo) {
					MoveTo pel = (MoveTo)el;
					pel.setX(pel.getX() + dx);
					pel.setY(pel.getY() + dy);
				}
				else if (el instanceof LineTo) {
					LineTo pel = (LineTo)el;
					pel.setX(pel.getX() + dx);
					pel.setY(pel.getY() + dy);
				}
				else if (el instanceof ArcTo) {
					ArcTo pel = (ArcTo)el;
					pel.setX(pel.getX() + dx);
					pel.setY(pel.getY() + dy);
				}
				else if (el instanceof HLineTo) {
					HLineTo pel = (HLineTo)el;
					pel.setX(pel.getX() + dx);
				}
				else if (el instanceof VLineTo) {
					VLineTo pel = (VLineTo)el;
					pel.setY(pel.getY() + dy);
				}
				else if (el instanceof CubicCurveTo) {
					CubicCurveTo pel = (CubicCurveTo)el;
					pel.setX(pel.getX() + dx);
					pel.setY(pel.getY() + dy);
					pel.setControlX1(pel.getControlX1() + dx);
					pel.setControlY1(pel.getControlY1() + dy);
					pel.setControlX2(pel.getControlX2() + dx);
					pel.setControlY2(pel.getControlY2() + dy);
				}
				else if (el instanceof QuadCurveTo) {
					QuadCurveTo pel = (QuadCurveTo)el;
					pel.setX(pel.getX() + dx);
					pel.setY(pel.getY() + dy);
					pel.setControlX(pel.getControlX() + dx);
					pel.setControlY(pel.getControlY() + dy);
				}
			}
		}
		else if (shape instanceof Polygon) {
			Polygon pol =(Polygon) shape;
			ObservableList<Double> points = pol.getPoints();
			for (int i=0; i<points.size(); i+=2) {
				points.set(i, points.get(i)+dx);
				points.set(i+1, points.get(i+1)+dy);
			}
		}
		else if (shape instanceof Polyline) {
			Polyline pol =(Polyline) shape;
			ObservableList<Double> points = pol.getPoints();
			for (int i=0; i<points.size(); i+=2) {
				points.set(i, points.get(i)+dx);
				points.set(i+1, points.get(i+1)+dy);
			}
		}
	}
}
