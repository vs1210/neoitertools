package net.ericaro.neoitertools.iterators;

import java.util.Iterator;

/**
 * an iterator that returns object over and over again. Runs indefinitely Used
 * as argument to imap() for invariant function parameters. Also used with
 * izip() to create constant fields in a tuple record.
 * 
 * @author eric
 * 
 * @param <T>
 */
public class RepeatIterator<T> implements Iterator<T> {

	private T object;
	private int times;

	public RepeatIterator(T object) {
		this.object = object;
		this.times = Integer.MAX_VALUE;
	}

	public RepeatIterator(T object, int times) {
		this(object);
		assert times >= 0 : "times must be >=0";
		this.times = times;
	}

	public boolean hasNext() {
		return times > 0;
	}

	public T next() {
		if (times != Integer.MAX_VALUE) // max value is interpreted as infinity
			times--;
		return object;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
