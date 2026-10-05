package shapes;

import java.awt.Color;
import java.awt.Graphics;

import memento.CareTaker;
import memento.MementoShape;

public class Triangle extends SurfaceShape {

	private static final long serialVersionUID = 1L;
	protected Point pointLeft;
	protected int base;
	protected int height;

	private CareTaker careTaker;

	public CareTaker getCareTaker() {
		return careTaker;
	}

	public void initCareTaker() {
		careTaker = new CareTaker();
	}

	public void createSavepoint() {
		careTaker.saveMemento(new MementoShape(this));
	}

	public Triangle getInitialTriangle() {
		Triangle triangle = (Triangle) careTaker.getMemento("Version 1").getStateOfShape();
		return triangle;

//		this.pointLeft = triangle.getLeftPoint();
//		this.base = triangle.getBase();
//		this.height = triangle.getHeight();
//		this.setColor(triangle.getColor());
//		this.setInteriorColor(triangle.getInteriorColor());
//		careTaker.clearRestorePoints();

	}

	public Triangle getTriangleBySavepoint(String savepoint) {
		Triangle triangle = (Triangle) careTaker.getMemento(savepoint).getStateOfShape();
		return triangle;
	}

	public Triangle() {
	}

	public Triangle(Point point1, int base, int height) {
		this.pointLeft = point1;
		this.base = base;
		this.height = height;

	}

	public Triangle(Point point1, int base, int height, Color edgeColor, Color interiorColor) {
		this.pointLeft = point1;
		this.base = base;
		this.height = height;
		setColor(edgeColor);
		setInteriorColor(interiorColor);
	}

	public void draw(Graphics graphics) {
		graphics.setColor(getColor());

		graphics.drawPolygon(
				new int[] { pointLeft.getXcoordinate(), pointLeft.getXcoordinate() + base / 2,
						pointLeft.getXcoordinate() + base },
				new int[] { pointLeft.getYcoordinate(), pointLeft.getYcoordinate() - height,
						pointLeft.getYcoordinate() },
				3);
		fillUpShape(graphics);
		if (isSelected())
			selected(graphics);

	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Triangle) {
			Triangle triangle = (Triangle) obj;
			return pointLeft.equals(triangle.pointLeft) && base == triangle.base && height == triangle.height;
		}
		return false;
	}

	@Override
	public int compareTo(Shape shape) {
		if (shape instanceof Triangle)
			return (int) (surface() - ((Triangle) shape).surface());
		return 0;

	}

	public String toString() {
		return "Triangle: x=" + pointLeft.getXcoordinate() + "; y=" + pointLeft.getYcoordinate() + "; base=" + base
				+ "; height=" + height + "; edge color=" + getColor().toString().substring(14).replace('=', '-')
				+ "; area color=" + getInteriorColor().toString().substring(14).replace('=', '-');
	}

	public void moveTo(int x, int y) {
		pointLeft.moveTo(x, y);
	}

	public void selected(Graphics graphics) {
		graphics.setColor(Color.BLUE);
		new Line(pointLeft, new Point(pointLeft.getXcoordinate() + base, pointLeft.getYcoordinate()))
				.selected(graphics);
		new Line(pointLeft, new Point(pointLeft.getXcoordinate() + base / 2, pointLeft.getYcoordinate() - height))
				.selected(graphics);
		new Line(new Point(pointLeft.getXcoordinate() + base / 2, pointLeft.getYcoordinate() - height),
				new Point(pointLeft.getXcoordinate() + base, pointLeft.getYcoordinate())).selected(graphics);

	}

	public boolean containsClick(int x, int y) {

		/*
		 * if (this.pointLeft.getXcoordinate() <= x && x <=
		 * (this.pointLeft.getXcoordinate() + width) && this.pointLeft.getYcoordinate()
		 * >= y && y >= (this.pointLeft.getYcoordinate() - height)) return true; return
		 * false;
		 */

		int xBottomLeft = pointLeft.getXcoordinate();
		int yBottomLeft = pointLeft.getYcoordinate();
		int xTop = pointLeft.getXcoordinate() + base / 2;
		int yTop = pointLeft.getYcoordinate() - height;
		int xBottomRight = pointLeft.getXcoordinate() + base;
		int yBottomRight = pointLeft.getYcoordinate();
		boolean b1, b2, b3;

		b1 = (x - xBottomLeft) * (yTop - yBottomLeft) - (xTop - xBottomLeft) * (y - yBottomLeft) > 0;
		b2 = (x - xTop) * (yBottomRight - yTop) - (xBottomRight - xTop) * (y - yTop) > 0;
		b3 = (x - xBottomRight) * (yBottomLeft - yBottomRight) - (xBottomLeft - xBottomRight) * (y - yBottomRight) > 0;

		return ((b1 == b2) && (b2 == b3));

	}

	public Triangle clone() {
		return new Triangle(this.pointLeft.clone(), this.base, this.height, getColor(), getInteriorColor());

	}

	public void fillUpShape(Graphics graphics) {
		graphics.setColor(getInteriorColor());
		graphics.fillPolygon(
				new int[] { pointLeft.getXcoordinate() + 1, pointLeft.getXcoordinate() + base / 2,
						pointLeft.getXcoordinate() + base - 1 },
				new int[] { pointLeft.getYcoordinate(), pointLeft.getYcoordinate() - height + 1,
						pointLeft.getYcoordinate() },
				3);
	}

	public double surface() {
		return (this.base * this.height) / 2;
	}

	public Point getLeftPoint() {
		return this.pointLeft;
	}

	public int getBase() {
		return this.base;
	}

	public int getHeight() {
		return this.height;
	}

	public void setBase(int base) {
		this.base = base;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setLeftPoint(Point point) {
		this.pointLeft = point;

	}

}