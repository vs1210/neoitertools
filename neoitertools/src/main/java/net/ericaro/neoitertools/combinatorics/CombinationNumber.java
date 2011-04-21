package net.ericaro.neoitertools.combinatorics;

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

	public boolean hasNext() {
		return fixedSum.hasNext();
	}

	protected void inc() {

		int[] f = fixedSum.next();
		int k = -1;
		for(int i=0;i<size;i++){
			k += f[i] + 1;
			base[i] = k;
		}
	}

}
