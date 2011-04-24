package net.ericaro.neoitertools.generators.primitives;

import java.util.NoSuchElementException;
import net.ericaro.neoitertools.Generator;
/** A simple Generator over a boolean array
 * 
 * @author eric
 *
 */
public class BooleanGenerator implements Generator<Boolean> {

	
	private boolean[] array;
	int index, end;
	public BooleanGenerator(boolean[] array) {
		this(array, 0, array.length);
	}
	
	public BooleanGenerator(boolean[] array, int from, int to) {
		this.array = array;
		index = from;
		end = to;
	}

	public Boolean next() throws NoSuchElementException{
		if (index >= end) throw new NoSuchElementException();
		return array[index++];
	}

}
