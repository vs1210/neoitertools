package net.ericaro.neoitertools.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple Iterator that returns a single value.
 * 
 * @author eric
 * 
 * @param <T>
 */
public class SingletonIterator<T> implements Iterator<T> {
	boolean returned = false;
	T t;

	public SingletonIterator(T t) {
		super();
		this.t = t;
	}

	public boolean hasNext() {
		return !returned;
	}

	public T next() {
		if (returned)
			throw new NoSuchElementException();
		returned = true;
		return t;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}