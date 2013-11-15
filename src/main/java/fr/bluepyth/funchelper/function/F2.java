package fr.bluepyth.funchelper.function;

public abstract class F2<I1, I2, O> {
	public abstract O apply(I1 i1, I2 i2);
	
	public <A,B> F2<A,B,O> preComp(final F1<A, I1> f1, final F1<B,I2> f2) {
		return new F2<A, B, O>() {
			@Override
			public O apply(A i1, B i2) {
				return F2.this.apply(f1.apply(i1), f2.apply(i2));
			}
		};
	}
	
	public <A> F2<I1,I2,A> then(final F1<O, A> f) {
		return new F2<I1, I2, A>() {
			@Override
			public A apply(I1 i1, I2 i2) {
				return f.apply(F2.this.apply(i1, i2));
			}
		};
	}
}
