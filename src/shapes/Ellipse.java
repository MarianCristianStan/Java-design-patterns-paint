package shapes;

import java.awt.Color;
import java.awt.Graphics;

import memento.CareTaker;
import memento.MementoShape;

public class Ellipse extends SurfaceShape {

	private static final long serialVersionUID = 1L;
	private Point center;
	private int width;
	private int height;

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

	public Ellipse getInitialEllipse() {
		Ellipse ellipse = (Ellipse) careTaker.getMemento("Version 1").getStateOfShape();
		return ellipse;

	}

	public Ellipse getEllipseBySavepoint(String savepoint) {
		Ellipse ellipse = (Ellipse) careTaker.getMemento(savepoint).getStateOfShape();
		return ellipse;
	}

	public Ellipse() {
	}

	public Ellipse(Point center, int width, int height) {
		this.center = center;
		this.setWidth(width);
		this.setHeight(height);
	}

	public Ellipse(Point center, int width, int height, Color edgeColor, Color interiorColor) {
		this.center = center;
		this.setWidth(width);
		this.setHeight(height);
		setColor(edgeColor);
		setInteriorColor(interiorColor);
	}

	public void draw(Graphics graphics) {
		graphics.setColor(getColor());
		graphics.drawOval(center.getXcoordinate() - getWidth(), center.getYcoordinate() - getHeight(), 2 * getWidth(),
				2 * getHeight());
		fillUpShape(graphics);
		if (isSelected())
			selected(graphics);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ellipse) {
			Ellipse ellipse = (Ellipse) obj;
			return center.equals(ellipse.center) && ellipse.getWidth() == width && ellipse.getHeight() == height;
		}
		return false;
	}

	@Override
	public int compareTo(Shape shape) {
		if (shape instanceof Ellipse) {
			if (center.getXcoordinate() - ((Ellipse) shape).center.getXcoordinate() == 0)
				if (center.getYcoordinate() - ((Ellipse) shape).center.getYcoordinate() == 0)
					return (int) (this.surface() - ((Ellipse) shape).surface());
		}
		return 0;

	}

	public double surface() {
		return (this.width * this.height) * 3.14;
	}

	@Override
	public String toString() {
		return "Ellipse: x=" + center.getXcoordinate() + "; y=" + center.getYcoordinate() + "; width=" + width
				+ "; height=" + height + "; edge color=" + getColor().toString().substring(14).replace('=', '-')
				+ "; area color=" + getInteriorColor().toString().substring(14).replace('=', '-');
	}

	public void moveTo(int x, int y) {
		center.moveTo(x, y);
	}

	public void selected(Graphics graphics) {
		graphics.setColor(Color.BLUE);
		new Line(new Point(center.getXcoordinate(), center.getYcoordinate() - getHeight()),
				new Point(center.getXcoordinate(), center.getYcoordinate() + getHeight())).selected(graphics);
		new Line(new Point(center.getXcoordinate() - getWidth(), center.getYcoordinate()),
				new Point(center.getXcoordinate() + getWidth(), center.getYcoordinate())).selected(graphics);
	}

	public boolean containsClick(int x, int y) {

		double dx = (x - center.getXcoordinate());
		double dy = (y - center.getYcoordinate());
		return (dx * dx) / (width * width) + (dy * dy) / (height * height) <= 1;

	}

	public Ellipse clone() {
		return new Ellipse(center.clone(), getWidth(), getHeight(), getColor(), getInteriorColor());

	}

	public void fillUpShape(Graphics graphics) {
		graphics.setColor(getInteriorColor());
		graphics.fillOval(center.getXcoordinate() - getWidth() + 1, center.getYcoordinate() - getHeight() + 1,
				2 * getWidth() - 2, 2 * getHeight() - 2);
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
