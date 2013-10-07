package fr.bluepyth.java.funchelper.tuples;

public class T2<Left,Right> {
	
	public static <L,R> T2<L,R> t2(L left, R right) {
		return new T2<L, R>(left, right);
	}
	
	private final Left left;
	private final Right right;
	
	public T2(Left left, Right right) {
		this.left = left;
		this.right = right;
	}

	public Left _1() {
		return left;
	}

	public Right _2() {
		return right;
	}
}
