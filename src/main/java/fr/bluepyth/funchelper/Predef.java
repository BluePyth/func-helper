package fr.bluepyth.funchelper;

import static fr.bluepyth.funchelper.Nothing.nothing;
import fr.bluepyth.funchelper.function.F1;
import fr.bluepyth.funchelper.function.F2;

public final class Predef {

	public static <A> F1<A,A> id() {
		return new F1<A, A>() {
			@Override
			public A apply(A input) {
				return input;
			}
		};
	}

	public static <A> F1<A,Nothing> nothing1() {
		return new F1<A, Nothing>() {
			@Override
			public Nothing apply(A input) {
				return nothing;
			}
		};
	}

	public static <A,B> F2<A, B, Nothing> nothing2() {
		return new F2<A, B, Nothing>() {
			@Override
			public Nothing apply(A i1, B i2) {
				return nothing;
			}
		};
	}

	public static final F1<String, Boolean> isStrEmpty = new F1<String, Boolean>() {
		public Boolean apply(String input) {
			return input.isEmpty();
		}
	};

	public static final F1<String, Boolean> isStrNotEmpty = new F1<String, Boolean>() {
		public Boolean apply(String input) {
			return !input.isEmpty();
		}
	};

	public static final F1<Object, String> objToString = new F1<Object, String>() {
		public String apply(Object input) {
			return input.toString();
		}
	};
}
