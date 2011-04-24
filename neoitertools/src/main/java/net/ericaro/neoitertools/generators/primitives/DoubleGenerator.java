package net.ericaro.neoitertools.generators.primitives;

import java.util.NoSuchElementException;
import net.ericaro.neoitertools.Generator;
/** A simple Generator over a double array
 * 
 * @author eric
 *
 */
public class DoubleGenerator implements Generator<Double> {

	
	private double[] array;
	int index, end;
	public DoubleGenerator(double[] array) {
		this(array, 0, array.length);
	}
	
	public DoubleGenerator(double[] array, int from, int to) {
		this.array = array;
		index = from;
		end = to;
	}

	public Double next() throws NoSuchElementException{
		if (index >= end) throw new NoSuchElementException();
		return array[index++];
	}

}
