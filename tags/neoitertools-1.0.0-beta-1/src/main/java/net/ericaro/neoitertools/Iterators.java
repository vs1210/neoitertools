package net.ericaro.neoitertools;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.primitives.BooleanIterator;
import net.ericaro.neoitertools.primitives.ByteIterator;
import net.ericaro.neoitertools.primitives.CharacterIterator;
import net.ericaro.neoitertools.primitives.DoubleIterator;
import net.ericaro.neoitertools.primitives.FloatIterator;
import net.ericaro.neoitertools.primitives.IntegerIterator;
import net.ericaro.neoitertools.primitives.LongIterator;
import net.ericaro.neoitertools.primitives.ShortIterator;


/** <p>This module implements a number of iterator building blocks inspired by constructs from the Python programming languages. 
 * Each has been recast in a form suitable for Java.</p>
 *
 * The module standardizes a core set of fast, memory efficient tools that are useful by themselves or in combination. 
 * Standardization helps avoid the readability and reliability problems which arise when many different individuals create their own slightly varying implementations, each with their own quirks and naming conventions.
 * 
 * The tools are designed to combine readily with one another. This makes it easy to construct more specialized tools succinctly and efficiently in pure Java.
 * 
 * 
 * @author eric
 *
 */
public class Iterators {
	
	/** Simple utility that contains an empty iterator
	 * 
	 * @author eric
	 *
	 * @param <T>
	 */
	static final class EmptyIterator<T> implements Iterator<T> {

		public boolean hasNext() {
			return false;
		}

