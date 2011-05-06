package net.ericaro.neoitertools.generators.combinatorics;

import java.util.Arrays;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;

/** a big number that return all the permutation of all the subsets, therefore the name: sublist
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/SubListNumber">SubListNumber's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class SubListNumber implements Generator<int[]> {

	private CombinationNumber combinationNumbers;
	private PermutationNumber permutationNumbers;
	private int[] combinationNumber;
	private int[] permutationNumber;

	private int[] base;
	private int size;


	public SubListNumber(int total, int size) {

		base = new int[size];
		this.size = size;
		
		combinationNumbers = new CombinationNumber(total, size);
		permutationNumbers = new PermutationNumber(size);
		combinationNumber = combinationNumbers.next();
	}

	

	public int[] next() {
		// all sublist is all the permutations of all the combinations.
		
		try {
			// use the next permutation has it has one
			permutationNumber = permutationNumbers.next();
		} catch (NoSuchElementException e) {
			// we have reach out of permutation, use the next combination, and reinit the permutations
			combinationNumber = combinationNumbers.next();
			permutationNumbers = new PermutationNumber(size);
			permutationNumber = permutationNumbers.next();
		}
		// apply the permutation to the current combination.
		for (int i = 0; i < size; i++) {
			base[i] = combinationNumber[permutationNumber[i]];
		}
		return base;
	}
	
	public String toString() {
		return Arrays.toString(base);
	}
}
