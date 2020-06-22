/**
 * Created on 2015. 4. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.command;

import hufs.ces.grimpan.core.GrimPanModel;
import hufs.ces.grimpan.core.ShapeFactory;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

/**
 * @author cskim
 *
 */
public class MoveCommand implements Command {

	GrimPanModel model = null;
	Point2D movedPos = null;
	Shape movedShape = null;
	public MoveCommand(GrimPanModel model, Point2D moved){
		this.model = model;
		this.movedPos = moved;
	}

	@Override
	public void execute() {
		movedShape = model.shapeList.get(model.getSelectedShapeIndex());
	}

	@Override
	public void undo() {
		int selIndex = model.shapeList.indexOf(movedShape);
		if (selIndex != -1){
			ShapeFactory.translateShape(movedShape, -movedPos.getX(), -movedPos.getY());
		}
		else {
			System.out.println("undo moved GrimShape not found!!");
		}
	}

}
