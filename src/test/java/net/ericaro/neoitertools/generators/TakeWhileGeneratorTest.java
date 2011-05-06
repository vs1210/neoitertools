package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.iter;
import static net.ericaro.neoitertools.Itertools.list;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import net.ericaro.neoitertools.Lambda;

import org.junit.Test;

public class TakeWhileGeneratorTest {

	//# takewhile(lambda x: x<5, [1,4,6,4,1]) --> 1 4
	@Test
	public void testTakeWhileGenerator() {
		
		List<Integer> xlist = Arrays.asList(1,4);
		List<Integer> list = list(new TakeWhileGenerator<Integer>(new Lambda<Integer,Boolean>() {

			public Boolean map(Integer x) {
				return x<5;
			}
		}, iter(Arrays.asList(1,4,6,4,1))));
		assert xlist.equals(list);
	}

}
