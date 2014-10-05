package fr.sertelon.fp.funchelper.tuples;

import java.util.Objects;

public class T2<Left,Right> {
	
	public static <L,R> T2<L,R> t2(L left, R right) {
		return new T2<L, R>(left, right);
	}
	
	public final Left _1;
	public final Right _2;
	
	private final int hashcode;
	
	public T2(Left left, Right right) {
		this._1 = left;
		this._2 = right;
		
		this.hashcode = Objects.hash(_1, _2);
	}

	@Override
	public int hashCode() {
		return hashcode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		T2<?, ?> other = (T2<?, ?>) obj;
		if (_1 == null) {
			if (other._1 != null)
				return false;
		} else if (!_1.equals(other._1))
			return false;
		if (_2 == null) {
			if (other._2 != null)
				return false;
		} else if (!_2.equals(other._2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("T2(").append(_1).append(", ").append(_2).append(")");
		return builder.toString();
	}
	
}
