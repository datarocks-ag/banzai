# Banzai

The banzai library is a transform-process-transform pipeline, that allows to implement complex data
processing task in a well-structured and configurable approach.

The library is named after  _Banzai Pipelines_, a surf reef break located in Hawaii known for its 
spectacular, huge waves that break in shallow water, forming large, hollow, thick curls of water
that surfers can tube ride.

## Banzai Pipeline Design

The benzai library is implemented as a highly configurable pipeline of processors. The pipeline can
easily be extended with additional processors:

![Abstract Pipeline](doc/images/abstract-pipeline.png)

The pipeline starts with a transformer which converts the input data into the data format consumed
by the processors, and ends with a tail transformer which converts the processor format into the
required output format.

### Transformer

A transformer does an operation of type `f: A->B`: It transforms an input object of one type into
another type. This can be a java type conversions, a deserialization, or a serialization.

The head transformer converts the input data of type I into the processing data type P. The tail
transformer converts to processing type P into the output type P.

For pipelines that do not need this conversion (I == P == O) the library provides the
`PassThroughTransformer`. It simply passes input to output.

#### Code Example

Simple transformer converting a BibJson string into a java
pojo. [Full example](src/main/java/org/datarocks/banzai/examples/bibjsonpipe/BibJsonToBibliographyTransformer.java)

```
@SuperBuilder
public class BibJsonToBibliographyTransformer extends AbstractTransformer<String, Bibliography> {
  @Override
  public Bibliography processImpl(String correlationId, String input) {
    Gson gson = new Gson();
    return gson.fromJson(input, Bibliography.class);
  }
}
```

### Processor

A processor does an operation of type `f: P->P`. Implement a concrete processor by extending
from `AbstractSingleItemProcessor<T>` with your pipeline processing type.

For Type P == String:

`public class StringCapitalizer extends AbstractSingleItemProcessor<String>`

and
override `public String processImpl(@NonNull final String correlationId, @NonNull final T input);`

```
public String processImpl(@NonNull final String correlationId, @NonNull final String input) {
    return input.toUpperCase()
}
```

#### Code Example

Simple processor capitalizing all author names in a `Bibliography`
object. [Full example](src/main/java/org/datarocks/banzai/examples/bibjsonpipe/BibliographyCapitalizeProcessor.java)

```
@SuperBuilder
public class BibliographyCapitalizeProcessor extends AbstractSingleItemProcessor<Bibliography> {

  @Override
  public Bibliography processImpl(@NonNull String correlationId,
      @NonNull Bibliography bibliography) {
    bibliography.setAuthor(bibliography.getAuthor().stream().map(author->new Author(author.name.toUpperCase(
        Locale.ROOT))).collect(Collectors.toList()));
    return bibliography;
  }
}
```

### Pipeline

The pipeline is the frame connecting all component (transformers, processors). It provides a
pipeline builder as convenience helper to assemble the pipeline in a few lines of code:
```
PipeLine<InputObject, ProcessObject, OutputObject> pipeLine = PipeLine.builder(
        HandlerConfiguration.builder().build(), InputObject.class, ProcessObject.class, OutputObject.class)
        .addHeadTransformer(InputToProcessTransformer.builder().build())
        .addStep(ProcessObjectProcessor.builder().build())
        .addTailTransformer(ProcessToOutputTransformer.builder().build())
        .build();
InputObject inputObject = ...;
OutputObject outputObjetc = pipeline.process(inputObject);
```

#### Code Example

Bibliography pipeline connecting `BibJsonToBibliographyTransformer`
, `BibliographyCapitalizeProcessor`, and `BibliographyToBibJsonTransformer`.
[Full example](src/main/java/org/datarocks/banzai/examples/bibjsonpipe/BibJsonPipeline.java)

```
PipeLine<String, Bibliography, String> pipeLine = PipeLine.builder(
        HandlerConfiguration.builder().build(), String.class, Bibliography.class, String.class)
        .addHeadTransformer(BibJsonToBibliographyTransformer.builder().build())
        .addStep(BibliographyCapitalizeProcessor.builder().build())
        .addTailTransformer(BibliographyToBibJsonTransformer.builder().build())
        .build();
```

## Getting Started

### Including the maven dependency

The library is provided through the github maven repository. Include the following dependency
section in your pom file:

```
<repositories>
   <repository>
      <id>github</id>
      <name>GitHub Datarocks Apache Maven Packages</name>
      <url>https://maven.pkg.github.com/datarocks-ag/banzai</url>
   </repository>
</repositories>

<dependency>
    <groupId>org.datarocks</groupId>
    <artifactId>banzai</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Pipeline creation and usage

The following code snippets show how to create a functional pipeline.

#### Handler Configuration

The required parameters for the handlers (transformers, processors) are supplied by
the `HandlerConfiguration`.

###### Create HandlerConfiguration

Finally, create the `HandlerConfiguration` object:

```
HandlerConfiguration handlerConfiguration = 
    HandlerConfiguration.builder()
        .handlerConfigurationItem("key", "value")
        .build();
```

### Building a simple PassThrough pipeline

```
PipeLine<Object, Object, Object> ipeline = 
    PipeLine.builder(handlerConfiguration, Object.class, Object.class, Object.class)
        .addHeadTransformer(PassTroughTransformer.<Object>builder().build())
        .addStep(PassThroughProcessor.builder().build())
        .addTailTransformer(PassTroughTransformer.<Object>builder().build())
        .build()
```

A fully working minimal implementation can be found in
section [Working Minimal Implementation](#working-minimal-implementation).

## Components Description

### Transformers

#### PassTroughTransformer

Dummy transformer that does no conversion and just returns input object.

### Processors

#### PassTroughProcessor

Dummy processor that does no conversion and just returns input object.

## Working Minimal Implementation
A working minimal implementation cab be found under [BibJsonPipe](src/main/java/org/datarocks/banzai/examples/bibjsonpipe)