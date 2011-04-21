package net.ericaro.neoitertools.combinatorics;


/**
 * A Factorial number is a big number where the ith digit must be in [0, i]. There are size! of them.
 */
public class FactorialNumber extends BigNumber {


	public FactorialNumber(int size) {
		super(size);
	}

	private void _inc(int j) {
		int i = size - j - 1;
		if (base[i] < j)
			base[i] += 1;
		else {
			base[i] = 0;
			_inc(j + 1);
		}
	}
	
	@Override
	protected void inc() {
		_inc(0);
	}

	public boolean hasNext() {
		for (int j = 0; j < size; j++) {
			int i = size - j - 1;
			if (base[i] < j)
				return true;
		}
		return false;
	}

}
