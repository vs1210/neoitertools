package net.ericaro.neoitertools.primitives;

/** A simple Iterator over a Long array
 * 
 * @author eric
 *
 */
public class LongIterator extends PrimitiveIterator<Long> {

	
	
	
	private long[] array;
	public LongIterator(long[] array) {
		this(array, 0, array.length);
	}
	
	public LongIterator(long[] array, int from, int to) {
		super(from, to);
		this.array = array;
	}

	@Override
	protected Long get(int j) {
		return array[j];
	}

}
