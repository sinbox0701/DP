/**
 * Created on 2015. 4. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.grimtalk.command;

import java.util.ArrayList;

import hufs.ces.grimtalk.core.GrimTalkModel;
import hufs.ces.grimtalk.svg.SVGGrimShape;
import javafx.scene.shape.Shape;

/**
 * @author cskim
 *
 */
public class RemoveCommand implements Command {

	GrimTalkModel model = null;
	ArrayList<SVGGrimShape> temp = new ArrayList<SVGGrimShape>();
	SVGGrimShape saveDeletedShape = null;
	public RemoveCommand(GrimTalkModel model){
		this.model = model;
	}

	@Override
	public void execute() {
		while(model.shapeList.size()>0) {
			temp.add(model.shapeList.get(model.shapeList.size()-1));
			model.shapeList.remove(model.shapeList.size()-1);
		}
		
	}

	@Override
	public void undo() {
		System.out.println(temp.size());
		while(temp.size()>0) {
			model.shapeList.add(temp.get(temp.size()-1));
			temp.remove(temp.size()-1);
		}
	}

}
