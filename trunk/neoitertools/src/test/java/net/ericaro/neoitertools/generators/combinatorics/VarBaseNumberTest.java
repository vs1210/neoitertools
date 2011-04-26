package net.ericaro.neoitertools.generators.combinatorics;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class VarBaseNumberTest {

	@Test
	public void testVarBaseNumber() {

		// "varbase ( 2,3) --> 00 01 10 11 20 21"
		List<int[]> xlist = Arrays.asList(   
				new int[] { 0,0},
				new int[] { 0,1},
				new int[] { 1,0},
				new int[] { 1,1},
				new int[] { 2,0},
				new int[] { 2,1}
				)  ;
				

				
				for(int[] c : xlist)
					System.out.print(Arrays.toString(c)+" , ");
				System.out.println();
				
				VarBaseNumber fn = new VarBaseNumber(3, 2) ;
				
				int i =0;
				try {
					while(true){
						int[] next = fn.next();
						System.out.print(Arrays.toString(next)+" , ");
						assert Arrays.equals(xlist.get(i++), next) : "oops "+i+"th element falied"; 
					}
				} catch (NoSuchElementException e) {	}
				assert i == xlist.size();
	}

}
