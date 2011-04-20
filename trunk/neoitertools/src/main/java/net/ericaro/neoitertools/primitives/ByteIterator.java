package net.ericaro.neoitertools.primitives;

/** A simple Iterator over a Byte array
 * 
 * @author eric
 *
 */
public class ByteIterator extends PrimitiveIterator<Byte> {

	
	
	
	private byte[] array;
	public ByteIterator(byte[] array) {
		this(array, 0, array.length);
	}
	
	public ByteIterator(byte[] array, int from, int to) {
		super(from, to);
		this.array = array;
	}

	@Override
	protected Byte get(int j) {
		return array[j];
	}

}
