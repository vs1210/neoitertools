package net.ericaro.neoitertools.generators.combinatorics;


/** A big number whose values are all the permutations.
 * 
 * @author eric
 *
 */
public class PermutationNumber extends BigNumber {

	private FactorialNumber factorial;

	int[] indices;

	public PermutationNumber(int size) {
		super(size);
		indices = new int[size];
		factorial = new FactorialNumber(size);
	}

	public void inc() {
		int[] f = factorial.next();

		// build a list of available indices
		for (int j = 0; j < size; j++)
			indices[j] = j;
		
		// peek the 
		for(int i=0;i<f.length;i++){
			int k = findNonEmpty( f[i] );
			base[i++] = indices[k];
			indices[k] = -1; // mark as removed
		}
	}

	/** find, remove and return the ith index available
	 * 
	 * @param i
	 * @return
	 */
	private int findNonEmpty(int i) {
		// at first we assume that all indices are non empty, then i is at the same time the ith non empty index, and the actual index of this value.
		// but when we found empty indices, we have to increase it.
		for (int j = 0; j <= i; j++)
			if (indices[j] < 0) // this is not a valid index, it has been marked as removed, then
				i++; // move the end test
		return i;
	}

}
