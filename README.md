# Benzai

The person data processor is part of the Search-Index-Client for the project **Landesweite
Grundstücksuche (LWGS)**.

The search-index-client offers different integration scenarios:

1. Integration over the rest interface of the search-index-client-service (with integrated
   person-data-processor).
2. Direct integration of the person-data-processor java library into an existing application.

More details on the different integration scenarios can be found in
the [search-index-client repository]().

This documentation explains the direct integration of the person-data-processor library.

## System Architecture

### Pipeline Concept

The person data processor library is implemented as an extendable pipeline of processors. The
pipeline can easily be extended with additional processors if required:

![Abstract Pipeline](assets/abstract-pipeline.png)

The pipeline starts with a transformer which converts the input data into the data format consumed
by the processors, and ends with a tail transformer which converts the processor format into the
required output format.

## Getting Started

### Including the maven dependency

The library is provided through the github maven repository. Include the following dependency
section in your pom file:

```
<repositories>
   <repository>
      <id>github</id>
      <name>GitHub Datarocks Apache Maven Packages</name>
      <url>https://maven.pkg.github.com/datarocks-ag/benzai</url>
   </repository>
</repositories>

<dependency>
    <groupId>org.datarocks</groupId>
    <artifactId>benzai</artifactId>
    <version>1.0</version>
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