package iterator_container;

import java.util.ArrayList;
import shapes.Shape;

public class ShapeArrayListIterator extends Iterator<Shape> {

	protected ArrayList<Shape> list;

	public ShapeArrayListIterator() {
		// this.list = new ArrayList<Shape>();
	}

	public ShapeArrayListIterator(ArrayList<Shape> list) {
		this.list = list;
	}

	public void first() {
		current = 0;
	}

	public void last() {
		current = list.size() ;
	}

	public void next() {
		if (hasNext())
			current++;
	}

	public void previous() {
		if (hasPrevious())
			current--;
	}

	public boolean isDone() {
		if (current >= list.size())
			return true;
		return false;
	}

	public boolean hasNext() {
		if (current < list.size())
			return true;
		else
			return false;

	}

	public boolean hasPrevious() {
		if (current > 0)
			return true;
		else
			return false;

	}

	public Shape currentItem() {
		return list.get(current);

	}

	public int getCurrentIndex() {
		return current;

	}

//	public void updateContainer(ArrayList<Shape> newList) {
//		this.list = newList;
//	}
}
