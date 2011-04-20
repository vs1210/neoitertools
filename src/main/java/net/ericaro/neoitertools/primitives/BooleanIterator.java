package net.ericaro.neoitertools.primitives;

/** A simple Iterator over a Boolean array
 * 
 * @author eric
 *
 */
public class BooleanIterator extends PrimitiveIterator<Boolean> {

	
	
	
	private boolean[] array;
	public BooleanIterator(boolean[] array) {
		this(array, 0, array.length);
	}
	
	public BooleanIterator(boolean[] array, int from, int to) {
		super(from, to);
		this.array = array;
	}

	@Override
	protected Boolean get(int j) {
		return array[j];
	}

}
