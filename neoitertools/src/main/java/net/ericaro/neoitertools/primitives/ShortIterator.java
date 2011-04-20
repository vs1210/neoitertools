package net.ericaro.neoitertools.primitives;

/** A simple Iterator over a Short array
 * 
 * @author eric
 *
 */
public class ShortIterator extends PrimitiveIterator<Short> {

	
	
	
	private short[] array;
	public ShortIterator(short[] array) {
		this(array, 0, array.length);
	}
	
	public ShortIterator(short[] array, int from, int to) {
		super(from, to);
		this.array = array;
	}

	@Override
	protected Short get(int j) {
		return array[j];
	}

}
