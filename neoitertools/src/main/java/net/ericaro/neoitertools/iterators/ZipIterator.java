package net.ericaro.neoitertools.iterators;

import java.util.Iterator;
import java.util.List;

import net.ericaro.neoitertools.Iterators;
import net.ericaro.neoitertools.Mapper;
import net.ericaro.neoitertools.Predicate;

/** an {@link Iterator} of tuple (unmodifiable List) ,
	 * where the i-th couple contains the i-th element from each of the argument
	 * iterators. The returned iterator is truncated in length to the length of
	 * the shortest argument sequence.
	 * 
	 * Due to static typing of java, it is not possible to provide a generic
	 * length of iterator and at the same time provide mixed-type tuples,
	 * therefore every iterator must be of type <code>T</code>. To have
	 * two-mixed type use {@link Zip2Iterator}
 * 
 * @author eric
 *
 * @param <T>
 */
public class ZipIterator<T> implements Iterator<List<T>> {
	private final List<Iterator<T>> iteratorList;

	public ZipIterator(List<Iterator<T>> iteratorList) {
		this.iteratorList = iteratorList;
	}

	public boolean hasNext() {
		return Iterators.all(iteratorList.iterator(),
				new Predicate<Iterator<T>>() {
					public Boolean map(Iterator<T> t) {
						return t.hasNext();
					}
				});
	}

	public List<T> next() {
		return Iterators.tuple(Iterators.map(new Mapper<Iterator<T>, T>() {
			public T map(Iterator<T> t) {
				return t.next();
			}
		}, iteratorList.iterator()));
	}

	public void remove() {
		for (Iterator<T> i : iteratorList)
			i.remove();
	}
}