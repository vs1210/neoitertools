package net.ericaro.neoitertools.generators;

import static net.ericaro.neoitertools.Itertools.identity;
import static net.ericaro.neoitertools.Itertools.in;
import static net.ericaro.neoitertools.Itertools.iter;
import static net.ericaro.neoitertools.Itertools.list;
import static net.ericaro.neoitertools.Itertools.map;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import net.ericaro.neoitertools.Generator;
import net.ericaro.neoitertools.Lambda;
import net.ericaro.neoitertools.Pair;

import org.junit.Test;

public class GroupByGeneratorTest {

	/*
	 * # [k for k, g in groupby('AAAABBBCCDAABBB')] --> A B C D A B
    # [(list(g)) for k, g in groupby('AAAABBBCCD')] --> AAAA BBB CC D
	 */
	
	@Test
	public void testGroupByGenerator() {
		List<Character> xlist = Arrays.asList('A','B','C','D','A','B');
		
		Lambda<Character, Character> identity = identity();
		GroupByGenerator<Character, Character> g = new GroupByGenerator<Character, Character>(iter("AAAABBBCCDAABBB"),  identity);
		
		Generator<Character> g2 = map(new Lambda<Pair<Character, Generator<Character>>, Character>(){
			public Character map(Pair<Character, Generator<Character>> arg) {
				return arg.f0;
			}}, g);
		
		List<Character> list = list(g2);
		assert xlist.equals(list);
		
	}
	@Test
	public void testGroupByGeneratorValue() {
		List<List<Character>> xlist = Arrays.asList(Arrays.asList('A','A','A','A'),Arrays.asList('B','B','B'),Arrays.asList('C','C'),Arrays.asList('D'));
		
		Lambda<Character, Character> identity = identity();
		GroupByGenerator<Character, Character> g = new GroupByGenerator<Character, Character>(iter("AAAABBBCCD"),  identity);
		
		List<List<Character>> list = new LinkedList<List<Character>>();
		
			try {
				while(true){
				Pair<Character, Generator<Character>> pair = g.next();
				list.add( 
						list(pair.f1) );
				}
			} catch (NoSuchElementException e) {
			}
			
		System.out.println(xlist);
		System.out.println(list);
		assert xlist.equals(list);
		
	}

}
