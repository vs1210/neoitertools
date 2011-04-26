package net.ericaro.neoitertools.generators.combinatorics;

import java.util.Arrays;

import net.ericaro.neoitertools.Generator;


/** A big number whose values are all the permutations.
 * 
 * @author eric
 *@see <a href="http://code.google.com/p/neoitertools/wiki/PermutationNumber">PermutationNumber's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class PermutationNumber implements Generator<int[]> {

	private FactorialNumber factorial;
	int[] indices;
	
	private int[] base;
	private int size;

	public PermutationNumber(int size) {
		indices = new int[size];
		factorial = new FactorialNumber(size);
		base = new int[size];
		this.size = size;
	}

	public int[] next() {
		int[] f = factorial.next();

		// build a list of available indices
		for (int j = 0; j < size; j++)
			indices[j] = j;
		
		// peek the 
		for(int i=0;i<size;i++)
			base[i] = popIth( f[i] );
		return base;
	}

	/** find, remove and return the ith index available
	 * 
	 * @param i
	 * @return
	 */
	private int popIth(int i) {
		// at first we assume that all indices are non empty, then i is at the same time the ith non empty index, and the actual index of this value.
		// but when we found empty indices, we have to increase it.
		for (int j = 0; j <= i; j++)
			if (indices[j] < 0) // this is not a valid index, it has been marked as removed, then
				i++; // move the end test
		indices[i] = -1; // mark as removed
		return i;
	}

	public String toString() {
		return Arrays.toString(base);
	}
}
