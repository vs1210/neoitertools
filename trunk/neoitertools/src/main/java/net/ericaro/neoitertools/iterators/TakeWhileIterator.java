package net.ericaro.neoitertools.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Predicate;

/**
 * an iterator that returns elements from the iterator as long as the predicate
 * is true.
 * 
 * 
 * @author eric
 * 
 * @param <T>
 */
public class TakeWhileIterator<T> implements Iterator<T> {

	private Iterator<T> iterator;
	private Predicate<T> predicate;
	private boolean hasNext;
	private T next;

	/**
	 * Make an iterator that returns elements from the iterator as long as the
	 * predicate is true.
	 * 
	 * @param iterator
	 * @param predicate
	 */
	public TakeWhileIterator(Iterator<T> iterator, Predicate<T> predicate) {
		this.iterator = iterator;
		this.predicate = predicate;
		next();
	}

	public boolean hasNext() {
		return hasNext;
	}

	public T next() {
		if (!hasNext)
			throw new NoSuchElementException();
		T current = next; // store the actual next for later return

		if (iterator.hasNext()) { // at least there is a candidate, let's check
									// it out
			next = iterator.next();
			hasNext = predicate.map(next); // check if next is ok
			if (!hasNext)
				next = null; // remove any link to the next for GC
		} else { // well there is even no candidate, so... end the iterator
			next = null;
			hasNext = false;
		}
		return current;
	}

	public void remove() {
		iterator.remove();
	}

}
