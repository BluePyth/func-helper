package fr.sertelon.fp.funchelper.function;

public interface F2<I1, I2, O> {
	O apply(I1 i1, I2 i2);
	
	public default <A,B> F2<A,B,O> preComp(final F1<A, I1> f1, final F1<B,I2> f2) {
		return (A i1, B i2) -> F2.this.apply(f1.apply(i1), f2.apply(i2));
	}
	
	public default <A> F2<I1,I2,A> then(final F1<O, A> f) {
		return (I1 i1, I2 i2) -> f.apply(F2.this.apply(i1, i2));
	}
}
