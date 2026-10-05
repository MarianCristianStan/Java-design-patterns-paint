package iterator_container;

public abstract class Iterator<T> {

	protected int current = 0;

	public abstract void first();

	public abstract void next();

	public abstract boolean hasNext();

	public abstract T currentItem();

	public abstract int getCurrentIndex();
	
	public abstract boolean isDone() ;
}
