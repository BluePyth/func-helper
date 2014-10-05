## Functional helpers for java

[![Build Status](https://travis-ci.org/rsertelon/func-helper.svg?branch=master)](https://travis-ci.org/rsertelon/func-helper)

This project is a really simple functional library. It provides only the building blocks that I lacked while working on a project. It is mostly inspired by what I've seen and used in the Scala language.

## Get it

```xml
<dependency>
    <groupId>fr.sertelon.fp</groupId>
    <artifactId>func-helper</artifactId>
    <version>2.0.0</version>
</dependency>
```

## Features

### Opt

Option[T] is a very handy structure in Scala. Guava has its own Optional<T>, but I didn't want to depend on any big lib. Thus, Opt<T>

```java
public class Option {
	
	Function<Integer, String> intToString = input -> String.valueOf(input);
	
	Function<String, Boolean> isEmpty = input -> input.isEmpty();
	
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
	
	Function<Integer, String> intToString = input -> String.valueOf(input);

	Function<Integer, Boolean> isOdd = input -> input % 2 == 1;

	Function<Integer, Nothing> print = input -> {
		System.out.println(input);
		return nothing;
	};
	
	BiFunction<Integer, Integer, Integer> sum = (i1, i2) ->  i1 + i2;
}

```

### Try

This structure allows you to return success or failure information.

```java
public class TryStructure {
	
	public TryStructure() {
		
		Try.emptySuccess(); // => Success<Nothing>(nothing)
		Try.failure(new Exception()); // => Failure<Exception, Object>
		Try.success(Integer.valueOf(2)); // => Success<Integer>(2)
		
		strToDouble.apply("2.34"); // => Success<Double>(2.34)
		strToDouble.apply("xxxx"); // => Failure(NumberFormatException, Double>
		
		strToDouble.apply("2.34").map(doubleToStr); // => Success<String>("2.34")
		
		IList<Try<Integer>> l = 
			list((Try<Integer>) success(1), (Try<Integer>) success(2), (Try<Integer>) success(3));
		
		Try<IList<Integer>> tl = trySeq(l); // => Success(List(1,2,3)) 
	}
	
	FTry<String, Double> strToDouble = input -> {
		try {
			return success(Double.valueOf("xxx"));
		} catch (NumberFormatException e) {
			return failure(e);
		}
	};
	
	FTry<Double, String> doubleToStr = input -> Try.success(input.toString());
	
}
```

## Roadmap

If someone else than me is to use this library, I'm open to feedback! Will add whatever I'd like to use in Java, as long as I can keep this lib lightweight, otherwise, I should be better off using an existing one.

## License

Copyright Romain Sertelon 2013

This software is licenced under the Apache Software License v2.0, you can find it in the LICENCE file.
