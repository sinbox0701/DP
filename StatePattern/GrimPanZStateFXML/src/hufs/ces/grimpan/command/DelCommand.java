/**
 * Created on 2015. 4. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.command;

import hufs.ces.grimpan.core.GrimPanModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * @author cskim
 *
 */
public class DelCommand implements Command {
	GrimPanModel model = null;
	Shape saveDeletedShape = null;
	public DelCommand(GrimPanModel model){
		this.model = model; 
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		saveDeletedShape = model.shapeList.get(model.getSelectedShapeIndex());
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
			model.shapeList.add(saveDeletedShape);
	}
	
}
