# immutables

[![Build Status](https://travis-ci.com/pwall567/immutables.svg?branch=main)](https://travis-ci.com/pwall567/immutables)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Maven Central](https://img.shields.io/maven-central/v/net.pwall.util/immutables?label=Maven%20Central)](https://search.maven.org/search?q=g:%22net.pwall.util%22%20AND%20a:%22immutables%22)

High-performance immutable collections

## Background

This library sprang from a need to provide simple, high-performance immutable collections classes for another project.

The requirements were as follows:
1. Immutable implementations of `List`, `Set` and `Map`
1. Efficient iteration over contents of collections
1. `Map` implementation must retain original order of elements
1. Optimise for large numbers of collections, with each collection relatively small (2 to 20 items, median around 5)

(The name is a nod to one of my favourite cartoon movies, [The Incredibles](https://www.imdb.com/title/tt0317705/).)

## Performance

Decisions about performance are never easy.
Different implementations may be optimised for different patterns of use, and an algorithm that performs well in one
case may be hopelessly slow in another.

There is no argument over the implementation of the `List` interface &ndash; an implementation backed by an array gives
the best read performance, and write performance isn't an issue for an immutable class.
But `Map` is a different matter.

`Map` implementations based on hash codes give constant time performance on lookup &ndash; _O_(1).
But these implementations come with an initialisation overhead, and if the number of lookup operations is small, the
startup cost can outweigh the benefit.

An implementation based on an unsorted array gives linear performance on lookup &ndash; _O_(_n_).
Under some circumstances this would rule it out, but where the number of elements is relatively low and the number of
lookup operations is also not high, then the much quicker startup times can make for better performance overall.

For example, comparing `ImmutableMap` with `LinkedHashMap` (the standard collection implementation offering ordering of
entries), the startup time for `LinkedHashMap` is consistently 2.5 to 3 times greater than for `ImmutableMap`.
With a map containing 5 entries, the better lookup performance of the `LinkedHashMap` would bring an overall benefit
only if there were 15 or more random lookup operations on each map.

Iteration over the entire map is also quicker for `ImmutableMap`, taking approximately 60% of the time taken by
`LinkedHashMap`.
(This is only if the iteration uses `map.entrySet()`; if the iteration uses `map.keySet()` and then retrieves each value
by key, the performance is of course quadratic.)

The unmarshalling of serialised data (e.g. JSON, XML) often fits the characteristics described above, and there are
probably many other use cases that would benefit from these implementations.

## User Guide

### `ImmutableList`

There are two constructors for `ImmutableList<T>`:
```java
        List<String> list = new ImmutableList<>(array);
```
and:
```java
        List<String> list = new ImmutableList<>(array, length);
```

There are also the following static functions:
```java
        List<String> list = ImmutableList.emptyList();
```
```java
        List<String> list = ImmutableList.listOf(array); // return a new list, or the empty list if the array size is 0
```
```java
        List<String> list = ImmutableList.listOf(array, length); // return a new list, or the empty list if length is 0
```

In all cases the array is not copied, so **the list is immutable only if the array is not subsequently modified**.
A good technique to adopt in creating the list is to use a local variable for the array, and then to exit the scope of
the variable, retaining no other reference.

The following code illustrates this pattern:
```java
        public List<LineItem> readLineItemList(Reader rdr) throws IOException {
            LineItem[] array = new LineItem[10];
            int index = 0;
            while (true) {
                LineItem lineItem = readLineItem(rdr);
                if (lineItem == null)
                    break;
                if (index >= 10)
                    throw new RuntimeException("Too many line items");
                array[index++] = lineItem;
            }
            return ImmutableList.listOf(array, index);
        }
```

All operations normally available through the `List` interface are available, but modifying operations will cause an
`UnsupportedOperationException`.

### `ImmutableSet`

There are two constructors for `ImmutableSet<T>`:
```java
        Set<String> set = new ImmutableSet<>(array);
```
and:
```java
        Set<String> set = new ImmutableSet<>(array, length);
```

There are also the following static functions:
```java
        Set<String> set = ImmutableSet.emptySet();
```
```java
        Set<String> set = ImmutableSet.setOf(array); // return a new set, or the empty set if the array size is 0
```
```java
        Set<String> set = ImmutableSet.setOf(array, length); // return a new set, or the empty set if length is 0
```

The array is **not** checked for uniqueness.
The goal of the classes is performance, and it is the responsibility of the user to ensure that there are no duplicates
in the array.
To help with this, the following function (part of `ImmutableCollection`, the base class of both `ImmutableSet` and
`ImmutableList`) tests whether an array contains the given object:
```java
        if (ImmutableCollection.contains(array, length, obj))
            throw new RuntimeException("Duplicate object");
```

The array is not copied; see the remarks in the description of [`ImmutableList`](#immutablelist) for the implications of
this.

All operations normally available through the `Set` interface are available, but all modifying operations will cause an
`UnsupportedOperationException`.

### `ImmutableMap`

`ImmutableMap` operates on an array of `ImmutableMap.MapEntry`, which may be created by the static function
`createArray()`:
```java
        ImmutableMap.MapEntry<String, LineItem>[] array = ImmutableMap.createArray(length);
```

Then, the array entries are constructed and added to the array:
```java
        array[0] = new ImmutableMap.MapEntry<>(key, value);
```
Or, more simply, using a static function:
```java
        array[0] = ImmutableMap.entry(key, value);
```

Alternatively, if the length of the array is not known in advance (and a reasonable default can not be assumed), the
array entries may be built in a `List`:
```java
        List<ImmutableMap.MapEntry<String, LineItem>> list = new ArrayList<>();
```
and:
```java
        list.add(ImmutableMap.entry(key, value));
```

Then, there are two constructors for `ImmutableMap<K, V>`:
```java
        Map<String, LineItem> map = new ImmutableMap(array);
```
and:
```java
        Map<String, LineItem> map = new ImmutableMap(array);
```

There are also these static functions:
```java
        Map<String, LineItem> map = ImmutableMap.emptyMap();
```
```java
        Map<String, LineItem> map = ImmutableMap.mapOf(array);
```
```java
        Map<String, LineItem> map = ImmutableMap.mapOf(array, length);
```
```java
        Map<String, LineItem> map = ImmutableMap.from(list);
```
As with `ImmutableSet`, the array is **not** checked for uniqueness of keys, but also like that class, there is a static
function to check for duplicates:
```java
        if (ImmutableMap.containsKey(array, length, key))
            throw new RuntimeException("Duplicate key");
```

The array is not copied (except in the case of `ImmutableMap.from(list)`, so the remarks in the description of
[`ImmutableList`](#immutablelist) apply here also.

All operations normally available through the `Map` interface are available, but all modifying operations will cause an
`UnsupportedOperationException`.

## Dependency Specification

The latest version of the library is 1.2, and it may be obtained from the Maven Central repository.

### Maven
```xml
    <dependency>
      <groupId>net.pwall.util</groupId>
      <artifactId>immutables</artifactId>
      <version>1.2</version>
    </dependency>
```
### Gradle
```groovy
    testImplementation 'net.pwall.util:immutables:1.2'
```
### Gradle (kts)
```kotlin
    testImplementation("net.pwall.util:immutables:1.2")
```

Peter Wall

2021-08-24
