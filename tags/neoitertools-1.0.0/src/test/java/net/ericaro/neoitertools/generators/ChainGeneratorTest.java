package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.in;
import static net.ericaro.neoitertools.Itertools.list;
import static net.ericaro.neoitertools.Itertools.yield;

import java.util.Arrays;
import java.util.List;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Yield;

import org.junit.Test;

public class ChainGeneratorTest {

	
	public <T> Yield<Void, T> def(final Generator<Generator<T>> generators){
		return new Yield<Void, T>() {

			public void generate() {
				for( Generator<T> generator: in(generators) )
					for( T t : in(generator) )
						yield(t);
			}
			

		};
	}
	
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
