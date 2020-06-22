/**
 * Created on 2015. 4. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimtalk.command;

import hufs.ces.grimtalk.core.GrimTalkModel;
import hufs.ces.grimtalk.svg.SVGGrimShape;

/**
 * @author cskim
 *
 */
public class DelCommand implements Command {
	GrimTalkModel model = null;
	SVGGrimShape saveDeletedShape = null;
	
	public DelCommand(GrimTalkModel model){
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
