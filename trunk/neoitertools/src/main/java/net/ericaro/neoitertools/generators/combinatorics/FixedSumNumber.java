package net.ericaro.neoitertools.generators.combinatorics;

import java.util.NoSuchElementException;


/** A Combinatorial Number is a BigNumber where the sum of all digits must be &lt;= total -size.
 * 
 * There are combination(total, size) of them (what a coincidence ;-) ). Digits can be seen as the distance between two
 * successive indices on a combination.
 * 
 */
public class FixedSumNumber extends BigNumber {

	private int total;
	private int sum;

	public FixedSumNumber(int total, int size) {
		super(size);
		if (total < 0 || size < 0) {
			throw new RuntimeException(
					"Can't create FixedSumNumber with a negative value.");
		}
		if (size > total) {
			throw new RuntimeException(
					"Can't create FixedSumNumber with subsize larger than size.");
		}
		this.total = total;
		this.sum = 0;
	}

	/**
	 * A little magic doesn't hurt ;-) this code does increment the combination.
	 * 
	 * 
	 * @param i
	 *            the digit to increment
	 * @return
	 */
	private void _inc(int i) {
		int mi = total - size; // max allowed index
		int j = size - i - 1; // reverse the digit order, so j is the new i
		if (sum < mi) { // there is room for increment
			base[j] += 1; // increment it
			sum += 1; // also increment the current sum
		} else { // well, all digits are full, then I'll clean this one:
			sum -= base[j]; // decrease the sum of the amount in the ith digit
							// (even if zero)
			base[j] = 0; // force it to be zero,
			_inc(i + 1); // try to increment the next one.
		}
	}

	protected void inc() {
		try {
			_inc(0);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new NoSuchElementException() ;
		}
	}
}
