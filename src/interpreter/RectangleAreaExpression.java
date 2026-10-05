package interpreter;

public class RectangleAreaExpression implements Expression {

	private int rectangleWidth;
	private int rectangleHeight;

	RectangleAreaExpression(int width, int height) {
		this.rectangleWidth = width;
		this.rectangleHeight = height;
	}

	public double evaluate() {
		return rectangleWidth * rectangleHeight;
	}
}
