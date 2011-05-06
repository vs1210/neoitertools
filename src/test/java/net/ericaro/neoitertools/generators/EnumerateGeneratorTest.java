package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.list;

import java.util.Arrays;
import java.util.List;

import net.ericaro.neoitertools.Index;

import org.junit.Test;

public class EnumerateGeneratorTest {

	@Test
	public void testSimpleRange() {

		@SuppressWarnings("unchecked")
		List<Index<Character>> xlist = Arrays.asList(new Index<Character>(1,
				'a'), new Index<Character>(2, 'b'),
				new Index<Character>(3, 'c'));

		EnumerateGenerator<Character> g = new EnumerateGenerator<Character>(
				new CharSequenceGenerator("abc"), 1);
		List<Index<Character>> list = list(g);
		System.out.println(xlist);
		System.out.println(list);
		assert xlist.equals(list) : "enumerate is a failure";
	}

}
