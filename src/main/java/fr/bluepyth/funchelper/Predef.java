package fr.bluepyth.funchelper;

import static fr.bluepyth.funchelper.Nothing.nothing;
import fr.bluepyth.funchelper.function.F1;
import fr.bluepyth.funchelper.function.F2;

public final class Predef {

	public static <A> F1<A,A> id() {
		return input -> input;
	}

	public static <A> F1<A,Nothing> nothing1() {
		return input -> nothing;
	}

	public static <A,B> F2<A, B, Nothing> nothing2() {
		return (A i1, B i2) ->nothing;
	}

	public static final F1<String, Boolean> isStrEmpty = //
		str -> str.isEmpty();

	public static final F1<String, Boolean> isStrNotEmpty = //
		str -> !str.isEmpty();

	public static final F1<Object, String> objToString = //
		obj -> obj.toString();
}
