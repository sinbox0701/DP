package hufs.ces.grimtalk.core;

import hufs.ces.grimtalk.svg.SVGGrimShape;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class DrawPane extends Pane {

	private GrimTalkModel model;

	public DrawPane() {
		this.model = GrimTalkModel.getInstance();
	}
	public DrawPane(double width, double height) {
		this.model = GrimTalkModel.getInstance();
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public void clear() {
		this.getChildren().clear();
	}
	public void redraw() {
		clear();
		//System.out.println("Shape Count="+model.shapeList.size());
		for (SVGGrimShape gsh:model.shapeList){
			this.getChildren().add(gsh.getShape());
		}
		if (model.curDrawShape!=null && model.curDrawShape.getShape()!=null) {
			this.getChildren().add(model.curDrawShape.getShape());
		}
	}
}
