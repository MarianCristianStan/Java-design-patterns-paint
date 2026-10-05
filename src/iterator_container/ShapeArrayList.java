package iterator_container;

import java.util.ArrayList;

import shapes.Shape;

public class ShapeArrayList extends Array<Shape> {

	private ArrayList<Shape> list;
	private ShapeArrayListIterator shapeIterator;

	public ShapeArrayList() {
		this.list = new ArrayList<Shape>();
	}

	public ShapeArrayList(ArrayList<Shape> l) {

		this.list = new ArrayList<Shape>();
		for (int i = 0; i < l.size(); i++) {
			this.list.add(l.get(i));
		}
	}

	public int numberOfElements() {
		return this.list.size();

	}

	public void add(Shape item) {

		this.list.add(item);
		//shapeIterator.updateContainer(this.list);
	}

	public void add(Shape item, int index) {

		this.list.add(index, item);
		//shapeIterator.updateContainer(this.list);
	}

	public void remove(Shape item) {
		this.list.remove(item);
		//shapeIterator.updateContainer(this.list);
	}

	public Iterator<Shape> createIterator() {
		shapeIterator = new ShapeArrayListIterator(this.list);
		return (ShapeArrayListIterator) shapeIterator;
	}
}
