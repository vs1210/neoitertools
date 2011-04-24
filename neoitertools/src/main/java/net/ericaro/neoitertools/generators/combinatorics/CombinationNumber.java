package net.ericaro.neoitertools.generators.combinatorics;

import java.util.NoSuchElementException;

/** A big number that follows combinations.
 * 
 * @author eric
 *
 */
public class CombinationNumber extends BigNumber {
	FixedSumNumber fixedSum;

	public CombinationNumber(int total, int size) {
		super(size);
		fixedSum = new FixedSumNumber(total, size);
	}

	protected void inc() {

		try {
			int[] f = fixedSum.next();
			int k = -1;
			for(int i=0;i<size;i++){
				k += f[i] + 1;
				base[i] = k;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new NoSuchElementException() ;
		}
	}

}
