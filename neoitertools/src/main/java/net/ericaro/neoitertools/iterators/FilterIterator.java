package net.ericaro.neoitertools.iterators;

import java.util.Iterator;

import net.ericaro.neoitertools.Predicate;

/** Make an iterator that filters elements from iterator returning only those
	 * for which the predicate is True (or false using the negate construct).
 * 
 * @author eric
 *
 * @param <T>
 */
public class FilterIterator<T> implements Iterator<T> {
	private final Iterator<T> iterator;
	private final Predicate<T> predicate;
	private T next;
	private boolean hasNext = false;
	private boolean first = true;
	private boolean neg = false;

	/** construct a filter that return items iif the predicate is true.
	 * 
	 * @param iterator
	 * @param predicate
	 */
	public FilterIterator(Iterator<T> iterator, Predicate<T> predicate) {
		this(iterator, predicate, false);
	}
	/** construct a filter that returns item iif the predicate XOR negate is true
	 * 
	 * @param iterator
	 * @param predicate
	 * @param negate
	 */
	public FilterIterator(Iterator<T> iterator, Predicate<T> predicate, boolean negate) {
		this.iterator = iterator;
		this.predicate = predicate;
		this.neg = negate;
	}

	public boolean hasNext() {
		if (first) {
			first = false;
			move();
		}
		return hasNext;
	}

	private void move() {
		hasNext = false;
		while (iterator.hasNext()) {
			next = iterator.next();
			if (neg^(predicate.map(next))) {
				hasNext = true;
				break;//
			}
		}
	}

	public T next() {
		if (first) {
			first = false;
			move();
		}
		try {
			return next;
		} finally {
			move();
		}

	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}