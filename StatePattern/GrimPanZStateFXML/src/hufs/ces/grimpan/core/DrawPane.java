package hufs.ces.grimpan.core;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class DrawPane extends Pane {

	private GrimPanModel model;

	public DrawPane() {
		this.model = GrimPanModel.getInstance();
	}
	public DrawPane(double width, double height) {
		this.model = GrimPanModel.getInstance();
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public void clear() {
		this.getChildren().clear();
	}
	public void redraw() {
		clear();
		//System.out.println("Shape Count="+model.shapeList.size());
		for (Shape sh:model.shapeList){
			this.getChildren().add(sh);
		}
		if (model.curDrawShape!=null) {
			this.getChildren().add(model.curDrawShape);
		}
	}
}
