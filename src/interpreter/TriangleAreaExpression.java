package interpreter;

public class TriangleAreaExpression implements Expression {

	private int triangleBase;
	private int triangleHeight;

	TriangleAreaExpression(int base, int height) {
		this.triangleBase = base;
		this.triangleHeight = height;
	}

	public double evaluate() {
		return (triangleBase * triangleHeight / 2);
	}

}
