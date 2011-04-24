package net.ericaro.neoitertools.iterators;

import java.util.Iterator;

/**
 * an iterator that returns selected elements from the iterator. If start is
 * non-zero, then elements from the iterator are skipped until start is reached.
 * Afterward, elements are returned consecutively unless step is set higher than
 * one which results in items being skipped. It stops at the specified position.
 * slice() does not support negative values for start, stop, or step.
 * 
 * @author eric
 * 
 * @param <T>
 */
public class SliceIterator<T> implements Iterator<T> {
	private final int step;
	private final Iterator<T> iterator;
	private final int stop;
	int i;
	T next;

	public SliceIterator(Iterator<T> iterator, int start, int stop, int step) {
		this.iterator = iterator;
		this.step = step;
		this.stop = stop;
		i = 0;
		while (i < start && iterator.hasNext()) {
			iterator.next();
			i++;
		}
	}

	public boolean hasNext() {
		return i < stop && iterator.hasNext();
	}

	public T next() {
		T current = iterator.next();
		// now move to the next -1 one
		int k = i;
		i++;
		while (i < k + step && i < stop && iterator.hasNext()) { // consume has
																	// much as
			// possible
			i++;
			next = iterator.next();// consume item
		}
		return current;

	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}