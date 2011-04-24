package net.ericaro.neoitertools.generators.combinatorics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.ericaro.neoitertools.Generator;

/** provides low level algorithms for permutations and combinations... Based on int[] to described indexes.
 * Warning, those algorithms are memory efficient, and therefore do not copy int[] around all the time. This means
 * that you shouldn't edit the int[] passed in the iterators.
 * 
 * The goal is to use those indices for higher level algorithm.
 * 
 * @author eric
 *
 */
public class Combinatorics {

	
	
	/**
	 * Calculate permutations and giving the indexes
	 * @param n
	 * @return an Generator containing indexes
	 */
	public static Generator<int[]> permutations(final int n) {
		return new PermutationNumber(n);
	}

	
	
	
	/**
	 * Calculate subsets (combinations) and giving the indexes
	 * @param m larger size
	 * @param n size of the subsets
	 * @return an Generator for the indexes
	 */
	public static Generator<int[]> combinations(final int m, final int n) {
		return new CombinationNumber(m, n);
	}
	
	
	
	/**
	 * Calculate sublists (arrangements) and giving the indexes
	 * @param m larger size
	 * @param n size of the sublists
	 * @return an Generator containing the indexes
	 */
	public static Generator<int[]> sublists(final int m, final int n) {
		return new SubListNumber(m, n);
	}
	
	/** Apply a transformation, described by the int[] to the given origin, into a new array.
	 * 
	 * @param <T>
	 * @param origin
	 * @param indices
	 * @return
	 */
	public static <T> List<T> apply(List<T> origin, int[] indices){
		List<T> items = new ArrayList<T>(indices.length);
		for (int i : indices)
			items.add(origin.get(i));
		return items;
	}
	
	/** Return an Iterator over a list of applied transformation ( @see {@link Combinatorics#apply(List, int[])} . 
	 * @param elements
	 * @param indicesGenerator
	 * @return
	 */
	public static <T> Generator<List<T>> applied(final List<T> elements,
			final Generator<int[]> indicesGenerator) {
		
		return new Generator<List<T>>() {
			public List<T> next() {
				return apply(elements, indicesGenerator.next());
			}
		};
	}

}
