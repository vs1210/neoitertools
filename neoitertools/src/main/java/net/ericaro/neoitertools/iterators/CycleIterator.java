package net.ericaro.neoitertools.iterators;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Make an iterator returning elements from the iterator and saving a copy of
 * each. When the iterator is exhausted, return elements from the saved copy.
 * Repeats indefinitely.
 * 
 * @author eric
 * 
 * @param <T>
 */
public class CycleIterator<T> implements Iterator<T> {

	private Iterator<T> iterator;
	private List<T> list;
	private boolean isEmpty;
	private boolean first;

	public CycleIterator(Iterator<T> iterator) {
		this.iterator = iterator;
		this.list = new LinkedList<T>();
		isEmpty = !iterator.hasNext();
		first = true;
	}

	public boolean hasNext() {
		return !isEmpty;
	}

	public T next() {
		if (first)
			return firstNexts();
		else
			return otherNexts();
	}

	/**
	 * until the "source" iterator is not exhausted, next goes this way
	 * 
	 * @return
	 */
	private T firstNexts() {
		if (iterator.hasNext()) {
			T t = iterator.next();
			list.add(t);
			return t;
		}
		// we reached the end of the first nexts, calling back the top level
		// method to go to the right method now
		first = false;
		return next();
	}

	/**
	 * the initial iterator is exhausted, use the list one for now on.
	 * 
	 * @return
	 */
	private T otherNexts() {
		// the field "iterator" for now on, will be an iterator over the list
		if (!iterator.hasNext()) // the iterator is empty, loop again
			iterator = list.iterator();
		return iterator.next();
	}

	public void remove() {
		// TODO Auto-generated method stub

	}

}
