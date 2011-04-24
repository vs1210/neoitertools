package net.ericaro.neoitertools.iterators;

import java.util.Iterator;

import net.ericaro.neoitertools.Index;
import net.ericaro.neoitertools.Iterators;

/**
 * An {@link Iterator} of Index object. The next() method of the iterator
 * returns an Index containing a count (from start which
 * defaults to 0) and the corresponding value obtained from iterating over
 * iterator. enumerate() is useful for obtaining an indexed series: (0, seq[0]),
 * (1, seq[1]), (2, seq[2]), .... For example:
 * 
 * <pre>
 * for (Index index : enumerate(seasons))
 * 	System.out.println(index.i + &quot; &quot; + index.value);
 * </pre>
 * 
 * gives:
 * 
 * <pre>
 * 		0 Spring
 * 		1 Summer
 * 		2 Fall
 * 		3 Winter
 * </pre>
 * 
 * @author eric
 * 
 * @param <T>
 */
public class EnumerateIterator<T> implements Iterator<Index<T>> {
	private final Iterator<T> iterator;
	Iterator<Integer> i1 = Iterators.count();

	public EnumerateIterator(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public Index<T> next() {
		return new Index<T>(i1.next(), iterator.next());
	}

	public void remove() {
		iterator.remove();
	}
}