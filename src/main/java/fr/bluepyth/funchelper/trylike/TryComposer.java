package fr.bluepyth.funchelper.trylike;

import static fr.bluepyth.funchelper.trylike.Try.success;

public abstract class TryComposer<A, B> implements FTry<A, B> {
	
	public static <A,B> TryComposer<A,B> composeTry(FTry<A,B> f1) {
		return TryComposerNil.<B>nil().prepend(f1);
	}
	
	public static <A,B,C> TryComposer<A,C> composeTry(FTry<A,B> f1, FTry<B,C> f2) {
		return composeTry(f2).prepend(f1);
	}
	
	public static <A,B,C,D> TryComposer<A,D> composeTry(FTry<A,B> f1, FTry<B,C> f2, FTry<C,D> f3) {
		return composeTry(f2, f3).prepend(f1);
	}
	
	public static <A,B,C,D,E> TryComposer<A,E> composeTry(FTry<A,B> f1, FTry<B,C> f2, FTry<C,D> f3, FTry<D,E> f4) {
		return composeTry(f2, f3, f4).prepend(f1);
	}
	
	public <T> TryComposer<T,B> prepend(FTry<T,A> previousAction) {
		return new TryComposerCons<T,A,B>(previousAction, this);
	}
	
	public static class TryComposerCons<A,I,B> extends TryComposer<A,B> {
		
		private final FTry<A,I> action;
		
		private final TryComposer<I,B> next;
		
		public TryComposerCons(FTry<A,I> action, TryComposer<I,B> next) {
			this.action = action;
			this.next = next;
		}

		@Override
		public Try<B> apply(A arg) {
			return action.apply(arg).map(new FTry<I, B>() {
				@Override
				public Try<B> apply(I input) {
					return next.apply(input);
				}
			});
		}
		
	}
	
	public static class TryComposerNil<A> extends TryComposer<A,A> {
		
		public static <T> TryComposerNil<T> nil() {
			return new TryComposerNil<T>();
		}

		@Override
		public Try<A> apply(A arg) {
			return success(arg);
		}

	}
}
