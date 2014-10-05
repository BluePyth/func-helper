package fr.sertelon.fp.funchelper;

import static fr.sertelon.fp.funchelper.Nothing.nothing;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class Predef {

	public static <A> Function<A,Nothing> nothing1() {
		return input -> nothing;
	}

	public static <A,B> BiFunction<A, B, Nothing> nothing2() {
		return (A i1, B i2) -> nothing;
	}

	public static final Function<String, Boolean> isStrEmpty = //
		str -> str.isEmpty();

	public static final Function<String, Boolean> isStrNotEmpty = //
		str -> !str.isEmpty();

	public static final Function<Object, String> objToString = //
		obj -> obj.toString();
}
