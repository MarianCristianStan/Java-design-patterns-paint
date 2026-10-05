package interpreter;

public class SquareAreaExpression implements Expression {

	private int SquareSide;

	SquareAreaExpression(int base) {
		this.SquareSide = base;
	}

	public double evaluate() {
		return SquareSide * SquareSide;
	}
}
