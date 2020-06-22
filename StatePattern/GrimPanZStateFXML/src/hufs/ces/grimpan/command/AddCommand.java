/**
 * Created on 2015. 4. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimpan.command;

import hufs.ces.grimpan.core.GrimPanModel;
import javafx.scene.shape.Shape;

/**
 * @author cskim
 *
 */
public class AddCommand implements Command {

	GrimPanModel model = null;
	Shape grimShape = null;
	public AddCommand(GrimPanModel model, Shape grimShape){
		this.model = model;
		this.grimShape = grimShape;
	}

	@Override
	public void execute() {
		if (model.curDrawShape != null){
			model.shapeList.add(grimShape);
			model.curDrawShape = null;
		}
	}

	@Override
	public void undo() {
		model.shapeList.remove(model.shapeList.size()-1);
	}

}
