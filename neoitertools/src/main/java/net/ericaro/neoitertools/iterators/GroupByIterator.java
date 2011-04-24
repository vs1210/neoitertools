package net.ericaro.neoitertools.iterators;

import java.util.Iterator;

import net.ericaro.neoitertools.Couple;
import net.ericaro.neoitertools.Mapper;

/**
 * An iterator that returns consecutive keys and groups from the source
 * iterator. The key is a function computing a key value for each element.
 * Generally, the iterator needs to already be sorted on the same key function.
 * 
 * The operation of groupby() is similar to the uniq filter in Unix. It
 * generates a break or new group every time the value of the key function
 * changes (which is why it is usually necessary to have sorted the data using
 * the same key function). That behavior differs from SQL’s GROUP BY which
 * aggregates common elements regardless of their input order.
 * 
 * The returned group is itself an iterator that shares the underlying iterator
 * with groupby(). Because the source is shared, when the groupby() object is
 * advanced, the previous group is no longer visible. So, if that data is needed
 * later, it should be stored as a list.
 * 
 * @author eric
 * 
 * @param <K>
 * @param <T>
 */
public class GroupByIterator<K, T> implements Iterator<Couple<K, Iterator<T>>> {

	private Iterator<T> iterator; // the source iterator
	private Mapper<T, K> keyMapper; // the mapper
	private boolean hasNext; // a boolean used to avoid calculation in the
								// hasnext method
	private T next = null; // the next item (or null)
	private Iterator<T> nexts;
	private K key;

	public GroupByIterator(Iterator<T> iterator, Mapper<T, K> key) {
		super();
		this.iterator = iterator;
		this.keyMapper = key;
		hasNext = iterator.hasNext();
		if (hasNext)
			next = iterator.next();
		nexts = createNexts();
	}

	private Iterator<T> createNexts() {
		return new Iterator<T>() {
			boolean hasNext = true;

			public boolean hasNext() {
				return hasNext;
			}

			public T next() {
				T current = next;
				if (iterator.hasNext()) {
					next = iterator.next();
					if (!key.equals(keyMapper.map(next)))
						hasNext = false;
				}
				return current;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	public boolean hasNext() {
		return hasNext;
	}

	public Couple<K, Iterator<T>> next() {

		// alg, get the next one, get it's key, it's a new key (always)

		key = keyMapper.map(next);

		return new Couple<K, Iterator<T>>(key, nexts);
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
