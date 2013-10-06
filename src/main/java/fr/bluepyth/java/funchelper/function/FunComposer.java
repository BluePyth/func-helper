package fr.bluepyth.java.funchelper.function;


public abstract class FunComposer<A,B> {

	public static <A,B> FunComposer<A,B> compose(F1<A,B> f1) {
		return ComposerNil.<B>nil().prepend(f1);
	}
	
	public static <A,B,C> FunComposer<A,C> compose(F1<A,B> f1, F1<B,C> f2) {
		return compose(f2).prepend(f1);
	}
	
	public static <A,B,C,D> FunComposer<A,D> compose(F1<A,B> f1, F1<B,C> f2, F1<C,D> f3) {
		return compose(f2, f3).prepend(f1);
	}
	
	public static <A,B,C,D,E> FunComposer<A,E> compose(F1<A,B> f1, F1<B,C> f2, F1<C,D> f3, F1<D,E> f4) {
		return compose(f2, f3, f4).prepend(f1);
	}
	
	public abstract B execute(A arg);

	public <T> FunComposer<T,B> prepend(F1<T,A> previousAction) {
		return new ComposerCons<T,A,B>(previousAction, this);
	}
	
	public static class ComposerCons<A,I,B> extends FunComposer<A,B> {
		
		private final F1<A,I> action;
		
		private final FunComposer<I,B> next;
		
		public ComposerCons(F1<A,I> action, FunComposer<I,B> next) {
			this.action = action;
			this.next = next;
		}

		@Override
		public B execute(A arg) {
			return next.execute(action.apply(arg));
		}
		
	}
	
	public static class ComposerNil<A> extends FunComposer<A,A> {
		
		public static <T> ComposerNil<T> nil() {
			return new ComposerNil<T>();
		}

		@Override
		public A execute(A arg) {
			return arg;
		}

	}
}
