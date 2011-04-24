package net.ericaro.neoitertools;

import static net.ericaro.neoitertools.Itertools.*;

import java.util.Arrays;
import java.util.List;


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
		int i = 0;
		assert list.get(i) == -i;
		i++;
		assert list.get(i) == -i;
		i++;
		assert list.get(i) == -i;
		i++;
		assert list.get(i) == -i;
		i++;
		assert list.get(i) == -i;
		i++;
		assert list.get(i) == -i;
		i++;
		assert list.get(i) == -i;
		i++;
		assert list.get(i) == -i;
		i++;
		assert list.get(i) == -i;
		i++;
		assert list.get(i) == -i;
		i++;
		assert list.size() == 10;

	}

	@Test
	public void testChain() {
		String str = string(chain(iter("ABC"), iter("DEF")));
		assert "ABCDEF".equals(str);
	}


	@Test
	public void testDropWhile() {

		String str = string(dropwhile(
				new Lambda<Character,Boolean>() {
					public Boolean map(Character t) {
						return Character.isUpperCase(t);
					}

				},iter("ABCabc")));
		assert "abc".equals(str);

	}

	@Test
	public void testMap() {

		String str = string(map(new Lambda<Character, Character>() {
			public Character map(Character arg) {
				return Character.toUpperCase(arg);
			}
		}, iter("abcdeF")));

		assert "ABCDEF".equals(str);

	}

	@Test
	public void testZip() {
		List<Pair<Character, Character>> list = tuple(zip(iter("abcdef"),
				iter("123456789")));

		List<String> strings = list(map(
				new Lambda<Pair<Character, Character>, String>() {

					public String map(Pair<Character, Character> arg) {
						StringBuilder sb = new StringBuilder();
						sb.append(arg.f0).append(arg.f1);
						return sb.toString();
					}

				}, iter(list)));

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
		List<String> sorted = list(sorted(iter(list),
				new Lambda<String, Integer>() {
					public Integer map(String arg) {
						return arg.length();
					}
				}, false));
		int i = 0;
		assert sorted.get(i++).equals("a");
		assert sorted.get(i++).equals("d");
		assert sorted.get(i++).equals("aa");
		assert sorted.get(i++).equals("bbb");
		assert sorted.get(i++).equals("ccc");
		assert sorted.size() == 5;
	}

	

	@Test
	public void testReduce() {

		Operator<Integer> iadd = new Operator<Integer>() {
			public Integer operate(Integer t1, Integer t2) {
				return t1 + t2;
			}
		};
		Integer res = reduce(iadd, range(1, 6), 0);
		// x+y, [1, 2, 3, 4, 5], y: x+y, [1, 2, 3, 4, 5])
		int xres = ((((1 + 2) + 3) + 4) + 5);
		assert res == xres;

	}
}
