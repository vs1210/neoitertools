package net.ericaro.neoitertools.generators.primitives;

import java.util.NoSuchElementException;
import net.ericaro.neoitertools.Generator;
/** A simple Generator over a char array
 * 
 * @author eric
 *
 */
public class CharacterGenerator implements Generator<Character> {

	
	private char[] array;
	int index, end;
	public CharacterGenerator(char[] array) {
		this(array, 0, array.length);
	}
	
	public CharacterGenerator(char[] array, int from, int to) {
		this.array = array;
		index = from;
		end = to;
	}

	public Character next() throws NoSuchElementException{
		if (index >= end) throw new NoSuchElementException();
		return array[index++];
	}

}
