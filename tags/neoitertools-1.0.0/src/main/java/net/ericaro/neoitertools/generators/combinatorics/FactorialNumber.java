package net.ericaro.neoitertools.generators.combinatorics;

import java.util.NoSuchElementException;


/**
 * A Factorial number is a big number where the ith digit must be in [0, i]. There are size! of them.
 * 
 * @see <a href="http://code.google.com/p/neoitertools/wiki/FactorialNumber">FactorialNumber's wiki page</a>
 * @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
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
		try {
			_inc(0);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new NoSuchElementException();
		}
	}

	

}
