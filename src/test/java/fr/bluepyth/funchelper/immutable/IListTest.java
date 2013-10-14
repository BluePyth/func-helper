package fr.bluepyth.funchelper.immutable;

import static fr.bluepyth.funchelper.immutable.IList.list;
import static fr.bluepyth.funchelper.immutable.IList.range;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import fr.bluepyth.funchelper.function.F1;
import fr.bluepyth.funchelper.function.F2;
import fr.bluepyth.funchelper.immutable.IList;

@RunWith(BlockJUnit4ClassRunner.class)
public class IListTest {
	
	private IList<Integer> list = list(1, 2, 3);
	private IList<Integer> mappedList = list(4, 5, 6);
	private IList<Integer> filteredList = list(1, 3);
	private IList<Integer> reversedList = list(3, 2, 1);

	@Test
	public void map_nominal() {
		
		IList<Integer> result = list.map(new F1<Integer, Integer>() {
			public Integer apply(Integer input) {
				return input + 3;
			}
		});
		
		assertEquals(mappedList, result);
	}
	
	@Test
	public void filter_nominal() {
		
		IList<Integer> result = list.filter(new F1<Integer, Boolean>() {
			public Boolean apply(Integer input) {
				return input % 2 == 1;
			}
		});
		
		assertEquals(filteredList, result);
		
	}
	
	@Test
	public void reverse_nominal() {
		IList<Integer> result = list.reverse();

		assertEquals(reversedList, result);
	}
	
	@Test
	public void foldLeft_nominal() {
		Integer result = list.foldLeft(0, new F2<Integer, Integer, Integer>() {
			public Integer apply(Integer i1, Integer i2) {
				return i1 + i2;
			}
		});
		
		assertEquals(Integer.valueOf(6), result);
	}
	
	@Test
	public void range_nominal() {
		IList<Integer> result = range(1, 4);
		
		assertEquals(list, result);
	}
	
	@Test
	public void mkString_nominal() {
		String result = list.mkString(";");
		
		assertEquals("1;2;3", result);
	}
}
