package net.ericaro.neoitertools.primitives;

/** A simple Iterator over a Float array
 * 
 * @author eric
 *
 */
public class FloatIterator extends PrimitiveIterator<Float> {

	
	
	
	private float[] array;
	public FloatIterator(float[] array) {
		this(array, 0, array.length);
	}
	
	public FloatIterator(float[] array, int from, int to) {
		super(from, to);
		this.array = array;
	}

	@Override
	protected Float get(int j) {
		return array[j];
	}

}
