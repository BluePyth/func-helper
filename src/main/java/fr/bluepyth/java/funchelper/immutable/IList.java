package fr.bluepyth.java.funchelper.immutable;

import static fr.bluepyth.java.funchelper.Nothing.nothing;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.bluepyth.java.funchelper.Nothing;
import fr.bluepyth.java.funchelper.function.F1;
import fr.bluepyth.java.funchelper.function.F2;

public abstract class IList<T> {
	
	public static <U> IList<U> nil() {
		return new Nil<U>();
	}
	
	public static <U> IList<U> list(U... elements) {
		IList<U> list = nil();
		for(int i = elements.length - 1; i >= 0; i--) {
			list = list.prepend(elements[i]);
		}
		return list;
	}
	
	public static IList<Integer> range(int from, int to) {
		IList<Integer> list = nil();
		for(int i = to - 1; i >= from ; i--) {
			list = list.prepend(i);
		}
		return list;
	}
	
	public abstract T head();
	public abstract IList<T> tail();
	public abstract boolean isEmpty();
	public abstract T get(int i);
	
	public IList<T> reverse() {
		return reverseAcc(IList.<T>nil());
	}
	protected abstract IList<T> reverseAcc(IList<T> acc);
	
	public IList<T> filter(F1<T, Boolean> predicate) {
		return filterAcc(predicate, IList.<T>nil());
	}
	protected abstract IList<T> filterAcc(F1<T, Boolean> p, IList<T> acc);
	
	public <U> IList<U> map(F1<T,U> f) {
		return mapAcc(f, IList.<U>nil());
	}
	protected abstract <U> IList<U> mapAcc(F1<T,U> f, IList<U> acc);

	public String mkString(String sep) {
		return mkString("", sep, "");
	}
	
	public String mkString(String start, final String sep, String end) {
		final AtomicBoolean first = new AtomicBoolean(true); 
		final StringBuilder sb = new StringBuilder();
		
		sb.append(start);
		
		foreach(new F1<T, Nothing>() {
			public Nothing apply(T input) {
				if(first.get()) {
					first.set(false);
				} else {
					sb.append(sep);
				}
				sb.append(input);
				return nothing;
			}
		});
		
		sb.append(end);
		
		return sb.toString();
		
	}
	
	public abstract Nothing foreach(F1<T, Nothing> f);
	
	public <U> U foldLeft(U init, F2<U, T, U> f) {
		return foldLeftAcc(f, init);
	}
	protected abstract <U> U foldLeftAcc(F2<U,T,U> f, U acc);
	
	public IList<T> prepend(T element) {
		return new Cons<T>(element, this);
	}
	
	@Override
	public String toString() {
		return mkString("List(", ",", ")");
	}
	
	public static class Cons<T> extends IList<T> {

		private final T head;
		private final IList<T> tail;
		
		public Cons(T head, IList<T> tail) {
			this.head = head;
			this.tail = tail;
		}
		
		@Override
		public T head() {
			return head;
		}

		@Override
		public IList<T> tail() {
			return tail;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public T get(int i) {
			return i == 0 ? head : tail.get(i - 1);
		}

		@Override
		public IList<T> reverseAcc(IList<T> acc) {
			return tail.reverseAcc(acc.prepend(head));
		}

		@Override
		public IList<T> filterAcc(F1<T, Boolean> p, IList<T> acc) {
			return tail.filterAcc(p, p.apply(head) ? acc.prepend(head) : acc);
		}

		@Override
		protected <U> IList<U> mapAcc(F1<T, U> f, IList<U> acc) {
			return tail.mapAcc(f, acc.prepend(f.apply(head)));
		}

		@Override
		protected <U> U foldLeftAcc(F2<U, T, U> f, U acc) {
			return tail.foldLeftAcc(f, f.apply(acc, head));
		}

		@Override
		public Nothing foreach(F1<T, Nothing> f) {
			f.apply(head);
			return tail.foreach(f);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof IList))
				return false;
			
			IList<?> other = (IList<?>) obj;
			
			if(other.isEmpty())
				return false;
			
			if(head.equals(other.head()))
				return tail.equals(other.tail());
			else
				return false;
		}
		
	}
	
	public static class Nil<T> extends IList<T> {

		@Override
		public T head() {
			throw new UnsupportedOperationException();
		}

		@Override
		public IList<T> tail() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public T get(int i) {
			throw new IndexOutOfBoundsException();
		}

		@Override
		public IList<T> reverseAcc(IList<T> acc) {
			return acc;
		}

		@Override
		public IList<T> filterAcc(F1<T, Boolean> p, IList<T> acc) {
			return acc.reverse();
		}

		@Override
		protected <U> IList<U> mapAcc(F1<T, U> f, IList<U> acc) {
			return acc.reverse();
		}

		@Override
		protected <U> U foldLeftAcc(F2<U, T, U> f, U acc) {
			return acc;
		}

		@Override
		public Nothing foreach(F1<T, Nothing> f) {
			return nothing;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof IList))
				return false;
			else 
				return ((IList<?>) obj).isEmpty();
		}
	}
	
}
