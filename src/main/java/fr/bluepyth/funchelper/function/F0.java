package fr.bluepyth.funchelper.function;

public abstract class F0<O> {
	public abstract O apply();
	
	public <A> F0<A> then(final F1<O,A> f) {
		return new F0<A>() {
			@Override
			public A apply() {
				return f.apply(F0.this.apply());
			}
		};
		
	}
}
