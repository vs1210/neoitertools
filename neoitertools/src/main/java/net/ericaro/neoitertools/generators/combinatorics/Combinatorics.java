package net.ericaro.neoitertools.generators.combinatorics;

import java.util.ArrayList;
import java.util.List;

import net.ericaro.neoitertools.Generator;

/** provides low level algorithms for permutations and combinations... Based on int[] to described indexes.
 * Warning, those algorithms are memory efficient, and therefore do not copy int[] around all the time. This means
 * that you shouldn't edit the int[] passed in the iterators.
 * 
 * The goal is to use those indices for higher level algorithm.
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/Combinatorics">Combinatorics's wiki page</a>
 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class Combinatorics {

	
	
	/**
	 * Calculate all permutations' indices
	 * @param n
	 * @return an Generator containing indices
	 */
	public static Generator<int[]> permutations(final int n) {
		return new PermutationNumber(n);
	}


	/** Calculate all product selectors' indices
	 * 
	 * @param lengths
	 * @return
	 */
	public static Generator<int[]> product(int[] lengths) {
		return new VarBaseNumber(lengths);
	}
	
	
	/**
	 * Calculate all subsets' indices
	 * @param m larger size
	 * @param n size of the subsets
	 * @return an Generator for the indices
	 */
	public static Generator<int[]> combinations(final int m, final int n) {
		return new CombinationNumber(m, n);
	}
	
	
	
	/**
	 * Calculate sublists' indexes
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
	
	/** Select on item per list, based on its index read in indices.
	 * lets define:
	 * <p>size : len( lists )
	 * <p>sizes[i] = len( lists[i] )  
	 * <p>then :
	 * <p>if len(indices) > size then indices[i] is associated with lists[i%size]
	 * <p>and 
	 * <p>indices[i] in [0 , sizes[i] &nbsp; [
	 * 
	 *  
	 * 
	 * @param lists
	 * @param indices 
	 * @return list of length size, with T values
	 */
	public static <T> List<T> select(List<List<T>> lists, int[] indices){
		int size = lists.size();
		List<T> items = new ArrayList<T>(indices.length);
		for (int i=0;i<indices.length;i++)
			items.add(lists.get(i%size).get(indices[i])  );
		return items;
	}
	
	
	
	
	/** Return an Generator over a list of selection ( @see {@link Combinatorics#select(List, int[])} . 
	 * @param elements
	 * @param indicesGenerator
	 * @return
	 */
	public static <T> Generator<List<T>> selected(final List<List<T>> lists, 	final Generator<int[]> indicesGenerator) {
		
		return new Generator<List<T>>() {
			public List<T> next() {
				return select(lists, indicesGenerator.next());
			}
		};
	}

	/** Return a Generator over a list of applied transformation ( @see {@link Combinatorics#apply(List, int[])} . 
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
