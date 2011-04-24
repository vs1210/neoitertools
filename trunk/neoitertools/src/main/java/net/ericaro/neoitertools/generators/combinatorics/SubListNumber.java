package net.ericaro.neoitertools.generators.combinatorics;

import java.util.NoSuchElementException;


public class SubListNumber extends BigNumber {

	private CombinationNumber combinationNumbers;
	private PermutationNumber permutationNumbers;
	private int[] combinationNumber;
	private int[] permutationNumber;

	public SubListNumber(int total, int size) {
		super(size);

		combinationNumbers = new CombinationNumber(total, size);
		permutationNumbers = new PermutationNumber(size);
		combinationNumber = combinationNumbers.next();
	}

	

	public void inc() {
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
	}
}
