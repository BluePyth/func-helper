package fr.bluepyth.funchelper.trylike;

import static fr.bluepyth.funchelper.trylike.Try.emptySuccess;
import static fr.bluepyth.funchelper.trylike.Try.failure;
import static fr.bluepyth.funchelper.trylike.Try.success;
import fr.bluepyth.funchelper.Nothing;
import fr.bluepyth.funchelper.function.F1;

public interface FTry<I, O> extends F1<I, Try<O>> {
	
	public static class Predef {
	
		public static <A> FTry<A, Nothing> nothing1() {
			return new FTry<A, Nothing>() {
				public Try<Nothing> apply(A input) {
					return emptySuccess();
				}
			};
		}
		
		public static abstract class TryWrapper<U,V> implements FTry<U, V> {
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
