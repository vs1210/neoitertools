package net.ericaro.neoitertools;

import static org.junit.Assert.*;
import static net.ericaro.neoitertools.Iterators.*;

import java.util.Arrays;
import java.util.List;

import net.ericaro.neoitertools.Couple;
import net.ericaro.neoitertools.Mapper;
import net.ericaro.neoitertools.Operator;
import net.ericaro.neoitertools.Predicate;

import org.junit.Test;

public class IteratorsTest {

	@Test
	public void testRange() {
		List<Integer> list = list(range(2, 10, 3));
		assert list.get(0) == 2;
		assert list.get(1) == 5;
		assert list.get(2) == 8;
		assert list.size() == 3;
	}
	@Test
	public void testRangeNeg() {
		List<Integer> list = list(range(0, -10, -1));
		int i=0;
		assert list.get(i) == -i; i++;
		assert list.get(i) == -i; i++;
		assert list.get(i) == -i; i++;
		assert list.get(i) == -i; i++;
		assert list.get(i) == -i; i++;
		assert list.get(i) == -i; i++;
		assert list.get(i) == -i; i++;
		assert list.get(i) == -i; i++;
		assert list.get(i) == -i; i++;
		assert list.get(i) == -i; i++;
		assert list.size() == 10;
		
	}

	@Test
	public void testChain() {
		String str = asString(chain(iter("ABC"), iter("DEF")));
		assert "ABCDEF".equals(str);
	}

	@Test
	public void testSingleton() {
		List<String> list = list(new SingletonIterator<String>("S"));
		assert list.size() == 1;
		assert "S".equals(list.get(0));
	}

	@Test
	public void testDropWhile() {

		String str = asString(dropwhile(iter("ABCabc"),
				new Predicate<Character>() {
					public Boolean map(Character t) {
						return Character.isUpperCase(t);
					}

				}));
		assert "abc".equals(str);

	}

	@Test
	public void testMap() {

		String str = asString(map(new Mapper<Character, Character>() {
			public Character map(Character arg) {
				return Character.toUpperCase(arg);
			}
		}, iter("abcdeF")));

		assert "ABCDEF".equals(str);

	}

	@Test
	public void testZip() {
		List<Couple<Character, Character>> list = tuple(zip(iter("abcdef"),
				iter("123456789")));

		List<String> strings = list(map(
				new Mapper<Couple<Character, Character>, String>() {

					public String map(Couple<Character, Character> arg) {
						StringBuilder sb = new StringBuilder();
						sb.append(arg.f0).append(arg.f1);
						return sb.toString();
					}

				}, list.iterator()));

		int i = 0;
		assert strings.get(i++).equals("a1");
		assert strings.get(i++).equals("b2");
		assert strings.get(i++).equals("c3");
		assert strings.get(i++).equals("d4");
		assert strings.get(i++).equals("e5");
		assert strings.get(i++).equals("f6");
		assert strings.size() == 6;

	}

	@Test
	public void testSorted() {
		List<String> list = Arrays.asList("a", "aa", "bbb", "ccc", "d");
		List<String> sorted = list(sorted(list.iterator(),  new Mapper<String, Integer>() {
			public Integer map(String arg) { return arg.length(); }	}, false));
		int i = 0;
		assert sorted.get(i++).equals("a");
		assert sorted.get(i++).equals("d");
		assert sorted.get(i++).equals("aa");
		assert sorted.get(i++).equals("bbb");
		assert sorted.get(i++).equals("ccc");
		assert sorted.size() == 5;
	}

	@Test
	public void testFilter() throws Exception {

		List<Integer> list = list(filter(new Predicate<Integer>() {
			public Boolean map(Integer t) {
				return t % 2 == 0;
			}

		}, range(10)));
		int i = 0;
		assert list.get(i++) == 0;
		assert list.get(i++) == 2;
		assert list.get(i++) == 4;
		assert list.get(i++) == 6;
		assert list.get(i++) == 8;
		assert list.size() == 5;

	}
	
	@Test public void testReduce(){
		
		
		Operator<Integer> iadd = new Operator<Integer>() {
			public Integer operate(Integer t1, Integer t2) {
				return t1+t2;
			}
		};
		Integer res = reduce(iadd , range(1,6), 0);
		//x+y, [1, 2, 3, 4, 5], y: x+y, [1, 2, 3, 4, 5]) 
		int xres = ((((1+2)+3)+4)+5);
		assert res==xres;
		
	}
}
