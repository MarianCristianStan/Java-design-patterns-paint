package facade;

import java.awt.event.MouseEvent;
import controller.DrawingController;
import model.DrawingModel;

public class ShapeFacadeAria {

	private DrawingController controller;
	private MouseEvent click;
	private String shape;

	public ShapeFacadeAria(DrawingController controller, MouseEvent click, String s) {
		this.controller = controller;
		this.click = click;
		this.shape = s;

	}

	public void useFacade() {
		DrawingModel model = controller.getModel();
		int sizeBeforeAdd = model.getAll().size();
		
		if (shape.compareTo("Triangle") == 0) {
			controller.btnAddTriangleClicked(click);
			if (model.getAll().size() != sizeBeforeAdd) {
				controller.AreaShape(model.getAll().get(model.getAll().size()-1));
			}
		}
		else if (shape.compareTo("Ellipse") == 0) {

			controller.btnAddEllipseClicked(click);
			if (model.getAll().size() != sizeBeforeAdd) {
				controller.AreaShape(model.getAll().get(model.getAll().size()-1));
			
			}
		}
		else if (shape.compareTo("Square") == 0) {

			controller.btnAddSquareClicked(click);
			if (model.getAll().size() != sizeBeforeAdd) {
				controller.AreaShape(model.getAll().get(model.getAll().size()-1));
			

			}
		}
		else if (shape.compareTo("Rectangle") == 0) {

			controller.btnAddRectangleClicked(click);
			if (model.getAll().size() != sizeBeforeAdd) {
				controller.AreaShape(model.getAll().get(model.getAll().size()-1));

			}
		}
		else if (shape.compareTo("Circle") == 0) {

			controller.btnAddCircleClicked(click);
			if (model.getAll().size() != sizeBeforeAdd) {
				controller.AreaShape(model.getAll().get(model.getAll().size()-1));
			}
		}
	}

}
