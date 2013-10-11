package fr.bluepyth.java.funchelper;

import fr.bluepyth.java.funchelper.function.F1;

public final class Predef {
	public static final F1<String, Boolean> isStrEmpty = new F1<String, Boolean>() {
		public Boolean apply(String input) {
			return input.isEmpty();
		}
	};
	
	public static final F1<Object, String> objToString = new F1<Object, String>() {
		public String apply(Object input) {
			return input.toString();
		}
	};
}
