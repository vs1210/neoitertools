package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.list;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CharSequenceGeneratorTest {

	@Test
	public void testCharSequenceGenerator() {
		CharSequenceGenerator g = new CharSequenceGenerator("ABCD");
		List<Character> list = list(g);
		List<Character> xlist = Arrays.asList('A','B','C','D');
		assert xlist.equals(list): "char sequence generator failed" ; 

	}

}
