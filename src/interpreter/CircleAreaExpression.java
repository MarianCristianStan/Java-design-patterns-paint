package interpreter;

public class CircleAreaExpression implements Expression {

	private int circleRadius;

	CircleAreaExpression(int radius) {
		this.circleRadius = radius;
	}

	public double evaluate() {
		return circleRadius * circleRadius * 3.14;
	}

}
