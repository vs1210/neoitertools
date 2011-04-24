package net.ericaro.neoitertools.generators.primitives;

import java.util.NoSuchElementException;
import net.ericaro.neoitertools.Generator;
/** A simple Generator over a long array
 * 
 * @author eric
 *
 */
public class LongGenerator implements Generator<Long> {

	
	private long[] array;
	int index, end;
	public LongGenerator(long[] array) {
		this(array, 0, array.length);
	}
	
	public LongGenerator(long[] array, int from, int to) {
		this.array = array;
		index = from;
		end = to;
	}

	public Long next() throws NoSuchElementException{
		if (index >= end) throw new NoSuchElementException();
		return array[index++];
	}

}