		public T next() {
			return null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	
	/** A simple Iterator that returns a single value.
	 * 
	 * @author eric
	 *
	 * @param <T>
	 */
	static final class SingletonIterator<T> implements Iterator<T> {
		boolean returned = false;
		T t;

		public SingletonIterator(T t) {
			super();
			this.t = t;
		}

		public boolean hasNext() {
			return !returned;
		}

		public T next() {
			if (returned)
				throw new NoSuchElementException();
			returned = true;
			return t;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	
	/**Return True if all elements of the iterator are evaluated to true with the Predicate (or if the iterator is empty). 
	 * 
	 * @param <T>
	 * @param iterator
	 * @param predicate
	 * @return
	 */
	public static <T> boolean all(Iterator<T> iterator, Predicate<T> predicate) {
		while (iterator.hasNext())
			if (!predicate.map(iterator.next()))
				return false;
		return true;
	}
	
	/** Return True if any element of the iterator is mapped to true. If the iterator is empty, return False. 
	 * 
	 * @param iterator
	 * @param predicate
	 * @return
	 */
	public static <T> boolean any(Iterator<T> iterator, Predicate<T> predicate) {
		while (iterator.hasNext())
			if (predicate.map(iterator.next()))
				return true;
		return false;
	}
	
	/**
	 * turn any Iterator into a list. Implementation uses a LinkedList
	 * 
	 * @param <T>
	 * @param iterator
	 * @return
	 */
	public static <T> List<T> asList(Iterator<T> iterator) {
		List<T> list = new LinkedList<T>();
		while(iterator.hasNext())
			list.add(iterator.next());
		return list;
	}
	
	/**
	 * Turn any Iterator of Character into a String
	 * 
	 * @param chars
	 * @return
	 */
	public static String asString(Iterator<Character> chars) {
		return asStringBuilder(chars).toString();
	}
	
	
	/**
	 * Turn any Iterator of Character into a StringBuilder
	 * 
	 * @param chars
	 * @return
	 */
	public static StringBuilder asStringBuilder(Iterator<Character> chars) {
		StringBuilder sb = new StringBuilder();
		while(chars.hasNext())
			sb.append(chars.next().charValue());
		return sb;
	}
	
	/** Make an iterator that returns elements from the first iterable until it is exhausted, then proceeds to the next iterable, until all of the iterables are exhausted. 
	 * Used for treating consecutive sequences as a single sequence.
	 * 
	 *  We do not use varargs due to an inner flaw in varargs that make them hard to combine with generics
	 * 
	 * @param iterators an iterator over iterators
	 * @return
	 */
	public static <T> Iterator<T> chain(final Iterator<Iterator<T>> iterators) {
		return new Iterator<T>() {
			Iterator<Iterator<T>> metaIterator = iterators;
			Iterator<T> currentIterator;
			Iterator<T> previousIterator;

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

		};
	}
	
	
	/** chain together two iterators.
	 * 
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static <T> Iterator<T> chain(Iterator<T> i1, Iterator<T> i2) {
		List<Iterator<T>> list = new LinkedList<Iterator<T>>();
		list.add(i1);
		list.add(i2);
		return chain(list.iterator());
	}
	
	
	
	/** equivalent to count(0)
	 * 
	 * @see Iterators#count(int)
	 * 
	 * @return
	 */
	public static Iterator<Integer> count() {
		return count(0);
	}

	/** Make an iterator that returns consecutive integers starting with n. 
	 * 
	 * @param n
	 * @return
	 */
	public static Iterator<Integer> count(final int n) {

		return new Iterator<Integer>() {
			int i = n;

			public boolean hasNext() {
				return true;
			}

			public Integer next() {
				return i++;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}
	
	
	
	
	/** Make an iterator that drops elements from the iterator as long as the predicate is true.
	 * Afterwards, returns every element. Note, the iterator does not produce any output until the predicate first becomes false, so it may have a lengthy start-up time. 
	 * 
	 * @param <T>
	 * @param iterator
	 * @param predicate
	 * @return
	 */
	public static <T> Iterator<T> dropwhile(final Iterator<T> iterator,
			final Predicate<T> predicate) {

		// consume the unwanted elements
		while (iterator.hasNext()) {
			T t = iterator.next();
			if (!predicate.map(t)) {
				// hey , t is the first element to return, then follow on the
				// iterator
				return chain(new SingletonIterator<T>(t), iterator);
			}
		}
		return new EmptyIterator<T>();
	}

	/** Return an {@link Iterator} of Index object. 
	 * The next() method of the iterator returned by enumerate() returns an Index containing a count (from start which defaults to 0) and the corresponding value obtained from iterating over iterator. 
	 * enumerate() is useful for obtaining an indexed series: (0, seq[0]), (1, seq[1]), (2, seq[2]), .... For example:
     * 
	 * <pre>for (Index index: enumerate( seasons ) ) 
	        System.out.println(index.i+" "+ index.value);
	  * </pre>
	  * gives:
	  * <pre>
		0 Spring
		1 Summer
		2 Fall
		3 Winter
	 * </pre>
	 * 
	 * @param iterator
	 * @return
	 */
	public static <T> Iterator<Index<T>> enumerate(final Iterator<T> iterator) {

		return new Iterator<Index<T>>() {
			Iterator<Integer> i1 = count();
			public boolean hasNext() {
				return iterator.hasNext();
			}

			public Index<T> next() {
				return new Index<T>(i1.next(), iterator.next());
			}

			public void remove() {
				iterator.remove();
			}
		};
	}
	
	/** Make an iterator that filters elements from iterator returning only those for which the predicate is True. 
	 *  
	 * 
	 * @param predicate
	 * @param iterator
	 * @return
	 */
	public static <T> Iterator<T> filter(final Predicate<T> predicate, final Iterator<T> iterator){
		return new Iterator<T>() {
			T next;
			boolean hasNext = false;
			boolean first = true;
			public boolean hasNext() {
				if (first ) {
					first = false;
					move();
				}
				return hasNext;
			}

			private void move(){
				hasNext =false;
				while (iterator.hasNext() ){
					next = iterator.next() ;
					if (predicate.map(next) ){
						hasNext =true;
						break ;// 
					}
				}
			}

			public T next() {
				if (first ) {
					first = false;
					move();
				}
				try{
					return next;
				}
				finally{
					move();
				}
				
			}
			public void remove() {throw new UnsupportedOperationException();		}
			
		};
	}
	
	/** Make an iterator that filters elements from iterator returning only those for which the predicate is True. 
	 *  
	 * 
	 * @param predicate
	 * @param iterator
	 * @return
	 */
	public static <T> Iterator<T> filterfalse(final Predicate<T> predicate, final Iterator<T> iterator){
		return filter(new Predicate<T>() {

			public Boolean map(T arg) {
				return ! predicate.map(arg);
			}
		}, iterator);
	}
	
	/** Turns a {@link CharSequence} into an {@link Iterator} 
	 * 
	 * @param seq any {@link CharSequence}
	 * @return
	 */
	public static Iterator<Character> iter(final CharSequence seq) {
		return new Iterator<Character>() {
			int i = 0;

			public boolean hasNext() {
				return i < seq.length();
			}

			public Character next() {
				return seq.charAt(i++);
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};

	}

	/**
	 * Turn any Iterable into an Iterator
	 * 
	 * @param chars
	 * @return
	 */
	public static <T> Iterator<T> iter(Iterable<T> iterable) {
		return iterable.iterator();
	}

	
	/**
	 * Turns any byte[] array into an iterable
	 * 
	 * @param array
	 * @return an Iterator over array
	 */
	public static Iterator<Byte> iter(byte[] array) {
		return new ByteIterator(array);

	}


/**
	 * Turns any char[] array into an iterable
	 * 
	 * @param array
	 * @return an Iterator over array
	 */
	public static Iterator<Character> iter(char[] array) {
		return new CharacterIterator(array);

	}


/**
	 * Turns any short[] array into an iterable
	 * 
	 * @param array
	 * @return an Iterator over array
	 */
	public static Iterator<Short> iter(short[] array) {
		return new ShortIterator(array);

	}


/**
	 * Turns any int[] array into an iterable
	 * 
	 * @param array
	 * @return an Iterator over array
	 */
	public static Iterator<Integer> iter(int[] array) {
		return new IntegerIterator(array);

	}


/**
	 * Turns any long[] array into an iterable
	 * 
	 * @param array
	 * @return an Iterator over array
	 */
	public static Iterator<Long> iter(long[] array) {
		return new LongIterator(array);

	}


/**
	 * Turns any float[] array into an iterable
	 * 
	 * @param array
	 * @return an Iterator over array
	 */
	public static Iterator<Float> iter(float[] array) {
		return new FloatIterator(array);

	}


/**
	 * Turns any double[] array into an iterable
	 * 
	 * @param array
	 * @return an Iterator over array
	 */
	public static Iterator<Double> iter(double[] array) {
		return new DoubleIterator(array);

	}


/**
	 * Turns any boolean[] array into an iterable
	 * 
	 * @param array
	 * @return an Iterator over array
	 */
	public static Iterator<Boolean> iter(boolean[] array) {
		return new BooleanIterator(array);

	}


	/**
	 * Turns any object array into an Iterator
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	public static <T> Iterator<T> iter(final T[] t) {
		return new Iterator<T>() {
			int i = 0;

			public boolean hasNext() {
				return i < t.length;
			}

			public T next() {
				return t[i++];
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}


	
	
	/** turns any {@link Iterator} into a list (that can be modified)
	 * 
	 * @param <T>
	 * @param iterator
	 * @return
	 */
	public static <T> List<T> list(Iterator<T> iterator) {
		List<T> list = new LinkedList<T>();
		while (iterator.hasNext())
			list.add(iterator.next());
		return list;
	}

	/** Apply {@link Mapper} to every item of <code>iterator</code> and return an iterator of the results. 
	 * 
	 * @param mapper
	 * @param iterator
	 * @return
	 */
	public static <I, O> Iterator<O> map(final Mapper<I, O> mapper,
			final Iterator<I> iterator) {
	
		return new Iterator<O>() {
	
			public boolean hasNext() {
				return iterator.hasNext();
			}
	
			public O next() {
				return mapper.map(iterator.next());
			}
	
			public void remove() {
				iterator.remove();
			}
	
		};
	
	}

	/** equivalent to <pre>range(0, end, 1)</pre>
	 * @see Iterators#range(int, int, int)
	 * @param end
	 * @return
	 */public static Iterator<Integer> range(final int end){
		return range(0,end,1);
	}

	/** equivalent to <pre>range(start, end, 1)</pre>
	 * @see Iterators#range(int, int, int)
	 * @param start
	 * @param end
	 * @return
	 */
	public static Iterator<Integer> range(int start, int end){
		return range(start,end,1);
	}

	/** This is a versatile function to create iterator containing arithmetic progressions. 
	 * It is most often used in for loops. 
	 * The full form returns an iterator over Integers [start, start + step, start + 2 * step, ...]. 
	 * <ul><li>If step is positive, the last element is the largest start + i * step less than stop; </li>
	 * <li>if step is negative, the last element is the smallest start + i * step greater than stop.</li>
	 * <li>step must not be zero (or else InvalidParameterException is raised).</li> 
	 * Example:
	 *	<pre>range(0, 30, 5);</pre>
	 *	gives <pre>[0, 5, 10, 15, 20, 25]</pre>
	 *	
	 * <pre>range(0, 10, 3);</pre>
	 * gives <pre>[0, 3, 6, 9];</pre>
	 * <pre>range(0, -10, -1);</pre>
	 * gives <pre>[0, -1, -2, -3, -4, -5, -6, -7, -8, -9];</pre>
	 * 
	 * @param start
	 * @param end
	 * @param step
	 * @return
	 */
	public static Iterator<Integer> range(final int start, final int end, final int step) throws InvalidParameterException{
		if (step==0) throw new InvalidParameterException("step must be != 0");
		return new Iterator<Integer>() {
			int i=start;

			public boolean hasNext() {
				return step>0?i<end:i>end;
			}

			public Integer next() {
				try{
					return i;
				}finally{
					i+=step;
				}
			}

			public void remove() {throw new UnsupportedOperationException();}
			
		};
	}
	/** equivalent to reduce(operator, iterator, null);
	 * 
	 * @param <T>
	 * @param operator
	 * @param iterator
	 * @return
	 */
	public static <T> T reduce( Operator<T> operator, Iterator<T> iterator){
		return reduce(operator, iterator, null);
	}

	/** Apply function of two arguments cumulatively to the items of iterator, from left to right, 
	 * so as to reduce the iterator to a single value. 
	 * For example, 
	 * <pre>
	 * Operator<Integer> iadd = new Operator<Integer>() {
			public Integer operate(Integer t1, Integer t2) {
				return t1+t2;
			}
		};
		Integer res = reduce(iadd , range(1,6), 0);
		</pre>
		calculates
		<pre>
		((((1+2)+3)+4)+5)
		</pre>
		The left argument, x, is the accumulated value and the right argument, y, is the update value from the iterator. 
		If the initializer is not null, it is placed before the items of the iterator in the calculation, and serves as a default when the iterable is empty. 
		If initializer is null and iterator contains only one item, the first item is returned.
	 * 
	 * @param operator
	 * @param iterator
	 * @param initializer
	 * @return
	 */
	public static <T> T reduce( Operator<T> operator, Iterator<T> iterator, T initializer){
		if (!iterator.hasNext()) return initializer;
		if (initializer==null)
			initializer = iterator.next();
		while(iterator.hasNext())
			initializer = operator.operate(initializer, iterator.next());
		return initializer;
	}

	/** equivalent to {@link Iterators#slice}(0, stop, 1);
	 * 
	 * @param <T>
	 * @param iterator
	 * @param start
	 * @param stop
	 * @return
	 */
	public static <T> Iterator<T> slice(final Iterator<T> iterator, final int stop) {
		return slice_0(iterator, stop, 1);
	}
	
	/** equivalent to {@link Iterators#slice}(start, stop, 1);
	 * 
	 * @param <T>
	 * @param iterator
	 * @param start
	 * @param stop
	 * @return
	 */
	public static <T> Iterator<T> slice(final Iterator<T> iterator, final int start, final int stop) {
		return slice(iterator, start, stop, 1);
	}
	
	/** Make an iterator that returns selected elements from the iterator. 
	 * If start is non-zero, then elements from the iterator are skipped until start is reached. 
	 * Afterward, elements are returned consecutively unless step is set higher than one which results in items being skipped. 
	 * It stops at the specified position. slice() does not support negative values for start, stop, or step. 
	 * 
	 * @param iterator
	 * @param start
	 * @param stop
	 * @param step
	 * @return
	 */
	public static <T> Iterator<T> slice(final Iterator<T> iterator,
			final int start, final int stop, final int step) {
		
		int i=0;
		while(i<start && iterator.hasNext())
			iterator.next() ;
		if (i<start) return new EmptyIterator<T>() ; // start was greater than the iterator
		// iterator is now ready to produce
		return slice_0(iterator, stop-start, step);
	}
	private static <T> Iterator<T> slice_0(final Iterator<T> iterator, final int stop, final int step) {
		if (!iterator.hasNext()) return new EmptyIterator<T>() ;
		
		return new Iterator<T>(){
			int i=0;
			boolean hasNext=false;
			T next = iterator.next();
			public boolean hasNext() {
				return i<=stop ;
			}

			private void move(){
				
				hasNext = true; // asumme it has next
				int k=0;
				while(k<step && iterator.hasNext()){ //consume has much as possible
					k++;
					next = iterator.next() ;// consume item
				}
				hasNext = ( k==step);
				
				
			}

			public T next() {
				try{
					return next;
				}finally{
					move();
				}
				
			}
			public void remove() {
				throw new UnsupportedOperationException() ;
			}
			
		};
	}
	

	/** return a sorted Iterator in natural ascending order of T.
	 * 
	 * @param <T>
	 * @param iterator
	 * @return
	 */
	public static <T extends Comparable<? super T>> Iterator<T> sorted(Iterator<T> iterator){
		List<T> list = list(iterator);
		Collections.sort(list);
		return list.iterator();
	}
	
	/** Return a new sorted iterator from the items in iterator.
	
	cmp specifies a custom Comparator of K.
	key specifies a {@link Mapper} that is used to extract a comparison key (K) from each iterator element. 
	reverse is a boolean value. If set to True, then the list elements are sorted as if each comparison were reversed.
	 * 
	 * @param <T>
	 * @param <K>
	 * @param iterator
	 * @param key
	 * @param reverse
	 * @return
	 */
	public static <T, K> Iterator<T> sorted(Iterator<T> iterator, final Comparator<? super K> cmp, final Mapper<T,K> key, final boolean reverse){
		// maps T into (K,T) to perform the sort
		Mapper<T, Couple<K, T>> valueToKeyValue = new Mapper<T, Couple<K,T>>() {
			public Couple<K, T> map(T arg) {
				return new Couple<K, T>(key.map(arg), arg);
			}
		};
		
		// Adversely maps (K,T) into T
		Mapper<Couple<K, T>,T > keyValueToValue = new Mapper<Couple<K,T>,T>() {
			public T map(Couple<K,T> arg) {
				return arg.f1;
			}
		};
		
		// use comparator of K to compare (K,T)
		Comparator<Couple<K, T>> keyComparator = new Comparator<Couple<K,T>>(){

			public int compare(Couple<K, T> o1, Couple<K, T> o2) {
				return (reverse?-1:1)*cmp.compare(o1.f0, o2.f0);
			}
			
		};
		
		List<Couple<K,T>> list = list(map(valueToKeyValue, iterator) );
		Collections.sort(list, keyComparator);
		
		return map( keyValueToValue, list.iterator());
	}
	/**Return a new sorted iterator from the items in iterator.
	 * the comparator is used to sort the iterator.
	 * 
	 * @param <T>
	 * @param <K>
	 * @param iterator
	 * @param key
	 * @param reverse
	 * @return
	 */
	public static <T> Iterator<T> sorted(Iterator<T> iterator, Comparator<? super T> cmp){
		List<T> list = list(iterator);
		Collections.sort(list, cmp);
		return list.iterator();
	}
	
	
	/**Return a new sorted iterator from the items in iterator.
	 * the Key Mapper is used to extract a key from T, and that key natural order is used to sort the whole iterator.
	 * 
	 * @param <T>
	 * @param <K>
	 * @param iterator
	 * @param key
	 * @param reverse
	 * @return
	 */
	public static <T, K extends Comparable<? super K>> Iterator<T> sorted(Iterator<T> iterator, final Mapper<T,K> key, final boolean reverse){
		return sorted(iterator, new Comparator<K>() {

			public int compare(K o1, K o2) {
				return o1.compareTo(o2);
			}}, key, reverse);
	}
	
	
	/** Turns any Iterator into a "tuple", here an unmodifiable {@link List}
	 * 
	 * @param iterator
	 * @return a 
	 */
	public static <T> List<T> tuple(Iterator<T> iterator) {
		return Collections.unmodifiableList(list(iterator));
	}
	
	/** This function returns an {@link Iterator} of tuple (unmodifiable List) , where the i-th couple contains the i-th element from each of the argument iterators. 
	 * The returned iterator is truncated in length to the length of the shortest argument sequence.
	 * 
	 *  Due to static typing of java, it is not possible to provide a generic length of iterator and at the same time provide mixed-type tuples, therefore every iterator
	 *  must be of type <code>T</code>. To have two-mixed type use {@link Iterators#zip(Iterator, Iterator)}
	 * 
	 * 
	 * @param <T>
	 * @param iterators
	 * @return
	 */
	public static <T> Iterator<List<T>> zip(Iterator<Iterator<T>> iterators) {
		final List<Iterator<T>> iteratorList = list(iterators); 
		return new Iterator<List<T>>() {
	
			public boolean hasNext() {
				return all(iteratorList.iterator(), new Predicate<Iterator<T>>() {
					public Boolean map(Iterator<T> t) {
						return t.hasNext();
					}
				});
			}
	
			public List<T> next() {
				return tuple( 
						map(
								new Mapper<Iterator<T>, T>(){ 
									public T map(Iterator<T> t){return t.next();}}
							, iteratorList.iterator() 
							) );
			}
	
			public void remove() {
				for(Iterator<T> i : iteratorList)  i.remove();
			}
		};
	}
	
	/**
	 * This function returns an {@link Iterator} of Couples, where the i-th couple contains the i-th element from each of the argument iterators. 
	 * The returned iterator is truncated in length to the length of the shortest argument sequence.
	 * 
	 *  Due to static typing of java, it is not possible to provide a generic length of iterator and at the same time provide mixed-type tuples.
	 * 
	 * @param iterator1
	 * @param iterator2
	 * @return
	 */
	public static <T1, T2> Iterator<Couple<T1, T2>> zip(final Iterator<T1> iterator1,
			final Iterator<T2> iterator2) {

		return new Iterator<Couple<T1, T2>>() {

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
		};
	}
		
	
	/** Return a reverse iterator. 
	 *  The whole iterator is stored, so be careful when used.
	 * @param <T>
	 * @param iterator
	 * @return
	 */
	public static <T> Iterator<T> reversed(Iterator<T> iterator){
		List<T> list = list(iterator);
		Collections.reverse(list);
		return list.iterator() ;
	}
	
	
	
	
	
	// TODO groupby(Iterator<T> iterator, Mapper<T, K> key ){}
	// TODO cycle
	// TODO product
	// TODO repeat
	// TODO takewhile
	// TODO tee
}
