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
the best read performance, and write performance isn&rsquo;t an issue for an immutable class.
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
by key, the performance is of course quadratic &ndash; _O_(_n<sup>2</sup>_).)

In a further optimisation, indexed access is allowed to all forms of collection, including `ImmutableSet` and the `Set`
and `Collection` implementations returned by`ImmutableMap.keySet()` and `ImmutableMap.values()`.
This makes it possible to iterate over these collections without instantiating an `Iterator` object.

The unmarshalling of serialised data (e.g. JSON, XML) often fits the characteristics described above, and there are
probably many other use cases that would benefit from these implementations.

## Mini-Collections

A related case involves small sets or maps used to validate input or to perform a simple lookup.
Take the following example:
```java
    Set<String> allowableLanguages = new HashSet<>(Arrays.asList("Java", "Kotlin", "Scala"));

    if (!allowableLanguages.contains(language))
        throw new IllegalArgumentException(language);
```
Sometimes you have to wonder whether it would be easier to just write:
```java
    if (!language.equals("Java") && !language.equals("Kotlin") && !language.equals("Scala"))
        throw new IllegalArgumentException(language);
```
Yes, it&rsquo;s true that the `contains` operation will complete in _O_(1) time, but only at the expense of the creation
of a complex data structure and the hashing of each entry and each value to be compared.

`MiniSet` allows the validation to be coded as:
```java
    Set<String> allowableLanguages = MiniSet.of("Java", "Kotlin", "Scala");

    if (!allowableLanguages.contains(language))
        throw new IllegalArgumentException(language);
```
The `contains` operation in this case will function similarly to the set of three `equals` comparisons, stopping when a
match is found.
Depending on the frequency of a hit in the first or second comparisons, this may well be faster than the _O_(1)
operation, even without taking into consideration the time and memory overhead of constructing the `HashSet`.
It is certainly more legible to code.

This is an example of a &ldquo;loop unrolling&rdquo; optimisation &ndash; a function written for a specific number of
entries can eliminate the overhead of the looping and counter comparison code.
`MiniSet` classes are available for up to five entries.

In a similar manner, the `MiniMap` classes provide a mechanism for specifying `Map` objects without the overhead of
`HashMap`:
```java
    Map<String, String> additionalAttributes = MiniMap.map("Language", "Java");
```
This is likely to be of use mainly in cases where the `Map` has only one or two entries;
`MiniMap` classes are available for up to three entries.

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

`ImmutableMap` operates on an array of `ImmutableMapEntry`, which may be created by the static function
`createArray()`:
```java
        ImmutableMapEntry<String, LineItem>[] array = ImmutableMap.createArray(length);
```

Then, the array entries are constructed and added to the array:
```java
        array[0] = new ImmutableMapEntry<>(key, value);
```
Or, more simply, using a static function:
```java
        array[0] = ImmutableMap.entry(key, value);
```

Alternatively, if the length of the array is not known in advance (and a reasonable default can not be assumed), the
array entries may be built in a `List`:
```java
        List<ImmutableMapEntry<String, LineItem>> list = new ArrayList<>();
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
        Map<String, LineItem> map = new ImmutableMap(array, length);
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

The array is not copied (except in the case of `ImmutableMap.from(list)`), so the remarks in the description of
[`ImmutableList`](#immutablelist) apply here also.

All operations normally available through the `Map` interface are available, but all modifying operations will cause an
`UnsupportedOperationException`.

There are also indexed operations `getEntry(n)`, `getKey(n)` and `getValue(n)` which return the `ImmutableMapEntry`, the
key or the value at the given index.
These allow for very fast iteration over the contents of an `ImmutableMap`, avoiding the need for creation of `Set` or
`Iterator` objects.

### `ImmutableMapEntry`

`ImmutableMapEntry` is a simple implementation of `Map.Entry` which blocks the `setValue()` method.
It is primarily intended to be used by `ImmutableMap`, but it may be used whenever an immutable map entry object is
required.

### `ImmutableIterator`

`ImmutableIterator` is used within the library as the object returned by `iterator()` calls on `ImmutableList` and
`ImmutableSet`, but it may also be used to create an `Iterator` over an arbitrary subset of entries of any array.

### `ImmutableListIterator`

Like `ImmutableIterator`, `ImmutableListIterator` is used within the library as the object returned by `listIterator()`
calls on `ImmutableList`, but it may also be used to create a `ListIterator` over an arbitrary subset of entries of any
array.

### `MiniSet`

`MiniSet` is the base class of a small number of individually optimised `Set` implementations for set sizes 0 &ndash; 5.
The classes are intended to be used mainly for lookup (`contains`) operations, but they also fulfil all the API
requirements for an immutable `Set`.

Instances of `MiniSet` may be created using the static `MiniSet.of()` function, which is overloaded to take zero or more
parameters.

To create a `MiniSet` with 3 entries:
```java
    Set<String> suits = MiniSet.of("Dots", "Bamboo", "Characters");
```
If more than 5 items are specified, the function will create an `ImmutableSet` instead.

### `MiniMap`

The `MiniMap` classes are a set of `Map` implementations for 0 &ndash; 3 entries.
As with `MiniSet`, the classes meet all the API requirements of an immutable `Map`.

The base class `MiniMap` has two alternative forms of static function to create a `Map` (again, with multiple overloaded
method signatures).

To create a `MiniMap` with a single entry:
```java
    Map<String, Boolean> options = MiniMap.map("verbose", true);
```
With two entries:
```java
    Map<String, Boolean> options = MiniMap.map("verbose", true, "debug", true);
```
Or three:
```java
    Map<String, Boolean> options = MiniMap.map("verbose", true, "debug", true, "delete", false);
```
No more than three entries may be specified in this manner.

Alternatively, to create a `MiniMap` using a vararg list of `Map.Entry` objects:
```java
    Map<String, Boolean> options = MiniMap.of(MiniMap.entry("verbose", true));
```
(This also illustrates the use of the `MiniMap.entry()` static function to create a `Map.Entry`.)

If more than 3 entries are specified, an `ImmutableMap` will be created.

## Dependency Specification

The latest version of the library is 2.5, and it may be obtained from the Maven Central repository.

### Maven
```xml
    <dependency>
      <groupId>net.pwall.util</groupId>
      <artifactId>immutables</artifactId>
      <version>2.5</version>
    </dependency>
```
### Gradle
```groovy
    implementation 'net.pwall.util:immutables:2.5'
```
### Gradle (kts)
```kotlin
    implementation("net.pwall.util:immutables:2.5")
```

Peter Wall

2024-02-18
