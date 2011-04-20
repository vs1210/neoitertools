package net.ericaro.neoitertools.primitives;

import java.util.Iterator;

public abstract class PrimitiveIterator<T> implements Iterator<T>{

	protected int i;
	private int to;
	
	protected PrimitiveIterator(int from, int to) {
		super();
		this.i= from;
		this.to = to;
	}

	public boolean hasNext() {
		return i < to;
	}

	public T next() {
		return get(i++);
	}

	protected abstract T get(int j);

	public void remove() {
		throw new UnsupportedOperationException();
	}


}
