package restoreTemplate;

import controller.DrawingController;
import shapes.Shape;

public abstract class RestoreTemplate {

	public final void restoreShape(DrawingController controller, Shape shape)
	{
		
		executeRestore(controller,shape,getInputDialog(shape));
	}
	
	public abstract String getInputDialog(Shape shape);
	public abstract void executeRestore(DrawingController controller,Shape shape,String getVersionSelected);

	
}
