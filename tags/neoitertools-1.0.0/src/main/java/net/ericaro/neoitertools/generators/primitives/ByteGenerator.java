package net.ericaro.neoitertools.generators.primitives;

import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
/** A simple Generator over a byte array
 * 
 * @author eric
 * @see <a href="http://code.google.com/p/neoitertools/wiki/ByteGenerator">ByteGenerator's wiki page</a>
* @see <a href="http://code.google.com/p/neoitertools/">neoitertools site</a>
 */
public class ByteGenerator implements Generator<Byte> {

	
	private byte[] array;
	int index, end;
	public ByteGenerator(byte[] array) {
		this(array, 0, array.length);
	}
	
	public ByteGenerator(byte[] array, int from, int to) {
		this.array = array;
		index = from;
		end = to;
	}

	public Byte next() throws NoSuchElementException{
		if (index >= end) throw new NoSuchElementException();
		return array[index++];
	}

}
