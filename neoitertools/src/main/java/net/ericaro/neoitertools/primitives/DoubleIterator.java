package net.ericaro.neoitertools.primitives;

/** A simple Iterator over a Double array
 * 
 * @author eric
 *
 */
public class DoubleIterator extends PrimitiveIterator<Double> {

	
	
	
	private double[] array;
	public DoubleIterator(double[] array) {
		this(array, 0, array.length);
	}
	
	public DoubleIterator(double[] array, int from, int to) {
		super(from, to);
		this.array = array;
	}

	@Override
	protected Double get(int j) {
		return array[j];
	}

}
