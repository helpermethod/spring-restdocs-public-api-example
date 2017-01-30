# Spring RestDocs Public Api Example

This project is an example of a simple groovy test suite that includes Spring RESTdocs to document a public api, JSON Placeholder.

The published docs from the example are available [here](http://jlstrater.github.io/spring-restdocs-public-api-example/).

-----

This project does not have a runnable application, it is just a test suite.  To build the docs, use:

```
$ gradle build
```

Or the Gradle Wrapper:

```
$ ./gradlew build
```

The docs from this project are sent to the temporary build folder `build/asciidoc/html5`.  The publish.gradle file 
shows the task that publishes them to github pages.

**It is very important to note that the project must be built before trying to run the project. Otherwise, the docs will not show up!**
