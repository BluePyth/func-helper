package fr.sertelon.fp.funchelper.trylike;

import fr.sertelon.fp.funchelper.function.F1;
import fr.sertelon.fp.funchelper.function.F2;

public interface FTry2<I1, I2, O> extends F2<I1, I2, Try<O>> {
	public abstract Try<O> apply(I1 i1, I2 i2);
	
	public default <A,B> FTry2<A,B,O> preComp(final F1<A, I1> f1, final F1<B,I2> f2) {
		return (A i1, B i2) -> FTry2.this.apply(f1.apply(i1), f2.apply(i2));
	}
	
	public default <A> FTry2<I1,I2,A> then(final FTry1<O, A> f) {
		return (I1 i1, I2 i2) -> FTry2.this.apply(i1, i2).flatMap(f);
	}
}
