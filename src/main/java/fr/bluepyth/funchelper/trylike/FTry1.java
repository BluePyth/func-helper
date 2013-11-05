package fr.bluepyth.funchelper.trylike;

import static fr.bluepyth.funchelper.trylike.Try.emptySuccess;
import static fr.bluepyth.funchelper.trylike.Try.failure;
import static fr.bluepyth.funchelper.trylike.Try.success;
import fr.bluepyth.funchelper.Nothing;
import fr.bluepyth.funchelper.function.F1;

public abstract class FTry1<I, O> extends F1<I, Try<O>> {
	
	public <A> FTry1<I, A> then(final FTry1<O, A> f) {
		return new FTry1<I, A>() {
			@Override
			public Try<A> apply(I input) {
				return FTry1.this.apply(input).flatMap(f);
			}
		};
	}
	
	public static class Predef {
	
		public static <A> FTry1<A, Nothing> nothing1() {
			return new FTry1<A, Nothing>() {
				public Try<Nothing> apply(A input) {
					return emptySuccess();
				}
			};
		}
		
		public static abstract class TryWrapper<U,V> extends FTry1<U, V> {
			@Override
			public Try<V> apply(U input) {
				try {
					return success(wrap(input));
				} catch (Exception e) {
					return failure(e);
				}
			}
			
			public abstract V wrap(U input) throws Exception;
		}
	}
}
