package interpreter;

import java.util.ArrayList;

public class AreaInterpreter {

	private ArrayList<Expression> evaluateExpressions = new ArrayList<Expression>();

	public AreaInterpreter(String s) {

		String[] tokensList = s.split(":");
		// get the shape type and parse the parts for expression for evaluate

		switch(tokensList[0])
		{
		case "Triangle":
			String[] triangleParts = tokensList[1].split(";");
			int baseTriangle = Integer.parseInt(triangleParts[2].split("=")[1]);
			int heightTriangle = Integer.parseInt(triangleParts[3].split("=")[1]);
			evaluateExpressions.add(new TriangleAreaExpression(baseTriangle, heightTriangle));
			break;
			
		case "Ellipse":
			String[] ellipseParts = tokensList[1].split(";");
			int widthEllipse = Integer.parseInt(ellipseParts[2].split("=")[1]);
			int heightEllipse = Integer.parseInt(ellipseParts[3].split("=")[1]);
			evaluateExpressions.add(new EllipseAreaExpression(widthEllipse, heightEllipse));
			break;
			
		case "Circle":
			String[] circleParts = tokensList[1].split(";");
			int radiusCircle = Integer.parseInt(circleParts[0].split("=")[1]);
			evaluateExpressions.add(new CircleAreaExpression(radiusCircle));
			break;

		case "Square":
			String[] squareParts = tokensList[1].split(";");
			int sideSquare = Integer.parseInt(squareParts[2].split("=")[1]);
			evaluateExpressions.add(new SquareAreaExpression(sideSquare));
			break;
			
		case "Rectangle":
			String[] rectangleParts = tokensList[1].split(";");
			int heightRectangle = Integer.parseInt(rectangleParts[2].split("=")[1]);
			int widthRectangle = Integer.parseInt(rectangleParts[3].split("=")[1]);
			evaluateExpressions.add(new RectangleAreaExpression(widthRectangle,heightRectangle));
			break;
	
		default:
			break;
		}
	}
	// evaluate area 
	public double evaluate() {
		double area = 0;
		for (var e : evaluateExpressions)
			area = e.evaluate();
		return area;
	}

}
