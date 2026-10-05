package interpreter;

public class EllipseAreaExpression implements Expression {

	private int EllipseWidth;
	private int EllipseHeight;

	EllipseAreaExpression(int width, int height) {
		this.EllipseWidth = width;
		this.EllipseHeight = height;
	}

	public double evaluate() {
		return EllipseWidth * EllipseHeight * 3.14;
	}
}
