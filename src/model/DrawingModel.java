package model;

import iterator_container.*;

import java.io.Serializable;
import java.util.ArrayList;
import shapes.Shape;

/**
 * Represent model in MVC architectural pattern. Contains application data.
 *
 */
public class DrawingModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private ShapeArrayList shapes;


	public DrawingModel() {
		this.shapes = new ShapeArrayList();
		//iteratorShapes = (ShapeArrayListIterator) shapes.createIterator();
	}

	/**
	 * Add new shape.
	 * 
	 * @param shape Represent shape which will be added.
	 */
	public void add(Shape shape) {
		shapes.add(shape);

	}

	/**
	 * Add new shape to specified index.
	 * 
	 * @param index Represent index on which shape will be added.
	 * @param shape Represent shape which will be added.
	 * 
	 * 
	 * 
	 */

	public void addToIndex(int index, Shape shape) {

		shapes.add(shape, index);
	}

	/**
	 * Add multiple shapes to list of shapes.
	 * 
	 * @param list Elements that are be added.
	 */
	public void addMultiple(ArrayList<Shape> shapes) {

		for (var s : shapes)
			this.shapes.add(s);
	}

	/**
	 * Remove shape from list of shapes.
	 * 
	 * @param shape Shape to be removed.
	 */
	public void remove(Shape shape) {

		shapes.remove(shape);
	}

	/**
	 * Remove shape at specified index.
	 * 
	 * @param index Represent index of shape that will be removed.
	 */
	public void removeAtIndex(int index) {
		ShapeArrayListIterator iteratorShapes = (ShapeArrayListIterator) shapes.createIterator();
		
		for (iteratorShapes.first(); !iteratorShapes.isDone(); iteratorShapes.next())
			if (iteratorShapes.getCurrentIndex() == index) {
				shapes.remove(iteratorShapes.currentItem());
			}
	}

	/**
	 * Remove multiple shapes from list of shapes.
	 * 
	 * @param shapes Shapes to be removed.
	 */
	public void removeMultiple(ArrayList<Shape> shapes) {

		;
		for (var s : shapes) {
			this.shapes.remove(s);
		}
	}

	/**
	 * Remove all shapes from list of shapes.
	 */
	public void removeAll() {
		ShapeArrayListIterator iteratorShapes = (ShapeArrayListIterator) shapes.createIterator();
		iteratorShapes.first();
		while (!iteratorShapes.isDone()) {
			shapes.remove(iteratorShapes.currentItem());
			iteratorShapes.first();
		}
	}

	public Shape getByIndex(int index) {
		ShapeArrayListIterator iteratorShapes = (ShapeArrayListIterator) shapes.createIterator();
		for (iteratorShapes.first(); !iteratorShapes.isDone(); iteratorShapes.next()) {
			if (iteratorShapes.getCurrentIndex() == index)
				return iteratorShapes.currentItem();

		}

		return null;
	}

	public int getIndexOf(Shape shape) {
		ShapeArrayListIterator iteratorShapes = (ShapeArrayListIterator) shapes.createIterator();
		for (iteratorShapes.first(); !iteratorShapes.isDone(); iteratorShapes.next()) {
			if (iteratorShapes.currentItem().equals(shape))
				return iteratorShapes.getCurrentIndex();

		}
		return -1;

	}

	public ArrayList<Shape> getAll() {

		ShapeArrayListIterator iteratorShapes = (ShapeArrayListIterator) shapes.createIterator();
		ArrayList<Shape> auxList = new ArrayList<Shape>();
		for (iteratorShapes.first(); !iteratorShapes.isDone(); iteratorShapes.next())
			auxList.add(iteratorShapes.currentItem());

		return auxList;

	}
}