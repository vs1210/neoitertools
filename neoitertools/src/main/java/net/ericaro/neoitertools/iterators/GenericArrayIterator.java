package net.ericaro.neoitertools.iterators;

import java.util.Iterator;

/** Turn any object array into an iterator.
 * 
 * @author eric
 *
 * @param <T>
 */
public class GenericArrayIterator<T> implements Iterator<T> {
	private final T[] t;
	int i = 0;

	public GenericArrayIterator(T[] t) {
		this.t = t;
	}

	public boolean hasNext() {
		return i < t.length;
	}

	public T next() {
		return t[i++];
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}