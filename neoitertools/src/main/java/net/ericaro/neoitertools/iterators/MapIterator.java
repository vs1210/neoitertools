package net.ericaro.neoitertools.iterators;

import java.util.Iterator;

import net.ericaro.neoitertools.Mapper;

/** Apply {@link Mapper} to every item of <code>iterator</code> and return an
	 * iterator of the results.
	 * 
	 * @param mapper
	 * @param iterator
	 * @return
 * 
 * @author eric
 *
 * @param <O>
 * @param <I>
 */
public  class MapIterator<O, I> implements Iterator<O> {
	private final Iterator<I> iterator;
	private final Mapper<I, O> mapper;

	public MapIterator(Iterator<I> iterator, Mapper<I, O> mapper) {
		this.iterator = iterator;
		this.mapper = mapper;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public O next() {
		return mapper.map(iterator.next());
	}

	public void remove() {
		iterator.remove();
	}
}