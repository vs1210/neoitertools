package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.list;

import java.util.Arrays;
import java.util.List;

import net.ericaro.neoitertools.Generator;

import org.junit.Test;

public class ChainGeneratorTest {

	
	/*# chain('ABC', 'DEF') --> A B C D E F
	 * 
	 */
	@Test
	public void testChainGenerator() {
		
		CharSequenceGenerator g1 = new CharSequenceGenerator("ABC");
		CharSequenceGenerator g2 = new CharSequenceGenerator("DEF");
		@SuppressWarnings("unchecked")
		ChainGenerator<Character> g = new ChainGenerator<Character>(new GenericArrayGenerator<Generator<Character>>(g1,g2));
		
		List<Character> xlist = Arrays.asList('A','B','C','D','E','F');
		List<Character> list = list(g);
		assert xlist.equals(list) : " chain ";
	}

}
