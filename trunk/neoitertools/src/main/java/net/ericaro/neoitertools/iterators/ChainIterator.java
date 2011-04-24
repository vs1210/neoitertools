package net.ericaro.neoitertools.iterators;

import java.util.Iterator;

/**
 * Make an iterator that returns elements from the first iterators until it is
 * exhausted, then proceeds to the next iterator, until all of the iterators are
 * exhausted. Used for treating consecutive sequences as a single sequence.
 * 
 * We do not use varargs due to an inner flaw in varargs that make them
 * hard/impossible to combine with generics
 * 
 * @author eric
 * 
 */
public class ChainIterator<T> implements Iterator<T> {
	Iterator<Iterator<T>> metaIterator;
	Iterator<T> currentIterator;
	Iterator<T> previousIterator;

	/**
	 * Make an iterator that returns elements from the first iterators until it
	 * is exhausted, then proceeds to the next iterator, until all of the
	 * iterators are exhausted. Used for treating consecutive sequences as a
	 * single sequence.
	 * 
	 * We do not use varargs due to an inner flaw in varargs that make them
	 * hard/impossible to combine with generics
	 * 
	 * @param iterators
	 */
	public ChainIterator(Iterator<Iterator<T>> iterators) {
		metaIterator = iterators;
	}

	public boolean hasNext() {
		move();
		return currentIterator.hasNext();
	}

	public void move() {
		if (currentIterator == null)
			currentIterator = metaIterator.next();
		// move to the next iterator
		while (!currentIterator.hasNext() && metaIterator.hasNext())
			currentIterator = metaIterator.next();
		// either currentIterator has next value, or I've exhausted
		// the metaIterator
	}

	public T next() {
		try {
			move();
			return currentIterator.next();
		} finally {
			previousIterator = currentIterator; // store the iterator
												// that causes the next,
												// for the remove method
			move();
		}
	}

	public void remove() {
		previousIterator.remove();
	}
}