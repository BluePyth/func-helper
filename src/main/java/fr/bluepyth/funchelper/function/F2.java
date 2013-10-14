package fr.bluepyth.funchelper.function;

import static fr.bluepyth.funchelper.Nothing.nothing;
import fr.bluepyth.funchelper.Nothing;

public interface F2<I1, I2, O> {
	O apply(I1 i1, I2 i2);

	public static class Predef {
		public static <A,B> F2<A, B, Nothing> nothing() {
			return new F2<A, B, Nothing>() {
				@Override
				public Nothing apply(A i1, B i2) {
					return nothing;
				}
			};
		}
	}
}
