package net.ericaro.neoitertools.generators.combinatorics;

import java.util.NoSuchElementException;

public class VarBaseNumber extends BigNumber {

	// max size of each digit
	private int[] sizes;

	public VarBaseNumber(int... sizes) {
		super(sizes.length);
		this.sizes = sizes;
	}

	@Override
	protected void inc() throws NoSuchElementException {
		for(int i=size-1;i>=0;i--){
			// increment the base digit (always, test afterwards
			base[i]++;
			if ( base[i] < sizes[i] ) // the new digit is within the bounds, cool, then return
				return;
			else
				base[i]=0 ;// reset the digit (and by default it will increase the next one
		}
		// the for shall return if it succeeded in inc a digit
		throw new NoSuchElementException() ;

	}

}
