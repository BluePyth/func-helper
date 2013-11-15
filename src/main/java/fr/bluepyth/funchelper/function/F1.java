package fr.bluepyth.funchelper.function;

public abstract class F1<I, O> {
	public abstract O apply(I input);
	
	public <T> F1<I, T> comp(final F1<O, T> next) {
		return new F1<I, T>() {
			@Override
			public T apply(I input) {
				return next.apply(F1.this.apply(input));
			}
		};
	}
}