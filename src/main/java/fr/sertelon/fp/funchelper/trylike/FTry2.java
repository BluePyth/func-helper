package fr.sertelon.fp.funchelper.trylike;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface FTry2<I1, I2, O> extends BiFunction<I1, I2, Try<O>> {
	public abstract Try<O> apply(I1 i1, I2 i2);
	
	public default <A,B> FTry2<A,B,O> preComp(final Function<A, I1> f1, final Function<B,I2> f2) {
		return (A i1, B i2) -> FTry2.this.apply(f1.apply(i1), f2.apply(i2));
	}
	
	public default <A> FTry2<I1,I2,A> then(final FTry1<O, A> f) {
		return (I1 i1, I2 i2) -> FTry2.this.apply(i1, i2).flatMap(f);
	}
}
