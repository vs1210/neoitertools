package net.ericaro.neoitertools.generators.primitives;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
/** A simple Generator over a int array
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/IntegerGenerator">IntegerGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class IntegerGenerator implements Generator<Integer> {

	
	private int[] array;
	int index, end;
	public IntegerGenerator(int[] array) {
		this(array, 0, array.length);
	}
	
	public IntegerGenerator(int[] array, int from, int to) {
		this.array = array;
		index = from;
		end = to;
	}

	public Integer next() throws NoSuchElementException{
		if (index >= end) throw new NoSuchElementException();
		return array[index++];
	}

}
