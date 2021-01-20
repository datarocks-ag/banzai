package org.datarocks.banzai.examples.bibjsonpipe;

import com.google.gson.Gson;
import lombok.experimental.SuperBuilder;
import org.datarocks.banzai.transformer.AbstractTransformer;

@SuperBuilder
public class BibliographyToBibJsonTransformer extends AbstractTransformer<Bibliography, String> {
  @Override
  public String processImpl(String correlationId, Bibliography input) {
    Gson gson = new Gson();
    return gson.toJson(input);
  }
}
