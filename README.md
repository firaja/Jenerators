# java-generators

![GitHub (pre-)release](https://img.shields.io/github/release/firaja/java-generators/all.svg) 
![Travis](https://img.shields.io/travis/firaja/java-generators.svg) 
[![Maintainability](https://api.codeclimate.com/v1/badges/630b3107e8dd859a9d2a/maintainability)](https://codeclimate.com/github/firaja/java-generators/maintainability) 
[![codecov](https://codecov.io/gh/firaja/java-generators/branch/master/graph/badge.svg)](https://codecov.io/gh/firaja/java-generators)

A Java library for building generators.

With a simple and clear interface, build a generator returning elements on-demand.

## Getting Started

The following code models the mathematical structure for the first 50 elements
of the Fibonacci sequence:
```java
StatefulGenerator<Long> fibonacci = new MemoryListGenerator<Long>(50) {
	@Override
	public Long generate() {
		if (c() == 0) {
			return 1L;
		} else if (c() == 1) {
			return 1L;
		} else {
			return getResult(c() - 1) + getResult(c() - 2);
		}
	}
};

```
And then you can just *lazily* retrieve the results like this:
```java
for(Long i : fibonacci){
	System.out.println(i);
}
```
## Documentation

The javadoc API can be found in this [page](https://firaja.github.io/java-generators/doc)


## Authors

* **David Bertoldi** - *Creator* - [firaja](https://github.com/firaja)

See also the list of [contributors](https://github.com/firaja/java-generators/graphs/contributors) who participated in this project.

## License

This project is licensed under the Apache-2.0 - see the [LICENSE.md](LICENSE.md) file for details

