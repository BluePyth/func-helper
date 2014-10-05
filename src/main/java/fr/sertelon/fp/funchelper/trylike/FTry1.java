package fr.sertelon.fp.funchelper.trylike;

import static fr.sertelon.fp.funchelper.trylike.Try.emptySuccess;
import static fr.sertelon.fp.funchelper.trylike.Try.failure;
import static fr.sertelon.fp.funchelper.trylike.Try.success;
import fr.sertelon.fp.funchelper.Nothing;
import fr.sertelon.fp.funchelper.function.F1;

public interface FTry1<I, O> extends F1<I, Try<O>> {
	
	public default  <A> FTry1<I, A> then(final FTry1<O, A> f) {
		return (I input) -> FTry1.this.apply(input).flatMap(f);
	}
	
	public static class Predef {
	
		public static <A> FTry1<A, Nothing> nothing1() {
			return (A input) -> {
				return emptySuccess();
			};
		}
		
		public static interface TryWrapper<U,V> extends FTry1<U, V> {
			@Override
			public default Try<V> apply(U input) {
				try {
					return success(wrapped(input));
				} catch (Exception e) {
					return failure(e);
				}
			}
			
			V wrapped(U input) throws Exception;
		}
	}
}
