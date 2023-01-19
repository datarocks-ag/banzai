package org.datarocks.banzai.examples.bibjsonpipe;

import java.util.UUID;
import org.datarocks.banzai.configuration.HandlerConfiguration;
import org.datarocks.banzai.pipeline.PipeLine;

public class BibJsonPipeline {
  private final PipeLine<String, Bibliography, String> pipeLine;

  public BibJsonPipeline() {
    pipeLine =
        PipeLine.builder(
                HandlerConfiguration.builder().build(),
                String.class,
                Bibliography.class,
                String.class)
            .addHeadTransformer(BibJsonToBibliographyTransformer.builder().build())
            .addStep(BibliographyCapitalizeProcessor.builder().build())
            .addTailTransformer(BibliographyToBibJsonTransformer.builder().build())
            .build();
  }

  public String processBibJson(String bibJson) {
    return pipeLine.process(UUID.randomUUID().toString(), bibJson);
  }
}
