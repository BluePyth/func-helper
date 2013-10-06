package fr.bluepyth.java.funchelper.trylike;

import static fr.bluepyth.java.funchelper.trylike.Try.success;
import fr.bluepyth.java.funchelper.function.F1;

public abstract class TryComposer<A, B> {
	
	public static <A,B> TryComposer<A,B> composeTry(F1<A,Try<B>> f1) {
		return TryComposerNil.<B>nil().prepend(f1);
	}
	
	public static <A,B,C> TryComposer<A,C> composeTry(F1<A,Try<B>> f1, F1<B,Try<C>> f2) {
		return composeTry(f2).prepend(f1);
	}
	
	public static <A,B,C,D> TryComposer<A,D> composeTry(F1<A,Try<B>> f1, F1<B,Try<C>> f2, F1<C,Try<D>> f3) {
		return composeTry(f2, f3).prepend(f1);
	}
	
	public static <A,B,C,D,E> TryComposer<A,E> composeTry(F1<A,Try<B>> f1, F1<B,Try<C>> f2, F1<C,Try<D>> f3, F1<D,Try<E>> f4) {
		return composeTry(f2, f3, f4).prepend(f1);
	}
	
	public abstract Try<B> execute(A arg);

	public <T> TryComposer<T,B> prepend(F1<T,Try<A>> previousAction) {
		return new TryComposerCons<T,A,B>(previousAction, this);
	}
	
	public static class TryComposerCons<A,I,B> extends TryComposer<A,B> {
		
		private final F1<A,Try<I>> action;
		
		private final TryComposer<I,B> next;
		
		public TryComposerCons(F1<A,Try<I>> action, TryComposer<I,B> next) {
			this.action = action;
			this.next = next;
		}

		@Override
		public Try<B> execute(A arg) {
			return action.apply(arg).map(new F1<I, Try<B>>() {
				@Override
				public Try<B> apply(I input) {
					return next.execute(input);
				}
			});
		}
		
	}
	
	public static class TryComposerNil<A> extends TryComposer<A,A> {
		
		public static <T> TryComposerNil<T> nil() {
			return new TryComposerNil<T>();
		}

		@Override
		public Try<A> execute(A arg) {
			return success(arg);
		}

	}
}
