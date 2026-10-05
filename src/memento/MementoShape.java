package memento;
import shapes.Circle;
import shapes.Ellipse;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;
import shapes.Square;
import shapes.Triangle;


public class MementoShape {

	private Shape stateOfShape ;
	
	public MementoShape(Shape stateOfShape)
	{
		if(stateOfShape instanceof Triangle)
		{
			this.stateOfShape = ((Triangle)stateOfShape).clone();
		}
		else if(stateOfShape instanceof Ellipse)
		{
			this.stateOfShape = ((Ellipse)stateOfShape).clone();
		}
		else if(stateOfShape instanceof Line)
		{
			this.stateOfShape = ((Line)stateOfShape).clone();
		}
		else if(stateOfShape instanceof Point)
		{
			this.stateOfShape = ((Point)stateOfShape).clone();
		}
		else if(stateOfShape instanceof Rectangle)
		{
			this.stateOfShape = ((Rectangle)stateOfShape).clone();
		}
		else if(stateOfShape instanceof Square)
		{
			this.stateOfShape = ((Square)stateOfShape).clone();
		}
		else if(stateOfShape instanceof Circle)
		{
			this.stateOfShape = ((Circle)stateOfShape).clone();
		}
		
	}
		

	public Shape getStateOfShape() {
		return stateOfShape;
	}
	
	
	
	
}
