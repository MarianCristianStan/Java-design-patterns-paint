package iterator_container;

public abstract class Array<T> {

	public abstract Iterator<T> createIterator();

	public abstract int numberOfElements();

	public abstract void add(T item);

	public abstract void remove(T item);

}
