package fr.sertelon.fp.funchelper.function;

import org.junit.Assert;
import org.junit.Test;

public class F1Test {
	private static final F1<Integer, String> intToString = new F1<Integer, String>() {
		@Override
		public String apply(Integer input) {
			if(input != null)
				return input.toString();
			else
				return "";
		}
	};
	
	private static final F1<String, Integer> stringToInt = new F1<String, Integer>() {
		@Override
		public Integer apply(String input) {
			return Integer.valueOf(input);
		}
	};
	
	@Test
	public void then_nominal() {
		
		Integer result = intToString.then(stringToInt).apply(2);
		
		Assert.assertEquals(Integer.valueOf(2), result);
	}
}
