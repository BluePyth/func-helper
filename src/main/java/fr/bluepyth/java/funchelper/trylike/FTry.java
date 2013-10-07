package fr.bluepyth.java.funchelper.trylike;

import static fr.bluepyth.java.funchelper.trylike.Try.failure;
import static fr.bluepyth.java.funchelper.trylike.Try.success;
import fr.bluepyth.java.funchelper.function.F1;

public interface FTry<I, O> extends F1<I, Try<O>> {
	
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
