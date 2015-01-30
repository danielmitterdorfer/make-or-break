Make or Break
=============

This is an accompanying demo project for my talk "Make or Break: The Big Impact of Small Changes on Performance in Java Programs" (link to the slide follows around the end of February). It contains [JMH](http://openjdk.java.net/projects/code-tools/jmh) microbenchmarks to demonstrate different effects of Java code on CPU level.

# Getting Started

## Prerequisites

The project requires at least JDK 8.

## Installation and Usage

If Gradle is not installed:

```
git clone https://github.com/danielmitterdorfer/make-or-break.git
cd make-or-break
./gradlew shadow
java -jar build/libs/make-or-break-0.1.0-all.jar
```

Or alternatively, if Gradle > 2.1 is installed:

```
git clone https://github.com/danielmitterdorfer/make-or-break.git
cd make-or-break
gradle shadow
java -jar build/libs/make-or-break-0.1.0-all.jar
```

## Usage with `perf`

You'll get the most out of the benchmarks when running them with the JMH perf profiler. This requires that the benchmark is run on Linux as `perf` is only supported there. So please make sure `perf` is installed. Please consult the documentation of your Linux distribution on how to do that.

If `perf` is installed, you can run the benchmarks with the JMH perf profiler:

```
java -jar build/libs/make-or-break-0.1.0-all.jar -prof perf
```

This will print perf statistics for each iteration in addition to the usually gathered metrics.

# License

'Make or Break' is distributed under the terms of the [Apache Software Foundation license, version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).