package net.ericaro.neoitertools;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.generators.ChainGenerator;
import net.ericaro.neoitertools.generators.CharSequenceGenerator;
import net.ericaro.neoitertools.generators.CycleGenerator;
import net.ericaro.neoitertools.generators.DropWhileGenerator;
import net.ericaro.neoitertools.generators.EnumerateGenerator;
import net.ericaro.neoitertools.generators.FilterGenerator;
import net.ericaro.neoitertools.generators.FullYieldGenerator;
import net.ericaro.neoitertools.generators.GeneratorIterator;
import net.ericaro.neoitertools.generators.GenericArrayGenerator;
import net.ericaro.neoitertools.generators.GroupByGenerator;
import net.ericaro.neoitertools.generators.IteratorGenerator;
import net.ericaro.neoitertools.generators.MapGenerator;
import net.ericaro.neoitertools.generators.RangeGenerator;
import net.ericaro.neoitertools.generators.RepeatGenerator;
import net.ericaro.neoitertools.generators.SimpleYieldGenerator;
import net.ericaro.neoitertools.generators.SliceGenerator;
import net.ericaro.neoitertools.generators.TakeWhileGenerator;
import net.ericaro.neoitertools.generators.TeeGeneratorFactory;
import net.ericaro.neoitertools.generators.YieldThread;
import net.ericaro.neoitertools.generators.ZipGenerator;
import net.ericaro.neoitertools.generators.ZipPairGenerator;
import net.ericaro.neoitertools.generators.combinatorics.Combinatorics;
import net.ericaro.neoitertools.generators.primitives.BooleanGenerator;
import net.ericaro.neoitertools.generators.primitives.ByteGenerator;
import net.ericaro.neoitertools.generators.primitives.CharacterGenerator;
import net.ericaro.neoitertools.generators.primitives.DoubleGenerator;
import net.ericaro.neoitertools.generators.primitives.FloatGenerator;
import net.ericaro.neoitertools.generators.primitives.IntegerGenerator;
import net.ericaro.neoitertools.generators.primitives.LongGenerator;
import net.ericaro.neoitertools.generators.primitives.ShortGenerator;

/**
 * <p>
 * This module implements a number of generator building blocks inspired by
 * constructs from the Python programming languages. Each has been recast in a
 * form suitable for Java.
 * </p>
 * 
 * The module standardizes a core set of fast, memory efficient tools that are
 * useful by themselves or in combination. Standardization helps avoid the
 * readability and reliability problems which arise when many different
 * individuals create their own slightly varying implementations, each with
 * their own quirks and naming conventions.
 * 
 * The tools are designed to combine readily with one another. This makes it
 * easy to construct more specialized tools succinctly and efficiently in pure
 * Java.
 * 
 * 
 * @author eric
 * 
 */
public class Itertools {

	/**
	 * Return True if all elements of the generator are evaluated to true with
	 * the Predicate (or if the generator is empty).
	 * 
	 * @param <T>
	 * @param generator
	 * @param predicate
	 * @return
	 */
	public static <T> boolean all(Generator<T> generator,
			Lambda<T, Boolean> predicate) {
		try {
			while (predicate.map(generator.next())) {
			}
			return false;
		} catch (NoSuchElementException e) {
			return true;
		}
	}

