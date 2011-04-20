package net.ericaro.neoitertools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.ericaro.neoitertools.Iterators.EmptyIterator;

/** <p>This module implements a number of iterable building blocks inspired by constructs from the Python programming languages. 
 * Each has been recast in a form suitable for Java.</p>
 *
 * The module standardizes a core set of fast, memory efficient tools that are useful by themselves or in combination. 
 * Standardization helps avoid the readability and reliability problems which arise when many different individuals create their own slightly varying implementations, each with their own quirks and naming conventions.
 * 
 * The tools are designed to combine readily with one another. This makes it easy to construct more specialized tools succinctly and efficiently in pure Java.
 * 
 * @author eric
 *
 */
public class Iterables {

	protected static class Iterable2IteratorMapper<T> implements
			Mapper<Iterable<T>, Iterator<T>> {
		public Iterator<T> map(Iterable<T> arg) {
			return arg.iterator();
		}
	};
	

	/**
	 * Return True if all elements of the iterable are evaluated to true with
	 * the Predicate (or if the iterable is empty).
	 * 
	 * @param <T>
	 * @param iterable
	 * @param predicate
	 * @return
	 */
	public static <T> boolean all(Iterable<T> iterable, Predicate<T> predicate) {
		return Iterators.all(iterable.iterator(), predicate);
	}

	/**
	 * Return True if any element of the iterable is mapped to true. If the
	 * iterable is empty, return False.
	 * 
	 * @param iterable
	 * @param predicate
	 * @return
	 */
	public static <T> boolean any(Iterable<T> iterable, Predicate<T> predicate) {
		return Iterators.any(iterable.iterator(), predicate);
	}

	/**
	 * turn any Iterable into a list. Implementation uses a LinkedList
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	public static <T> List<T> asList(Iterable<T> iterable) {
		return Iterators.asList(iterable.iterator());
	}

	/**
	 * Turn any Iterable of Character into a String
	 * 
	 * @param chars
	 * @return
	 */
	public static String asString(Iterable<Character> chars) {
		return asStringBuilder(chars).toString();
	}

	/**
	 * Turn any Iterable of Character into a StringBuilder
	 * 
	 * @param chars
	 * @return
	 */
	public static StringBuilder asStringBuilder(Iterable<Character> chars) {
		StringBuilder sb = new StringBuilder();
		for (Character c : chars)
			sb.append(c.charValue());
		return sb;
	}

	/**
	 * Make an iterable that returns elements from the first iterable until it
	 * is exhausted, then proceeds to the next iterable, until all of the
	 * iterables are exhausted. Used for treating consecutive sequences as a
	 * single sequence.
	 * 
	 * We do not use varargs due to an inner flaw in varargs that make them hard
	 * to combine with generics
	 * 
	 * @param iterators
	 *            an iterator over iterators
	 * @return
	 */
	public static <T> Iterable<T> chain(final Iterable<Iterable<T>> iterables) {
		return new Iterable<T>() {
			public Iterator<T> iterator() {

				return Iterators.chain(map(new Iterable2IteratorMapper<T>(),
						iterables).iterator());
			}
		};
	}

	/**
	 * chain together two iterables.
	 * 
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static <T> Iterable<T> chain(Iterable<T> i1, Iterable<T> i2) {

		List<Iterable<T>> list = new ArrayList<Iterable<T>>();
		list.add(i1);
		list.add(i2);
		return chain(list);

	}

	/**
	 * equivalent to count(0)
	 * 
	 * @see Iterables#count(int)
	 * 
	 * @return
	 */
	public static Iterable<Integer> count() {
		return count(0);
	}

	/**
	 * Makes an iterable that returns consecutive integers starting with n.
	 * 
	 * @param n
	 * @return
	 */
	public static Iterable<Integer> count(final int n) {
		return new Iterable<Integer>() {

			public Iterator<Integer> iterator() {
				return Iterators.count(n);
			}
		};
	}

	/**
	 * Make an iterable that drops elements from the iterable as long as the
	 * predicate is true. Afterwards, returns every element. Note, the iterator
	 * does not produce any output until the predicate first becomes false, so
	 * it may have a lengthy start-up time.
	 * 
	 * @param <T>
	 * @param iterable
	 * @param predicate
	 * @return
	 */
	public static <T> Iterable<T> dropwhile(final Iterable<T> iterable,
			final Predicate<T> predicate) {
		return new Iterable<T>() {

			public Iterator<T> iterator() {
				return Iterators.dropwhile(iterable.iterator(), predicate);
			}

		};
	}

