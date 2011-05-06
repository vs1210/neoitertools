package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.iter;
import static net.ericaro.neoitertools.Itertools.list;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ZipGeneratorTest {

	@Test
	public void testZipGenerator() {
		List<Integer> x = Arrays.asList(1,2,3);
		List<Integer> y = Arrays.asList(4,5,6);
		List<List<Integer>>  xlist = Arrays.asList(Arrays.asList(1,4) ,Arrays.asList(2,5) ,Arrays.asList(3,6)  );
		
		List<List<Integer>> list = list(new ZipGenerator<Integer>(Arrays.asList(iter(x), iter(y)) ));
		System.out.println(xlist);
		System.out.println(list);
		assert xlist.equals(list);
		
	}

}