	/**
	 * Return True if any element of the generator is mapped to true. If the
	 * generator is empty, return False.
	 * 
	 * @param generator
	 * @param predicate
	 * @return
	 */
	public static <T> boolean any(Generator<T> generator,
			Lambda<T, Boolean> predicate) {
		try {
			while (!predicate.map(generator.next())) {
			}
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Make an generator that returns elements from the first iterable until it
	 * is exhausted, then proceeds to the next iterable, until all of the
	 * iterables are exhausted. Used for treating consecutive sequences as a
	 * single sequence.
	 * 
	 * We do not use varargs due to an inner flaw in varargs that make them
	 * hard/impossible to combine with generics
	 * 
	 * @param generators
	 *            an generator over generators
	 * @return
	 */
	public static <T> Generator<T> chain(Generator<Generator<T>> generators) {
		return new ChainGenerator<T>(generators);
	}

	/**
	 * chain together two generators.
	 * 
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static <T> Generator<T> chain(Generator<T> i1, Generator<T> i2) {
		List<Generator<T>> list = new LinkedList<Generator<T>>();
		list.add(i1);
		list.add(i2);
		return chain(iter(list) );
	}

	/**
	 * Return r length subsequences of elements from the input generator.
	 * 
	 * Combinations are emitted in lexicographic sort order. So, if the input
	 * generator is sorted, the combination tuples will be produced in sorted
	 * order.
	 * 
	 * Elements are treated as unique based on their position, not on their
	 * value. So if the input elements are unique, there will be no repeat
	 * values in each combination.
	 * 
	 * @param generator
	 * @param r
	 * @return generator over combinations as list
	 */
	public static <T> Generator<List<T>> combinations(Generator<T> generator,
			int r) {
		List<T> list = list(generator);
		return Combinatorics.applied(list,
				Combinatorics.combinations(list.size(), r));
	}

	/**
	 * equivalent to count(0)
	 * 
	 * @see Generators#count(int)
	 * 
	 * @return
	 */
	public static Generator<Integer> count() {
		return count(Integer.MAX_VALUE);
	}

	/**
	 * Make an generator that returns consecutive integers starting with n.
	 * 
	 * @param n
	 * @return
	 */
	public static Generator<Integer> count(final int n) {
		return new RangeGenerator(0, n );
	}

	/**
	 * Make an generator returning elements from the generator and saving a copy
	 * of each. When the generator is exhausted, return elements from the saved
	 * copy. Repeats indefinitely.
	 * 
	 * @param generator
	 * @return an generator returning elements from the generator over and over
	 *         again.
	 */
	public static <T> Generator<T> cycle(Generator<T> generator) {
		return new CycleGenerator<T>(generator);
	}

	/**
	 * Make an generator that drops elements from the generator as long as the
	 * predicate is true. Afterwards, returns every element. Note, the generator
	 * does not produce any output until the predicate first becomes false, so
	 * it may have a lengthy start-up time.
	 * 
	 * @param <T>
	 * @param generator
	 * @param predicate
	 * @return
	 */
	public static <T> Generator<T> dropwhile(Lambda<T, Boolean> predicate, Generator<T> generator) {
		return new DropWhileGenerator<T>(predicate, generator);
	}

	/**
	 * Return an {@link Generator} of Index object. The next() method of the
	 * generator returned by enumerate() returns an Index containing a count
	 * (from start which defaults to 0) and the corresponding value obtained
	 * from iterating over generator. enumerate() is useful for obtaining an
	 * indexed series: (0, seq[0]), (1, seq[1]), (2, seq[2]), .... For example:
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
	 * @param generator
	 * @return
	 */
	public static <T> Generator<Index<T>> enumerate(final Generator<T> generator) {

		return new EnumerateGenerator<T>(generator);
	}

	/**
	 * Make an generator that filters elements from generator returning only
	 * those for which the predicate is True.
	 * 
	 * 
	 * @param predicate
	 * @param generator
	 * @return
	 */
	public static <T> Generator<T> filter(Lambda<T, Boolean> predicate,Generator<T> generator) {
		return new FilterGenerator<T>(predicate, generator);
	}

	/**
	 * Make an generator that filters elements from generator returning only
	 * those for which the predicate is False.
	 * 
	 * 
	 * @param predicate
	 * @param generator
	 * @return
	 */
	public static <T> Generator<T> filterfalse(
			final Lambda<T, Boolean> predicate, final Generator<T> generator) {
		return new FilterGenerator<T>(predicate, generator, true);
	}

	/**
	 * Make an generator that returns consecutive keys and groups from the
	 * source generator. The key is a function computing a key value for each
	 * element. Generally, the generator needs to already be sorted on the same
	 * key function.
	 * 
	 * The operation of groupby() is similar to the uniq filter in Unix. It
	 * generates a break or new group every time the value of the key function
	 * changes (which is why it is usually necessary to have sorted the data
	 * using the same key function). That behavior differs from SQL’s GROUP BY
	 * which aggregates common elements regardless of their input order.
	 * 
	 * The returned group is itself an generator that shares the underlying
	 * generator with groupby(). Because the source is shared, when the
	 * groupby() object is advanced, the previous group is no longer visible.
	 * So, if that data is needed later, it should be stored as a list.
	 * 
	 * @param generator
	 *            the source generator
	 * @param key
	 *            the key mapper
	 * @return an generator that returns consecutive keys and groups from the
	 *         source generator
	 */
	public static <T, K> Generator<Pair<K, Generator<T>>> groupby(
			Generator<T> generator, Lambda<T, K> key) {
		return new GroupByGenerator<K, T>(generator, key);
	}

	public static <T> Lambda<T, T> identity() {
		return new Lambda<T, T>() {

			public T map(T arg) {
				return arg;
			}

		};
	}

	
	public static <T> Iterable<T> in(final Generator<T> seq) {
		return new Iterable<T>() {

			public Iterator<T> iterator() {
				return new GeneratorIterator<T>(seq);
			}

		};
	}

	/**
	 * Turns any boolean[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 */
	public static Generator<Boolean> iter(boolean[] array) {
		return new BooleanGenerator(array);
	}

	
	/**
	 * Turns any byte[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 */
	public static Generator<Byte> iter(byte[] array) {
		return new ByteGenerator(array);
	}

	/**
	 * Turns any char[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 */
	public static Generator<Character> iter(char[] array) {
		return new CharacterGenerator(array);
	}

	/**
	 * Turns a {@link CharSequence} into an {@link Generator}
	 * 
	 * @param seq
	 *            any {@link CharSequence}
	 * @return
	 */
	public static Generator<Character> iter(final CharSequence seq) {
		return new CharSequenceGenerator(seq);

	}

	/**
	 * Turns any double[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 */
	public static Generator<Double> iter(double[] array) {
		return new DoubleGenerator(array);
	}

	/**
	 * Turns any float[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 */
	public static Generator<Float> iter(float[] array) {
		return new FloatGenerator(array);
	}

	/**
	 * Turns any int[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 */
	public static Generator<Integer> iter(int[] array) {
		return new IntegerGenerator(array);
	}
	
	

	/**
	 * Turn any Iterable into a Generator
	 * 
	 * @param chars
	 * @return
	 */
	public static <T> Generator<T> iter(Iterable<T> iterable) {
		return iter(iterable.iterator());
	}

	/**
	 * Turn any Generator into a Generator
	 * 
	 * @param chars
	 * @return
	 */
	public static <T> Generator<T> iter(Iterator<T> iterator) {
		return new IteratorGenerator<T>(iterator);
	}

	/**
	 * Turns any long[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 */
	public static Generator<Long> iter(long[] array) {
		return new LongGenerator(array);
	}

	/**
	 * Turns any short[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 */
	public static Generator<Short> iter(short[] array) {
		return new ShortGenerator(array);
	}

	/**
	 * Turns any object array into an Generator
	 * 
	 * @param t
	 * @return
	 */
	public static <T> Generator<T> iter(final T[] t) {
		return new GenericArrayGenerator<T>(t);
	}
	
	
	/**
	 * Turns a Yield generator into a standard Generator.
	 * 
	 * @param t
	 * @return
	 */
	public static <T> Generator<T> iter(Yield<Void,T> yield) {
		return new SimpleYieldGenerator<T>(yield);
	}
	

	/**
	 * creates a list from a generator
	 * 
	 * @param seq
	 * @return
	 */
	public static <T> List<T> list(Generator<T> seq) {
		List<T> list = new LinkedList<T>();
		try {
			while (true)
				list.add(seq.next());
		} catch (NoSuchElementException e) {
		}
		return list;
	}

	/**
	 * creates a list from a generator, of size maximum
	 * 
	 * @param seq
	 * @param max
	 *            maximum size of the list
	 * @return
	 */
	public static <T> List<T> list(Generator<T> seq, int max) {
		List<T> list = new LinkedList<T>();
		try {
			int i = 0;
			while (i < max) {
				list.add(seq.next());
				i++;
			}
		} catch (NoSuchElementException e) {
		}
		return list;
	}

	/**
	 * Apply {@link Lambda} to every item of <code>sequence</code> and return a
	 * generaotr of the results.
	 * 
	 * @param mapper
	 * @param sequence
	 * @return
	 */
	public static <T, K> Generator<K> map(final Lambda<T, K> mapper,
			final Generator<T> sequence) {

		return new MapGenerator<T, K>(mapper, sequence);

	}

	/**
	 * Return successive full length permutations of elements in the generator.
	 * 
	 * 
	 * Permutations are emitted in lexicographic sort order. So, if the input
	 * iterable is sorted, the permutation list will be produced in sorted
	 * order.
	 * 
	 * Elements are treated as unique based on their position, not on their
	 * value. So if the input elements are unique, there will be no repeat
	 * values in each permutation.
	 * 
	 * @param generator
	 * @return generator of permuted list
	 */
	public static <T> Generator<List<T>> permutations(Generator<T> generator) {
		List<T> list = list(generator);
		return Combinatorics.applied(list,
				Combinatorics.permutations(list.size()));
	}

	/**
	 * Return successive r-length permutations of elements in the generator.
	 * 
	 * Permutations are emitted in lexicographic sort order. So, if the input
	 * iterable is sorted, the permutation list will be produced in sorted
	 * order.
	 * 
	 * Elements are treated as unique based on their position, not on their
	 * value. So if the input elements are unique, there will be no repeat
	 * values in each permutation.
	 * 
	 * @param generator
	 * @param r
	 * @return
	 */
	public static <T> Generator<List<T>> permutations(Generator<T> generator,
			int r) {
		List<T> list = list(generator);
		return Combinatorics.applied(list,
				Combinatorics.sublists(list.size(), r));
	}

	/**
	 * equivalent to
	 * 
	 * <pre>
	 * range(0, end, 1)
	 * </pre>
	 * 
	 * @see Itertools#range(int, int, int)
	 * @param end
	 * @return
	 */
	public static Generator<Integer> range(final int end) {
		return range(0, end, 1);
	}

	/**
	 * equivalent to
	 * 
	 * <pre>
	 * range(start, end, 1)
	 * </pre>
	 * 
	 * @see Itertools#range(int, int, int)
	 * @param start
	 * @param end
	 * @return
	 */
	public static Generator<Integer> range(int start, int end) {
		return range(start, end, 1);
	}

	/**
	 * This is a versatile function to create generator containing arithmetic
	 * progressions. It is most often used in for loops. The full form returns
	 * an generator over Integers [start, start + step, start + 2 * step, ...].
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
	public static Generator<Integer> range(final int start, final int end,
			final int step) throws InvalidParameterException {
		return new RangeGenerator(start, end, step);
	}

	/**
	 * equivalent to reduce(operator, generator, null);
	 * 
	 * @param <T>
	 * @param operator
	 * @param generator
	 * @return
	 */
	public static <T> T reduce(Operator<T> operator, Generator<T> generator) {
		return reduce(operator, generator, null);
	}

	/**
	 * Apply function of two arguments cumulatively to the items of generator,
	 * from left to right, so as to reduce the generator to a single value. For
	 * example,
	 * 
	 * <pre>
	 * Operator&lt;Integer&gt; iadd = new Operator&lt;Integer&gt;() {
	 * 	public Integer operate(Integer t1, Integer t2) {
	 * 		return t1 + t2;
	 * 	}
	 * };
	 * Integer res = reduce(iadd, range(1, 6), 0);
	 * </pre>
	 * 
	 * calculates
	 * 
	 * <pre>
	 * ((((1 + 2) + 3) + 4) + 5)
	 * </pre>
	 * 
	 * The left argument, x, is the accumulated value and the right argument, y,
	 * is the update value from the generator. If the initializer is not null,
	 * it is placed before the items of the generator in the calculation, and
	 * serves as a default when the iterable is empty. If initializer is null
	 * and generator contains only one item, the first item is returned.
	 * 
	 * @param operator
	 * @param generator
	 * @param initializer
	 * @return
	 */
	public static <T> T reduce(Operator<T> operator, Generator<T> generator,
			T initializer) {
		
		try {
			if (initializer == null)
				initializer = generator.next();
		} catch (NoSuchElementException e) {return initializer;	}
		
		try {
			while (true)
				initializer = operator.operate(initializer, generator.next());
		} catch (NoSuchElementException e) {}
		return initializer;
	}

	/**
	 * Make an generator that returns object over and over again. Runs
	 * indefinitely Used as argument to imap() for invariant function
	 * parameters. Also used with izip() to create constant fields in a tuple
	 * record.
	 * 
	 * @param object
	 * @return an generator that returns object over and over again.
	 */
	public static <T> Generator<T> repeat(T object) {
		return new RepeatGenerator<T>(object);
	}

	/**
	 * Make an generator that returns object over and over again. Runs
	 * indefinitely unless the times argument is specified. Used as argument to
	 * imap() for invariant function parameters. Also used with izip() to create
	 * constant fields in a tuple record.
	 * 
	 * @param object
	 * @param times
	 * @return an generator that returns object over and over again.
	 */
	public static <T> Generator<T> repeat(T object, int times) {
		return new RepeatGenerator<T>(object, times);
	}

	/**
	 * Return a reverse generator. The whole generator is stored, so be careful
	 * when used.
	 * 
	 * @param <T>
	 * @param generator
	 * @return
	 */
	public static <T> Generator<T> reversed(Generator<T> generator) {
		List<T> list = list(generator);
		Collections.reverse(list);
		return iter(list);
	}

	/**
	 * equivalent to {@link Generators#slice}(0, stop, 1);
	 * 
	 * @param <T>
	 * @param generator
	 * @param start
	 * @param stop
	 * @return
	 */
	public static <T> Generator<T> slice(final Generator<T> generator,
			final int stop) {
		return new SliceGenerator<T>(generator, 0, stop, 1);
	}

	// TODO product

	/**
	 * equivalent to {@link Generators#slice}(start, stop, 1);
	 * 
	 * @param <T>
	 * @param generator
	 * @param start
	 * @param stop
	 * @return
	 */
	public static <T> Generator<T> slice(final Generator<T> generator,
			final int start, final int stop) {
		return slice(generator, start, stop, 1);
	}

	/**
	 * Make an generator that returns selected elements from the generator. If
	 * start is non-zero, then elements from the generator are skipped until
	 * start is reached. Afterward, elements are returned consecutively unless
	 * step is set higher than one which results in items being skipped. It
	 * stops at the specified position. slice() does not support negative values
	 * for start, stop, or step.
	 * 
	 * @param generator
	 * @param start
	 * @param stop
	 * @param step
	 * @return
	 */
	public static <T> Generator<T> slice(final Generator<T> generator,
			final int start, final int stop, final int step) {
		return new SliceGenerator<T>(generator, start, stop, step);
	}

	/**
	 * return a sorted Generator in natural ascending order of T.
	 * 
	 * @param <T>
	 * @param generator
	 * @return
	 */
	public static <T extends Comparable<? super T>> Generator<T> sorted(
			Generator<T> generator) {
		List<T> list = list(generator);
		Collections.sort(list);
		return iter(list);
	}

	/**
	 * Return a new sorted generator from the items in generator.
	 * 
	 * cmp specifies a custom Comparator of K. key specifies a {@link Lambda}
	 * that is used to extract a comparison key (K) from each generator element.
	 * reverse is a boolean value. If set to True, then the list elements are
	 * sorted as if each comparison were reversed.
	 * 
	 * @param <T>
	 * @param <K>
	 * @param generator
	 * @param key
	 * @param reverse
	 * @return
	 */
	public static <T, K> Generator<T> sorted(Generator<T> generator,
			final Comparator<? super K> cmp, final Lambda<T, K> key,
			final boolean reverse) {
		// maps T into (K,T) to perform the sort
		Lambda<T, Pair<K, T>> valueToKeyValue = new Lambda<T, Pair<K, T>>() {
			public Pair<K, T> map(T arg) {
				return new Pair<K, T>(key.map(arg), arg);
			}
		};

		// Adversely maps (K,T) into T
		Lambda<Pair<K, T>, T> keyValueToValue = new Lambda<Pair<K, T>, T>() {
			public T map(Pair<K, T> arg) {
				return arg.f1;
			}
		};

		// use comparator of K to compare (K,T)
		Comparator<Pair<K, T>> keyComparator = new Comparator<Pair<K, T>>() {

			public int compare(Pair<K, T> o1, Pair<K, T> o2) {
				return (reverse ? -1 : 1) * cmp.compare(o1.f0, o2.f0);
			}

		};

		List<Pair<K, T>> list = list(map(valueToKeyValue, generator));
		Collections.sort(list, keyComparator);

		return map(keyValueToValue, iter(list));
	}

	/**
	 * Return a new sorted generator from the items in generator. the comparator
	 * is used to sort the generator.
	 * 
	 * @param <T>
	 * @param <K>
	 * @param generator
	 * @param key
	 * @param reverse
	 * @return
	 */
	public static <T> Generator<T> sorted(Generator<T> generator,
			Comparator<? super T> cmp) {
		List<T> list = list(generator);
		Collections.sort(list, cmp);
		return iter(list);
	}

	/**
	 * Return a new sorted generator from the items in generator. the Key Lambda
	 * is used to extract a key from T, and that key natural order is used to
	 * sort the whole generator.
	 * 
	 * @param <T>
	 * @param <K>
	 * @param generator
	 * @param key
	 * @param reverse
	 * @return
	 */
	public static <T, K extends Comparable<? super K>> Generator<T> sorted(
			Generator<T> generator, final Lambda<T, K> key,
			final boolean reverse) {
		return sorted(generator, new Comparator<K>() {

			public int compare(K o1, K o2) {
				return o1.compareTo(o2);
			}
		}, key, reverse);
	}

	/**
	 * Turn any Generator of Character into a String
	 * 
	 * @param chars
	 * @return
	 */
	public static String string(Generator<Character> chars) {
		return stringBuilder(chars).toString();
	}

	/**
	 * Turn any Generator of Character into a StringBuilder
	 * 
	 * @param chars
	 * @return
	 */
	public static StringBuilder stringBuilder(Generator<Character> chars) {
		StringBuilder sb = new StringBuilder();
		try {
			while (true)
				sb.append(chars.next().charValue());
		} catch (NoSuchElementException e) {}
		return sb;
	}

	/**
	 * Make an generator that returns elements from the generator as long as the
	 * predicate is true.
	 * 
	 * @param generator
	 * @param predicate
	 * @return an generator that returns elements from the generator as long as
	 *         the predicate is true.
	 */
	public static <T> Generator<T> takewhile(final Generator<T> generator,
			final Lambda<T, Boolean> predicate) {
		return new TakeWhileGenerator<T>(predicate,generator);
	}

	/**
	 * Return n independent generators from a single iterable.
	 * 
	 * @param generator
	 *            the source generator
	 * @param n
	 *            number of independent generators
	 * @return an unmodifiable list of generators.
	 */
	public static <T> List<Generator<T>> tee(Generator<T> generator, int n) {
		// create the generator provider
		List<Generator<T>> list = new ArrayList<Generator<T>>(n);
		TeeGeneratorFactory<T> factory = new TeeGeneratorFactory<T>(generator);
		for(int i=0;i<n;i++)
			list.add(factory.newInstance());
		return list;
	}

	/**
	 * Turns any Generator into a "tuple", here an unmodifiable {@link List}
	 * 
	 * @param generator
	 * @return a
	 */
	public static <T> List<T> tuple(Generator<T> generator) {
		return Collections.unmodifiableList(list(generator));
	}
	
	
	/** causes the generate method to stop, and make the <code>t</code>value returned by the associated <code>next</code> method.
	 *
	 * 
	 * 
	 * 
	 * @param t
	 */
	public static <R,T> R yield(T t){
		YieldThread<R, T> thread =  (YieldThread<R, T>) Thread.currentThread();
		return thread.yield(t);
	}

	/**
	 * This function returns an {@link Generator} of tuple (unmodifiable List) ,
	 * where the i-th couple contains the i-th element from each of the argument
	 * generators. The returned generator is truncated in length to the length
	 * of the shortest argument sequence.
	 * 
	 * Due to static typing of java, it is not possible to provide a generic
	 * length of generator and at the same time provide mixed-type tuples,
	 * therefore every generator must be of type <code>T</code>. To have
	 * two-mixed type use {@link Generators#zip(Generator, Generator)}
	 * 
	 * 
	 * @param <T>
	 * @param generators
	 * @return
	 */
	public static <T> Generator<List<T>> zip(Generator<Generator<T>> generators) {
		final List<Generator<T>> generatorList = list(generators);
		return new ZipGenerator<T>(generatorList);
	}

	/**
	 * This function returns an {@link Generator} of Couples, where the i-th
	 * couple contains the i-th element from each of the argument generators.
	 * The returned generator is truncated in length to the length of the
	 * shortest argument sequence.
	 * 
	 * Due to static typing of java, it is not possible to provide a generic
	 * length of generator and at the same time provide mixed-type tuples.
	 * 
	 * @param generator1
	 * @param generator2
	 * @return
	 */
	public static <T1, T2> Generator<Pair<T1, T2>> zip(
			final Generator<T1> generator1, final Generator<T2> generator2) {

		return new ZipPairGenerator<T1, T2>(generator1, generator2);
	}

	
	

	

}