	/**
	 * Return an {@link Iterable} of Index object. The next() method of the
	 * iterable returned by enumerate() returns an Index containing a count
	 * (from start which defaults to 0) and the corresponding value obtained from
	 * iterating over iterable. enumerate() is useful for obtaining an indexed
	 * series: (0, seq[0]), (1, seq[1]), (2, seq[2]), .... For example:
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
	 * @param iterable
	 * @return
	 */
	public static <T> Iterable<Index<T>> enumerate(final Iterable<T> iterable) {
		return new Iterable<Index<T>>() {
			public Iterator<Index<T>> iterator() {
				return Iterators.enumerate(iterable.iterator());
			}
		};
	}

	
	/** Make an iterable that filters elements from iterable returning only those for which the predicate is True. 
	 *  
	 * 
	 * @param predicate
	 * @param iterable
	 * @return
	 */
	public static <T> Iterable<T> filter(final Predicate<T> predicate, final Iterable<T> iterable){
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return  Iterators.filter(predicate, iterable.iterator());
			}
		};
	}

	
	
	/** Make an iterable that filters elements from iterable returning only those for which the predicate is True. 
	 *  
	 * 
	 * @param predicate
	 * @param iterable
	 * @return
	 */
	public static <T> Iterable<T> filterfalse(final Predicate<T> predicate, final Iterable<T> iterable){
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return Iterators.filterfalse(predicate, iterable.iterator());
			}
		};
	}
	/**
	 * Turns any CharSequence into an Iterable of Characters
	 * 
	 * @param s
	 * @return
	 */
	public static Iterable<Character> iter(final CharSequence s) {
		return new Iterable<Character>() {
			
			public Iterator<Character> iterator() {
				return Iterators.iter(s);
			}
		};
	}
	
	/**
	 * Turns any byte[] array into an iterable
	 * 
	 * @param array
	 * @return
	 */
	public static Iterable<Byte> iter(final byte[] array) {
		return new Iterable<Byte>() {
			public Iterator<Byte> iterator() {
				return Iterators.iter(array);
			}
		};
			
	}

/**
	 * Turns any char[] array into an iterable
	 * 
	 * @param array
	 * @return
	 */
	public static Iterable<Character> iter(final char[] array) {
		return new Iterable<Character>() {
			public Iterator<Character> iterator() {
				return Iterators.iter(array);
			}
		};
			
	}

/**
	 * Turns any short[] array into an iterable
	 * 
	 * @param array
	 * @return
	 */
	public static Iterable<Short> iter(final short[] array) {
		return new Iterable<Short>() {
			public Iterator<Short> iterator() {
				return Iterators.iter(array);
			}
		};
			
	}

/**
	 * Turns any int[] array into an iterable
	 * 
	 * @param array
	 * @return
	 */
	public static Iterable<Integer> iter(final int[] array) {
		return new Iterable<Integer>() {
			public Iterator<Integer> iterator() {
				return Iterators.iter(array);
			}
		};
			
	}

/**
	 * Turns any long[] array into an iterable
	 * 
	 * @param array
	 * @return
	 */
	public static Iterable<Long> iter(final long[] array) {
		return new Iterable<Long>() {
			public Iterator<Long> iterator() {
				return Iterators.iter(array);
			}
		};
			
	}

/**
	 * Turns any float[] array into an iterable
	 * 
	 * @param array
	 * @return
	 */
	public static Iterable<Float> iter(final float[] array) {
		return new Iterable<Float>() {
			public Iterator<Float> iterator() {
				return Iterators.iter(array);
			}
		};
			
	}

/**
	 * Turns any double[] array into an iterable
	 * 
	 * @param array
	 * @return
	 */
	public static Iterable<Double> iter(final double[] array) {
		return new Iterable<Double>() {
			public Iterator<Double> iterator() {
				return Iterators.iter(array);
			}
		};
			
	}

