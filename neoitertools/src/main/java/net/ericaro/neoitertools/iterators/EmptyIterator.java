package net.ericaro.neoitertools.iterators;

import java.util.Iterator;

/**
 * Simple utility that contains an empty iterator
 * 
 * @author eric
 * 
 * @param <T>
 */
public class EmptyIterator<T> implements Iterator<T> {

	public boolean hasNext() {
		return false;
	}

	public T next() {
		return null;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}