package decorator;

import java.awt.Color;
import shapes.Circle;
import shapes.Ellipse;
import shapes.Line;
import shapes.Point;
import shapes.Rectangle;
import shapes.Shape;
import shapes.Square;
import shapes.Triangle;

public class ShapeDecorator  {

	public ShapeDecorator(Shape shape)
	{
		
		if (shape instanceof Triangle)
		{
			((Triangle) shape).setInteriorColor(Color.CYAN);
		}
		if (shape instanceof Ellipse)
		{
			((Ellipse) shape).setInteriorColor(Color.YELLOW);
		}
		if (shape instanceof Square)
		{
			((Square) shape).setInteriorColor(Color.ORANGE);
		}
		if (shape instanceof Rectangle)
		{
			((Rectangle) shape).setInteriorColor(Color.MAGENTA);
		}
		if (shape instanceof Circle)
		{
			((Circle) shape).setInteriorColor(Color.GREEN);
		}
		if (shape instanceof Point)
		{
			((Point) shape).setColor(Color.BLUE);
		}
		if (shape instanceof Line)
		{
			((Line) shape).setColor(Color.RED);
		}
	}
}
