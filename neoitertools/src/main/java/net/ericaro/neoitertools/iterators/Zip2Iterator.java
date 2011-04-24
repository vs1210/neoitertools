package net.ericaro.neoitertools.iterators;

import java.util.Iterator;

import net.ericaro.neoitertools.Couple;

/**
 * an {@link Iterator} of Couples, where the i-th couple contains the i-th
 * element from each of the argument iterators. The returned iterator is
 * truncated in length to the length of the shortest argument sequence.
 * 
 * Due to static typing of java, it is not possible to provide a generic length
 * of iterator and at the same time provide mixed-type tuples.
 * 
 * @author eric
 * 
 */
public class Zip2Iterator<T1, T2> implements Iterator<Couple<T1, T2>> {
	private final Iterator<T1> iterator1;
	private final Iterator<T2> iterator2;

	public Zip2Iterator(Iterator<T1> iterator1, Iterator<T2> iterator2) {
		this.iterator1 = iterator1;
		this.iterator2 = iterator2;
	}

	public boolean hasNext() {
		return (iterator1.hasNext() && iterator2.hasNext());
	}

	public Couple<T1, T2> next() {
		return new Couple<T1, T2>(iterator1.next(), iterator2.next());
	}

	public void remove() {
		iterator1.remove();
		iterator2.remove();
	}
}