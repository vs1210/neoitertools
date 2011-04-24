package net.ericaro.neoitertools.generators.primitives;

import java.util.NoSuchElementException;
import net.ericaro.neoitertools.Generator;
/** A simple Generator over a float array
 * 
 * @author eric
 *
 */
public class FloatGenerator implements Generator<Float> {

	
	private float[] array;
	int index, end;
	public FloatGenerator(float[] array) {
		this(array, 0, array.length);
	}
	
	public FloatGenerator(float[] array, int from, int to) {
		this.array = array;
		index = from;
		end = to;
	}

	public Float next() throws NoSuchElementException{
		if (index >= end) throw new NoSuchElementException();
		return array[index++];
	}

}
