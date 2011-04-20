package net.ericaro.neoitertools.primitives;

/** A simple Iterator over a Integer array
 * 
 * @author eric
 *
 */
public class IntegerIterator extends PrimitiveIterator<Integer> {

	
	
	
	private int[] array;
	public IntegerIterator(int[] array) {
		this(array, 0, array.length);
	}
	
	public IntegerIterator(int[] array, int from, int to) {
		super(from, to);
		this.array = array;
	}

	@Override
	protected Integer get(int j) {
		return array[j];
	}

}
