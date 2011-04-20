package net.ericaro.neoitertools.primitives;

/** A simple Iterator over a Character array
 * 
 * @author eric
 *
 */
public class CharacterIterator extends PrimitiveIterator<Character> {

	
	
	
	private char[] array;
	public CharacterIterator(char[] array) {
		this(array, 0, array.length);
	}
	
	public CharacterIterator(char[] array, int from, int to) {
		super(from, to);
		this.array = array;
	}

	@Override
	protected Character get(int j) {
		return array[j];
	}

}
