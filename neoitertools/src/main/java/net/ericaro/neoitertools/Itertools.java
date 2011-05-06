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
import net.ericaro.neoitertools.generators.EmptyGenerator;
import net.ericaro.neoitertools.generators.EnumerateGenerator;
import net.ericaro.neoitertools.generators.FilterGenerator;
import net.ericaro.neoitertools.generators.GeneratorIterator;
import net.ericaro.neoitertools.generators.GenericArrayGenerator;
import net.ericaro.neoitertools.generators.GroupByGenerator;
import net.ericaro.neoitertools.generators.IteratorGenerator;
import net.ericaro.neoitertools.generators.MapGenerator;
import net.ericaro.neoitertools.generators.RangeGenerator;
import net.ericaro.neoitertools.generators.RepeatGenerator;
import net.ericaro.neoitertools.generators.YieldGenerator;
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
 * This module implements a number of generator building blocks inspired by constructs from the Python programming languages. Each has been recast in a form
 * suitable for Java.
 * </p>
 * <p>
 * The module standardizes a core set of fast, memory efficient tools that are useful by themselves or in combination. Standardization helps avoid the
 * readability and reliability problems which arise when many different individuals create their own slightly varying implementations, each with their own
 * quirks and naming conventions.
 * </p>
 * <p>
 * The tools are designed to combine readily with one another. This makes it easy to construct more specialized tools succinctly and efficiently in pure Java.
 * </p>
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class Itertools {

	/**
	 * <p>
	 * Return True if all elements of the generator are evaluated to true with the Predicate (or if the generator is empty).
	 * </p>
	 * 
	 * @param <T>
	 *            type of generator elements
	 * @param generator
	 * @param predicate
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 * @return true|false
	 */
	public static <T> boolean all(Generator<T> generator, Lambda<? super T, Boolean> predicate) {
		try {
			while (predicate.map(generator.next())) {
			}
			return false;
		} catch (NoSuchElementException e) {
			return true;
		}
	}

	/**
	 * <p>
	 * Return True if any element of the generator is mapped to true. If the generator is empty, return False.
	 * </p>
	 * 
	 * @param generator
	 * @param predicate
	 * @return true|false
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> boolean any(Generator<T> generator, Lambda<? super T, Boolean> predicate) {
		try {
			while (!predicate.map(generator.next())) {
			}
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * <p>
	 * Make an generator that returns elements from the first iterable until it is exhausted, then proceeds to the next iterable, until all of the iterables are
	 * exhausted. Used for treating consecutive sequences as a single sequence.
	 * </p>
	 * <p>
	 * We do not use varargs due to an inner flaw in varargs that make them hard/impossible to combine with generics
	 * </p>
	 * 
	 * @param generators
	 *            a generator over generators
	 * @return a new generator that chain together all the above generators
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> chain(Generator<Generator<T>> generators) {
		return new ChainGenerator<T>(generators);
	}

	/**
	 * <p>
	 * Chain together two generators.
	 * </p>
	 * 
	 * @param generators
	 *            any generators
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> chain(Generator<T>... generators) {
		return chain(iter(generators));
	}

	/**
	 * <p>
	 * Return <code>r</code> length subsequences of elements from the input generator.
	 * </p>
	 * <p>
	 * Combinations are emitted in lexicographic sort order. So, if the input generator is sorted, the combination tuples will be produced in sorted order.
	 * </p>
	 * <p>
	 * Elements are treated as unique based on their position, not on their value. So if the input elements are unique, there will be no repeat values in each
	 * combination.
	 * </p>
	 * 
	 * @param generator
	 * @param r
	 * @return generator over combinations as list
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<List<T>> combinations(Generator<T> generator, int r) {
		List<T> list = list(generator);
		return Combinatorics.applied(list, Combinatorics.combinations(list.size(), r));
	}

	/**
	 * <p>
	 * Make a generator that returns all the consecutive integers starting with 0.
	 * </p>
	 * 
	 * @see Itertools#count(int)
	 * @return a count generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Integer> count() {
		return count(0);
	}

	/**
	 * <p>
	 * Make an generator that returns consecutive integers starting with n.
	 * </p>
	 * 
	 * @param n
	 *            the first integer returned by this generator
	 * @return a count generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/"> neoitertools site</a>
	 */
	public static Generator<Integer> count(final int n) {
		return new RangeGenerator(n, Integer.MAX_VALUE);
	}

	/**
	 * <p>
	 * Make an generator returning elements from the generator and saving a copy of each. When the generator is exhausted, return elements from the saved copy.
	 * Repeats indefinitely.
	 * </p>
	 * 
	 * @param generator
	 *            the source generator
	 * @return an generator returning elements from the generator over and over
	 *         again.
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> cycle(Generator<T> generator) {
		return new CycleGenerator<T>(generator);
	}

	/**
	 * <p>
	 * Make an generator that drops elements from the generator as long as the predicate is true. Afterwards, returns every element.
	 * </p>
	 * <p>
	 * Note, the generator does not produce any output until the predicate first becomes false, so it may have a lengthy start-up time.
	 * </p>
	 * 
	 * @param <T>
	 * @param generator
	 * @param predicate
	 * @return generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> dropwhile(Lambda<T, Boolean> predicate, Generator<T> generator) {
		return new DropWhileGenerator<T>(predicate, generator);
	}

	/**
	 * <p>
	 * Return an {@link Generator} of Index object.
	 * </p>
	 * <p>
	 * The next() method of the generator returned by enumerate() returns an Index containing a count (from start which defaults to 0) and the corresponding
	 * value obtained from iterating over generator.
	 * </p>
	 * <p>
	 * enumerate() is useful for obtaining an indexed series: (0, seq[0]), (1, seq[1]), (2, seq[2]), .... For example:
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
	 * </p>
	 * 
	 * @param generator
	 *            the source generator to enumerate
	 * @return a generator of index
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<Index<T>> enumerate(final Generator<T> generator) {
		return new EnumerateGenerator<T>(generator);
	}

	/**
	 * <p>
	 * Make an generator that filters elements from generator returning only those for which the predicate is True.
	 * </p>
	 * 
	 * @param predicate
	 * @param generator
	 * @return a filtered generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> filter(Lambda<T, Boolean> predicate, Generator<T> generator) {
		return new FilterGenerator<T>(predicate, generator);
	}

	/**
	 * <p>
	 * Make an generator that filters elements from generator returning only those for which the predicate is False.
	 * </p>
	 * 
	 * @param predicate
	 * @param generator
	 * @return a filtered generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> filterfalse(final Lambda<T, Boolean> predicate, final Generator<T> generator) {
		return new FilterGenerator<T>(predicate, generator, true);
	}

	/**
	 * <p>
	 * Make an generator that returns consecutive keys and groups from the source generator.
	 * </p>
	 * <p>
	 * The key is a function computing a key value for each element. Generally, the generator needs to already be sorted on the same key function.
	 * </p>
	 * <p>
	 * The operation of groupby() is similar to the uniq filter in Unix. It generates a break or new group every time the value of the key function changes
	 * (which is why it is usually necessary to have sorted the data using the same key function). That behavior differs from SQL’s GROUP BY which aggregates
	 * common elements regardless of their input order. The returned group is itself an generator that shares the underlying generator with groupby(). Because
	 * the source is shared, when the groupby() object is advanced, the previous group is no longer visible. So, if that data is needed later, it should be
	 * stored as a list.
	 * </p>
	 * 
	 * @param generator
	 *            the source generator
	 * @param key
	 *            the key mapper
	 * @return an generator that returns consecutive keys and groups from the
	 *         source generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T, K> Generator<Pair<K, Generator<T>>> groupby(Generator<T> generator, Lambda<T, K> key) {
		return new GroupByGenerator<K, T>(generator, key);
	}

	/** Return the identity Lambda function.
	 * 
	 * @param <T> any type
	 */
	public static <T> Lambda<T, T> identity() {
		return new Lambda<T, T>() {
			public T map(T arg) {
				return arg;
			}

		};
	}

	/**
	 * <p>
	 * Turn a {@link Generator} into an {@link Iterable}.
	 * </p>
	 * A much required function to use the java foreach statement.
	 * 
	 * <pre>
	 * for (int i : in(range(12)))
	 * 	System.out.println(i);
	 * </pre>
	 * 
	 * @param generator
	 *            the source generator
	 * @return a iterable fully equivalent to the generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Iterable<T> in(final Generator<T> generator) {
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return new GeneratorIterator<T>(generator);
			}

		};
	}

	/**
	 * Turns any boolean[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Boolean> iter(boolean[] array) {
		return new BooleanGenerator(array);
	}

	/**
	 * Turns any byte[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Byte> iter(byte[] array) {
		return new ByteGenerator(array);
	}

	/**
	 * Turns any char[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Character> iter(char[] array) {
		return new CharacterGenerator(array);
	}

	/**
	 * Turns a {@link CharSequence} into an {@link Generator}
	 * 
	 * @param seq
	 *            any {@link CharSequence}
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Character> iter(final CharSequence seq) {
		return new CharSequenceGenerator(seq);

	}

	/**
	 * Turns any double[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Double> iter(double[] array) {
		return new DoubleGenerator(array);
	}

	/**
	 * Turns any float[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Float> iter(float[] array) {
		return new FloatGenerator(array);
	}

	/**
	 * Turns any int[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Integer> iter(int[] array) {
		return new IntegerGenerator(array);
	}

	/**
	 * Turns any long[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Long> iter(long[] array) {
		return new LongGenerator(array);
	}

	/**
	 * Turns any short[] array into a generator
	 * 
	 * @param array
	 * @return a Generator over <code>array</code>
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Short> iter(short[] array) {
		return new ShortGenerator(array);
	}

	/**
	 * Turns any object array into an Generator
	 * 
	 * @param t
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> iter(final T[] t) {
		return new GenericArrayGenerator<T>(t);
	}

	/**
	 * Turn any {@link Iterable} into a {@link Generator}
	 * 
	 * @param iterable
	 * @return a generator over the iterable
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> iter(Iterable<T> iterable) {
		return iter(iterable.iterator());
	}

	/**
	 * Turn any {@link Iterator} into a {@link Generator}
	 * 
	 * @param iterator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> iter(Iterator<T> iterator) {
		return new IteratorGenerator<T>(iterator);
	}

	/**
	 * Turns a Yield generator into a standard Generator.
	 * 
	 * @param yield a Yield generator statement
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> iter(Yield<Void, T> yield) {
		return new YieldGenerator<T>(yield);
	}

	/**
	 * Creates a {@link List} from a {@link Generator}
	 * 
	 * @param generator
	 * @return a java modifiable List
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> List<T> list(Generator<T> generator) {
		List<T> list = new LinkedList<T>();
		try {
			while (true)
				list.add(generator.next());
		} catch (NoSuchElementException e) {
		}
		return list;
	}

	/**
	 * Creates a list from a generator, of size <code>max</code>
	 * 
	 * @param generator
	 * @param max
	 *            maximum size of the list
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> List<T> list(Generator<T> generator, int max) {
		List<T> list = new LinkedList<T>();
		try {
			int i = 0;
			while (i < max) {
				list.add(generator.next());
				i++;
			}
		} catch (NoSuchElementException e) {
		}
		return list;
	}

	/**
	 * Apply {@link Lambda} to every item of <code>sequence</code> and return a {@link Generator} of the results.
	 * 
	 * @param mapper
	 * @param sequence
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T, K> Generator<K> map(final Lambda<? super T, K> mapper, final Generator<T> sequence) {
		return new MapGenerator<T, K>(mapper, sequence);
	}

	/**
	 * <p>
	 * Return successive full length permutations of elements in the generator.
	 * </p>
	 * <p>
	 * Permutations are emitted in lexicographic sort order. So, if the input iterable is sorted, the permutation list will be produced in sorted order.
	 * </p>
	 * <p>
	 * Elements are treated as unique based on their position, not on their value. So if the input elements are unique, there will be no repeat values in each
	 * permutation.
	 * </p>
	 * 
	 * @param generator
	 * @return generator of permuted list
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<List<T>> permutations(Generator<T> generator) {
		List<T> list = list(generator);
		return Combinatorics.applied(list, Combinatorics.permutations(list.size()));
	}

	/**
	 * <p>
	 * Return successive r-length permutations of elements in the generator.
	 * </p>
	 * <p>
	 * Permutations are emitted in lexicographic sort order. So, if the input iterable is sorted, the permutation list will be produced in sorted order.
	 * </p>
	 * <p>
	 * Elements are treated as unique based on their position, not on their value. So if the input elements are unique, there will be no repeat values in each
	 * permutation.
	 * </p>
	 * 
	 * @param generator
	 * @param r
	 * @return a generator of r-sized permutations
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<List<T>> permutations(Generator<T> generator, int r) {
		List<T> list = list(generator);
		return Combinatorics.applied(list, Combinatorics.sublists(list.size(), r));
	}

	/**
	 * Cartesian product of input sequences.
	 * 
	 * @param generators
	 *            a generator of Generators
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<List<T>> product(Generator<Generator<T>> generators) {
		return product(generators, 1);
	}

	/**
	 * <p>
	 * Cartesian product of input sequences.
	 * </p>
	 * <p>
	 * repeat simulate the repetition of the input sequence.
	 * </p>
	 * 
	 * @param generators
	 *            a generator of Generators
	 * @param repeat
	 * @return a generator of cartesian product items
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<List<T>> product(Generator<Generator<T>> generators, int repeat) {
		if (repeat == 0)
			return new EmptyGenerator<List<T>>();
		List<List<T>> list = new LinkedList<List<T>>();
		// store all values (required for a product
		for (Generator<T> g : in(generators))
			list.add(list(g));

		// generate length array: length = len(lists)
		// [ len( lists[i%length]) for i in range( length ) ]
		// or for more fun
		// map( len, list*repeat )
		// note that I don't use neoitertools to implement it to avoid bug
		// propagations
		// and that I should also provide a bunch of Lambda object for every
		// function in itertools (and more (like len )

		int[] lengths = new int[list.size() * repeat];
		for (int i = 0; i < lengths.length; i++)
			lengths[i] = list.get(i % list.size()).size();

		return Combinatorics.selected(list, Combinatorics.product(lengths));
	}

	/**
	 * Equivalent to
	 * 
	 * <pre>
	 * range(0, end, 1)
	 * </pre>
	 * 
	 * @see Itertools#range(int, int, int)
	 * @param end
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
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
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Integer> range(int start, int end) {
		return range(start, end, 1);
	}

	/**
	 * <p>
	 * This is a versatile function to create generator containing arithmetic progressions.
	 * </p>
	 * <p>
	 * It is most often used in for loops. The full form returns an generator over Integers [start, start + step, start + 2 * step, ...].
	 * <ul>
	 * <li>If step is positive, the last element is the largest start + i * step less than stop;</li>
	 * <li>if step is negative, the last element is the smallest start + i * step greater than stop.</li>
	 * <li>step must not be zero (or else InvalidParameterException is raised).</li>
	 * </p>
	 * <p>
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
	 * </p>
	 * 
	 * @param start
	 * @param end
	 * @param step
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static Generator<Integer> range(final int start, final int end, final int step) throws InvalidParameterException {
		return new RangeGenerator(start, end, step);
	}

	/**
	 * Equivalent to reduce(operator, generator, null);
	 * 
	 * @param <T>
	 * @param operator
	 * @param generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> T reduce(Operator<T> operator, Generator<T> generator) {
		return reduce(operator, generator, null);
	}

	/**
	 * <p>
	 * Apply function of two arguments cumulatively to the items of generator, from left to right, so as to reduce the generator to a single value.
	 * </p>
	 * <p>
	 * For example,
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
	 * </p>
	 * The left argument, x, is the accumulated value and the right argument, y,
	 * is the update value from the generator. If the initializer is not null,
	 * it is placed before the items of the generator in the calculation, and
	 * serves as a default when the iterable is empty. If initializer is null
	 * and generator contains only one item, the first item is returned.
	 * 
	 * @param operator
	 * @param generator
	 * @param initializer
	 * @return all items reduced to a single one
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> T reduce(Operator<T> operator, Generator<T> generator, T initializer) {

		try {
			if (initializer == null)
				initializer = generator.next();
		} catch (NoSuchElementException e) {
			return initializer;
		}

		try {
			while (true)
				initializer = operator.operate(initializer, generator.next());
		} catch (NoSuchElementException e) {
		}
		return initializer;
	}

	/**
	 * <p>
	 * Make an generator that returns object over and over again.
	 * </p>
	 * <p>
	 * Runs indefinitely Used as argument to map() for invariant function parameters. Also used with zip() to create constant fields in a tuple record.
	 * </p>
	 * 
	 * @param object
	 * @return an generator that returns object over and over again.
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> repeat(T object) {
		return new RepeatGenerator<T>(object);
	}

	/**
	 * <p>
	 * Make an generator that returns object <code>times</code> times.
	 * </p>
	 * <p>
	 * Runs indefinitely unless the times argument is specified. Used as argument to imap() for invariant function parameters. Also used with izip() to create
	 * constant fields in a tuple record.
	 * </p>
	 * 
	 * @param object
	 * @param times
	 *            number of times the object is returned
	 * @return an generator that returns object over and over again.
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> repeat(T object, int times) {
		return new RepeatGenerator<T>(object, times);
	}

	/**
	 * <p>
	 * Return a reverse generator.
	 * </p>
	 * <p>
	 * The whole generator is stored, so be careful when used.
	 * </p>
	 * 
	 * @param generator
	 *            the source generator
	 * @return a generator that returns values in the reverse order
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> reversed(Generator<T> generator) {
		List<T> list = list(generator);
		Collections.reverse(list);
		return iter(list);
	}

	/**
	 * equivalent to {@link Itertools#slice}(0, stop, 1);
	 * 
	 * @param generator
	 *            source generator
	 * @param stop
	 *            last included index
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> slice(final Generator<T> generator, final int stop) {
		return new SliceGenerator<T>(generator, 0, stop, 1);
	}

	/**
	 * equivalent to {@link Itertools#slice}(start, stop, 1);
	 * 
	 * @param generator
	 *            source generator
	 * @param start
	 *            first included index
	 * @param stop
	 *            last included index
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> slice(final Generator<T> generator, final int start, final int stop) {
		return slice(generator, start, stop, 1);
	}

	/**
	 * <p>
	 * Make an generator that returns selected elements from the generator.
	 * </p>
	 * <p>
	 * If start is non-zero, then elements from the generator are skipped until start is reached. Afterward, elements are returned consecutively unless step is
	 * set higher than one which results in items being skipped. It stops at the specified position. slice() does not support negative values for start, stop,
	 * or step.
	 * </p>
	 * 
	 * @param generator
	 *            source generator
	 * @param start
	 *            first included index
	 * @param stop
	 *            last included index
	 * @param step
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> slice(final Generator<T> generator, final int start, final int stop, final int step) {
		return new SliceGenerator<T>(generator, start, stop, step);
	}

	/**
	 * Returns a sorted Generator in natural ascending order of T.
	 * 
	 * @param <T>
	 * @param generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T extends Comparable<? super T>> Generator<T> sorted(Generator<T> generator) {
		List<T> list = list(generator);
		Collections.sort(list);
		return iter(list);
	}

	/**
	 * <p>
	 * Return a new sorted generator from the items in generator.
	 * </p>
	 * <p>
	 * <code>cmp</code> specifies a custom Comparator of K. key specifies a {@link Lambda} that is used to extract a comparison key (K) from each generator
	 * element. reverse is a boolean value. If set to True, then the list elements are sorted as if each comparison were reversed.
	 * </p>
	 * 
	 * @param <T>
	 *            Type of items
	 * @param <K>
	 *            Type of the key used to filter
	 * @param generator
	 *            source generator
	 * @param key
	 *            key extraction function
	 * @param reverse
	 *            if true the the comparison is used in reverse order
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T, K> Generator<T> sorted(Generator<T> generator, final Comparator<? super K> cmp, final Lambda<? super T, K> key, final boolean reverse) {
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
	 * @param generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> sorted(Generator<T> generator, Comparator<? super T> cmp) {
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
	 *            Type of items
	 * @param <K>
	 *            Type of the key used to filter
	 * @param generator
	 *            source generator
	 * @param key
	 *            key extraction function
	 * @param reverse
	 *            if true the the comparison is used in reverse order
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T, K extends Comparable<? super K>> Generator<T> sorted(Generator<T> generator, final Lambda<T, K> key, final boolean reverse) {
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
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static String string(Generator<Character> chars) {
		return stringBuilder(chars).toString();
	}

	/**
	 * Turn any Generator of Character into a StringBuilder
	 * 
	 * @param chars
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static StringBuilder stringBuilder(Generator<Character> chars) {
		StringBuilder sb = new StringBuilder();
		try {
			while (true)
				sb.append(chars.next().charValue());
		} catch (NoSuchElementException e) {
		}
		return sb;
	}

	/**
	 * Make an generator that returns elements from the generator as long as the
	 * predicate is true.
	 * 
	 * @param generator
	 *            the source generator
	 * @param predicate
	 *            test function
	 * @return an generator that returns elements from the generator as long as
	 *         the predicate is true.
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<T> takewhile(final Generator<T> generator, final Lambda<? super T, Boolean> predicate) {
		return new TakeWhileGenerator<T>(predicate, generator);
	}

	/**
	 * Return n independent generators from a single iterable.
	 * 
	 * @param generator
	 *            the source generator
	 * @param n
	 *            number of independent generators
	 * @return an unmodifiable list of generators.
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> List<Generator<T>> tee(Generator<T> generator, int n) {
		// create the generator provider
		List<Generator<T>> list = new ArrayList<Generator<T>>(n);
		TeeGeneratorFactory<T> factory = new TeeGeneratorFactory<T>(generator);
		for (int i = 0; i < n; i++)
			list.add(factory.newInstance());
		return list;
	}

	/**
	 * Turns any Generator into a "tuple", here an unmodifiable {@link List}
	 * 
	 * @param generator
	 * @return a tuple extracted from a generator
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> List<T> tuple(Generator<T> generator) {
		return Collections.unmodifiableList(list(generator));
	}

	/**
	 * Causes the generate method to stop, and make the <code>t</code>value
	 * returned by the associated <code>next</code> method.
	 * 
	 * @param t
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/YieldStatement">Yield protocol</a>
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <R, T> R yield(T t) {
		YieldThread<R, T> thread = (YieldThread<R, T>) Thread.currentThread();
		return thread.yield(t);
	}

	/**
	 * <p>
	 * This function returns an {@link Generator} of tuple (unmodifiable List) , where the i-th couple contains the i-th element from each of the argument
	 * generators.
	 * </p>
	 * <p>
	 * The returned generator is truncated in length to the length of the shortest argument sequence.
	 * </p>
	 * <p>
	 * Due to static typing of java, it is not possible to provide a generic length of generator and at the same time provide mixed-type tuples, therefore every
	 * generator must be of type <code>T</code>. To have two-mixed type use {@link Itertools#zip(Generator, Generator)}
	 * </p>
	 * 
	 * @param generators
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T> Generator<List<T>> zip(Generator<Generator<T>> generators) {
		final List<Generator<T>> generatorList = list(generators);
		return new ZipGenerator<T>(generatorList);
	}

	/**
	 * <p>
	 * This function returns an {@link Generator} of {@link Pair}s, where the i-th pair contains the i-th element from each of the argument generators.
	 * </p>
	 * <p>
	 * The returned generator is truncated in length to the length of the shortest argument sequence. Due to static typing of java, it is not possible to
	 * provide a generic length of generator and at the same time provide mixed-type tuples.
	 * </p>
	 * 
	 * @param generator1
	 * @param generator2
	 * @see <a href="http://code.google.com/p/neoitertools/wiki/Itertools">Itertools's wiki page</a>
	 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
	 */
	public static <T1, T2> Generator<Pair<T1, T2>> zip(final Generator<T1> generator1, final Generator<T2> generator2) {

		return new ZipPairGenerator<T1, T2>(generator1, generator2);
	}

}
