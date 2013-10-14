package fr.bluepyth.funchelper.function;

import static fr.bluepyth.funchelper.Nothing.nothing;
import fr.bluepyth.funchelper.Nothing;

public interface F1<I, O> {
	O apply(I input);
	
	public static class Predef {
		public static <A> F1<A,A> id() {
			return new F1<A, A>() {
				@Override
				public A apply(A input) {
					return input;
				}
			};
		}
		
		public static <A> F1<A,Nothing> nothing() {
			return new F1<A, Nothing>() {
				@Override
				public Nothing apply(A input) {
					return nothing;
				}
			};
		}
	}
}
