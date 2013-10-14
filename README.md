## Functional helpers for java

[![Build Status](https://travis-ci.org/BluePyth/func-helper.png?branch=master)](https://travis-ci.org/BluePyth/func-helper)

This project is a really simple functional library. It provides only the building blocks that I lacked while working on a project. It is mostly inspired by what I've seen and used in the Scala language.

## Get it

Func-helper is available in my maven repository.

```xml
<!-- Repository -->
<repositories>
    <repository>
        <id>bluepyth</id>
        <name>BluePyth Repository</name>
        <url>http://repository.bluepyth.fr/content/groups/public</url>
    </repository>
</repositories>

<!-- Dependency -->
<dependency>
    <groupId>fr.bluepyth</groupId>
    <artifactId>func-helper</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Features

### Functions

As building blocks of functional programming, they are present in this library. They are easy to use, but until Java 8, the syntax will look very heavy.

```java
import static fr.bluepyth.funchelper.function.FunComposer.compose;
import fr.bluepyth.funchelper.function.F1;


public class Functions {
	
	F1<Integer, String> intToString = new F1<Integer, String>() {
		@Override
		public String apply(Integer input) {
			return String.valueOf(input);
		}
	};
	
	F1<String, Integer> stringToInt = new F1<String, Integer>() {
		@Override
		public Integer apply(String input) {
			return Integer.valueOf(input);
		}
	};

	public Functions() {
		intToString.apply(2);   // => "2"
		stringToInt.apply("3"); // => 3
		
		compose(intToString, stringToInt).apply(2); // => 2
	}
}
```

### Opt

Option[T] is a very handy structure in Scala. Guava has its own Optional<T>, but I didn't want to depend on any lib. Thus, Opt<T>

```java
import static fr.bluepyth.funchelper.option.Opt.none;
import static fr.bluepyth.funchelper.option.Opt.toOpt;
import fr.bluepyth.funchelper.function.F1;
import fr.bluepyth.funchelper.option.Opt;


public class Option {
	
	F1<Integer, String> intToString = new F1<Integer, String>() {
		@Override
		public String apply(Integer input) {
			return String.valueOf(input);
		}
	};
	
	F1<String, Boolean> isEmpty = new F1<String, Boolean>() {
		@Override
		public Boolean apply(String input) {
			return input.isEmpty();
		}
	};
	
	public Option() {
				
		Opt<Integer> intOpt = toOpt(null); // => None
		Opt<Integer> intOpt2 = toOpt(3);   // => Some(3)
		
		Opt<Integer> choose = intOpt.or(intOpt2); // => Some(3)
		
		Opt<String> map = choose.map(intToString); // => Some("3")
		
		map.isDefined(); // => true
		intOpt.getOrElse(3); // => 3
		intOpt.get(); // => NoSuchElementException
		Opt<String> strOpt = toOpt((Object) "3").asOpt(String.class);
		toOpt("").filter(isEmpty); // => None		
		
		Opt<Integer> noneOpt = none();
	}
}
```
### IList

This construct is an immutable linked list. It is based on the same implementation as Scala's List.

```java
import static fr.bluepyth.funchelper.Nothing.nothing;
import static fr.bluepyth.funchelper.immutable.IList.list;
import static fr.bluepyth.funchelper.immutable.IList.nil;
import static fr.bluepyth.funchelper.immutable.IList.range;
import static fr.bluepyth.funchelper.immutable.IList.rangeIncl;
import fr.bluepyth.funchelper.Nothing;
import fr.bluepyth.funchelper.function.F1;
import fr.bluepyth.funchelper.function.F2;
import fr.bluepyth.funchelper.immutable.IList;


public class ImmutableList {
	
	public ImmutableList() {
		
		nil();  // => empty list
		IList<Integer> l = list(1, 2, 3); // => List(1,2,3)
		range(0, 3); // List(0,1,2)
		rangeIncl(0, 3); // List(0,1,2,3)
		
		l.get(1); // => 2
		l.head(); // => 1
		l.tail(); // => List(2,3)
		l.isEmpty(); // => false
		l.reverse(); // => List(3,2,1)
		l.mkString("-"); // => 1-2-3
		l.mkString("[", ":", "]"); // => [1:2:3]
		l.prepend(0); // => List(0,1,2,3)
		
		l.map(intToString); // => List("1","2","3")
		l.filter(isOdd); // => List(1,3)
		l.foreach(print); // => prints 1, then 2, then 3
		l.foldLeft(0, sum); // => 6
	}
	
	F1<Integer, String> intToString = new F1<Integer, String>() {
		@Override
		public String apply(Integer input) {
			return String.valueOf(input);
		}
	};
	
	F1<Integer, Boolean> isOdd = new F1<Integer, Boolean>() {
		@Override
		public Boolean apply(Integer input) {
			return input % 2 == 1;
		}
	};
	
	F1<Integer, Nothing> print = new F1<Integer, Nothing>() {
		@Override
		public Nothing apply(Integer input) {
			System.out.println(input);
			return nothing;
		}
	};
	
	F2<Integer, Integer, Integer> sum = new F2<Integer, Integer, Integer>() {
		@Override
		public Integer apply(Integer i1, Integer i2) {
			return i1 + i2;
		}
	};
}

```

### Try

This structure allows you to return success or failure information.

```java
import static fr.bluepyth.funchelper.immutable.IList.list;
import static fr.bluepyth.funchelper.trylike.Try.emptySuccess;
import static fr.bluepyth.funchelper.trylike.Try.failure;
import static fr.bluepyth.funchelper.trylike.Try.success;
import static fr.bluepyth.funchelper.trylike.Try.trySeq;
import fr.bluepyth.funchelper.immutable.IList;
import fr.bluepyth.funchelper.trylike.FTry;
import fr.bluepyth.funchelper.trylike.Try;


public class TryStructure {
	
	public TryStructure() {
		
		emptySuccess(); // => Success<Nothing>(nothing)
		failure(new Exception()); // => Failure<Exception, Object>
		success(Integer.valueOf(2)); // => Success<Integer>(2)
		
		strToDouble.apply("2.34"); // => Success<Double>(2.34)
		strToDouble.apply("xxxx"); // => Failure(NumberFormatException, Double>
		
		strToDouble.apply("2.34").map(doubleToStr); // => Success<String>("2.34")
		
		IList<Try<Integer>> l = 
			list((Try<Integer>) success(1), (Try<Integer>) success(2), (Try<Integer>) success(3));
		
		Try<IList<Integer>> tl = trySeq(l); // => Success(List(1,2,3)) 
	}
	
	FTry<String, Double> strToDouble = new FTry<String, Double>() {
		@Override
		public Try<Double> apply(String input) {
			try {
				return success(Double.valueOf("xxx"));
			} catch (NumberFormatException e) {
				return failure(e);
			}
		}
	};
	
	FTry<Double, String> doubleToStr = new FTry<Double, String>() {
		@Override
		public Try<String> apply(Double input) {
			return success(input.toString());
		}
	};
	
}
```

## Roadmap

If someone else than me is to use this library, I'm open to feedback! Will add whatever I'd like to use in Java.

## License

Copyright Romain Sertelon 2013

This software is licenced under the Apache Software License v2.0, you can find it in the LICENCE file.