/**
	 * Turns any boolean[] array into an iterable
	 * 
	 * @param array
	 * @return
	 */
	public static Iterable<Boolean> iter(final boolean[] array) {
		return new Iterable<Boolean>() {
			public Iterator<Boolean> iterator() {
				return Iterators.iter(array);
			}
		};
			
	}



	/**
	 * Turns any object array into an Iterable
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	public static <T> Iterable<T> iter(T[] t) {
		return Arrays.asList(t);
	}

	/**
	 * turns any {@link Iterable} into a list (that can be modified)
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	public static <T> List<T> list(Iterable<T> iterable) {
		List<T> list = new LinkedList<T>();
		for (T t : iterable)
			list.add(t);
		return list;
	}

	/**
	 * Apply {@link Mapper} to every item of <code>iterable</code> and return an
	 * iterable of the results.
	 * 
	 * @param mapper
	 * @param iterable
	 * @return
	 */
	public static <I, O> Iterable<O> map(final Mapper<I, O> mapper,
			final Iterable<I> iterable) {

		return new Iterable<O>() {

			public Iterator<O> iterator() {
				return Iterators.map(mapper, iterable.iterator());
			}

		};
	}

	/**
	 * equivalent to
	 * 
	 * <pre>
	 * range(0, end, 1)
	 * </pre>
	 * 
	 * @see Iterables#range(int, int, int)
	 * @param end
	 * @return
	 */
	public static Iterable<Integer> range(final int end) {
		return range(0, end, 1);
	}

	/**
	 * equivalent to
	 * 
	 * <pre>
	 * range(start, end, 1)
	 * </pre>
	 * 
	 * @see Iterables#range(int, int, int)
	 * @param start
	 * @param end
	 * @return
	 */
	public static Iterable<Integer> range(final int start, int end) {
		return range(start, end, 1);
	}

	/**
	 * This is a versatile function to create iterable containing arithmetic
	 * progressions. It is most often used in for loops. The full form returns
	 * an iterator over Integers [start, start + step, start + 2 * step, ...].
	 * <ul>
	 * <li>If step is positive, the last element is the largest start + i * step
	 * less than stop;</li>
	 * <li>if step is negative, the last element is the smallest start + i *
	 * step greater than stop.</li>
	 * <li>step must not be zero (or else InvalidParameterException is raised).</li>
	 * Example:
	 * 
	 * <pre>
	 * range(0, 30, 5);
	 * </pre>
	 * 
	 * gives
	 * 
	 * <pre>
	 * [0, 5, 10, 15, 20, 25]
	 * </pre>
	 * 
	 * <pre>
	 * range(0, 10, 3);
	 * </pre>
	 * 
	 * gives
	 * 
	 * <pre>
	 * [0, 3, 6, 9];
	 * </pre>
	 * 
	 * <pre>
	 * range(0, -10, -1);
	 * </pre>
	 * 
	 * gives
	 * 
	 * <pre>
	 * [0, -1, -2, -3, -4, -5, -6, -7, -8, -9];
	 * </pre>
	 * 
	 * @param start
	 * @param end
	 * @param step
	 * @return
	 */
	public static Iterable<Integer> range(final int start, final int end,
			final int step) {
		return new Iterable<Integer>() {
			public Iterator<Integer> iterator() {
				return Iterators.range(start, end, step);
			}
		};
	}

	/** equivalent to reduce(operator, iterable, null);
	 * 
	 * @param <T>
	 * @param operator
	 * @param iterable
	 * @return
	 */
	public static <T> T reduce( Operator<T> operator, Iterable<T> iterable){
		return reduce(operator, iterable, null);
	}

	/** Apply function of two arguments cumulatively to the items of iterable, from left to right, 
	 * so as to reduce the iterable to a single value. 
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
		The left argument, x, is the accumulated value and the right argument, y, is the update value from the iterable. 
		If the initializer is not null, it is placed before the items of the iterable in the calculation, and serves as a default when the iterable is empty. 
		If initializer is null and iterator contains only one item, the first item is returned.
	 * 
	 * @param operator
	 * @param iterable
	 * @param initializer
	 * @return
	 */
	public static <T> T reduce( Operator<T> operator, Iterable<T> iterable, T initializer){
		return Iterators.reduce(operator, iterable.iterator(), initializer);
	}
	
	
	/** equivalent to {@link Iterators#slice}(0, stop, 1);
	 * 
	 * @param <T>
	 * @param iterable
	 * @param start
	 * @param stop
	 * @return
	 */
	public static <T> Iterable<T> slice(final Iterable<T> iterable, final int stop) {
		return slice(iterable,0, stop, 1);
	}
	
	/** equivalent to {@link Iterators#slice}(start, stop, 1);
	 * 
	 * @param <T>
	 * @param iterator
	 * @param start
	 * @param stop
	 * @return
	 */
	public static <T> Iterable<T> slice(final Iterable<T> iterable, final int start, final int stop) {
		return slice(iterable, start, stop, 1);
	}
	
	/** Make an iterable that returns selected elements from the iterable. 
	 * If start is non-zero, then elements from the iterator are skipped until start is reached. 
	 * Afterward, elements are returned consecutively unless step is set higher than one which results in items being skipped. 
	 * It stops at the specified position. slice() does not support negative values for start, stop, or step. 
	 * 
	 * @param iterable
	 * @param start
	 * @param stop
	 * @param step
	 * @return
	 */
	public static <T> Iterable<T> slice(final Iterable<T> iterable,
			final int start, final int stop, final int step) {
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return Iterators.slice(iterable.iterator(), start, stop, step);
			}
		};
	}
	
		
	/** return a sorted Iterable in natural ascending order of T.
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	public static <T extends Comparable<? super T>> Iterable<T> sorted(final Iterable<T> iterable){
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return Iterators.sorted(iterable.iterator());
			}
		};
	}
	
	/** Return a new sorted iterable from the items in iterable.
	
	cmp specifies a custom Comparator of K.
	key specifies a {@link Mapper} that is used to extract a comparison key (K) from each iterable element. 
	reverse is a boolean value. If set to True, then the list elements are sorted as if each comparison were reversed.
	 * 
	 * @param iterable
	 * @param key
	 * @param cmp comparator of keys
	 * @param reverse
	 * @return
	 */
	public static <T, K> Iterable<T> sorted(final Iterable<T> iterable, final Mapper<T,K> key, final Comparator<? super K> cmp, final boolean reverse){

		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return Iterators.sorted(iterable.iterator(), cmp, key, reverse);
			}
		};
	
	}
	/**Return a new sorted iterable from the items in iterable.
	 * the comparator is used to sort the iterable.
	 * 
	 * @param <T>
	 * @param <K>
	 * @param iterable
	 * @param key
	 * @param reverse
	 * @return
	 */
	public static <T> Iterable<T> sorted(final Iterable<T> iterable, final Comparator<? super T> cmp){

		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return Iterators.sorted(iterable.iterator(), cmp);
			}
		};
	
	}
	
	
	/**Return a new sorted iterable from the items in iterable.
	 * the Key Mapper is used to extract a key from T, and that key natural order is used to sort the whole iterable.
	 * 
	 * @param <T>
	 * @param <K>
	 * @param iterable
	 * @param key
	 * @param reverse
	 * @return
	 */
	public static <T, K extends Comparable<? super K>> Iterable<T> sorted(final Iterable<T> iterable, final Mapper<T,K> key, final boolean reverse){
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return Iterators.sorted(iterable.iterator(), key, reverse);
			}
		};
	}
	
	
	
	/**
	 * Turns any Iterable into a "tuple", here an unmodifiable {@link List}
	 * 
	 * @param iterable
	 * @return a list
	 */
	public static <T> List<T> tuple(Iterable<T> iterable) {
		return Collections.unmodifiableList(list(iterable));
	}

	/**
	 * This function returns an {@link Iterable} of tuple (unmodifiable List) ,
	 * where the i-th couple contains the i-th element from each of the argument
	 * iterables. The returned iterable is truncated in length to the length of
	 * the shortest argument sequence.
	 * 
	 * Due to static typing of java, it is not possible to provide a generic
	 * length of iterable and at the same time provide mixed-type tuples,
	 * therefore every iterable must be of type <code>T</code>. To have
	 * two-mixed type use {@link Iterables#zip(Iterable, Iterable)}
	 * 
	 * 
	 * @param <T>
	 * @param iterables
	 * @return
	 */
	public static <T> Iterable<List<T>> zip(
			final Iterable<Iterable<T>> iterables) {
		return new Iterable<List<T>>() {

			public Iterator<List<T>> iterator() {
				return Iterators.zip(map(new Iterable2IteratorMapper<T>(),
						iterables).iterator());
			}

		};
	}

	/**
	 * This function returns an {@link Iterable} of Couples, where the i-th
	 * couple contains the i-th element from each of the argument iterables. The
	 * returned iterable is truncated in length to the length of the shortest
	 * argument sequence.
	 * 
	 * Due to static typing of java, it is not possible to provide a generic
	 * length of iterable and at the same time provide mixed-type tuples.
	 * 
	 * @param iterable1
	 * @param iterable2
	 * @return
	 */
	public static <T1, T2> Iterable<Couple<T1, T2>> zip(
			final Iterable<T1> iterable1, final Iterable<T2> iterable2) {

		return new Iterable<Couple<T1, T2>>() {

			public Iterator<Couple<T1, T2>> iterator() {
				return Iterators
						.zip(iterable1.iterator(), iterable2.iterator());
			}
		};
	}
	
	/** Return a reverse iterable. 
	 *  The whole iterable is stored, so be careful when used.
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	public <T> Iterable<T> reversed(final Iterable<T> iterable){
		return new Iterable<T>() {

			public Iterator<T> iterator() {
				return Iterators.reversed(iterable.iterator());
			}
		};
	}
	
	

}
