package net.ericaro.neoitertools.generators.combinatorics;

import java.util.Arrays;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;


/** A BigNumber is simply a number represented by a fixed collections of digits of size <code>size</code>.
 * it requires two abstract method : 
 * {@link BigNumber#inc()} to move to the next one. hasNext() to tell if it is the bigger one.
 *  
 * 
 * @author eric
 *
 */
public abstract class BigNumber implements Generator<int[]> {

protected int[] base;
protected int size;
private boolean first = true;


public BigNumber(int size) {
	this.base = new int[size];
	this.size = size;
}


/** increment this big number by one
 * 
 */
protected abstract void inc() throws NoSuchElementException;


public String toString() {
	return Arrays.toString(base);
}

public int[] next() {
	if (!first ) 
		inc();
	first = false;
	return base;
}


}
