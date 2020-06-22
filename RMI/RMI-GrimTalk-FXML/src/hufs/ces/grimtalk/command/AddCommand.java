/**
 * Created on 2015. 4. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimtalk.command;

import hufs.ces.grimtalk.core.GrimTalkModel;
import hufs.ces.grimtalk.svg.SVGGrimShape;
import javafx.scene.shape.Shape;

/**
 * @author cskim
 *
 */
public class AddCommand implements Command {

	GrimTalkModel model = null;
	SVGGrimShape grimShape = null;
	public AddCommand(GrimTalkModel model, SVGGrimShape grimShape){
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
