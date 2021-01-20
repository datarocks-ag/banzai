package org.datarocks.banzai.examples.bibjsonpipe;

import com.google.gson.Gson;
import lombok.experimental.SuperBuilder;
import org.datarocks.banzai.transformer.AbstractTransformer;

@SuperBuilder
public class BibJsonToBibliographyTransformer extends AbstractTransformer<String, Bibliography> {
  @Override
  public Bibliography processImpl(String correlationId, String input) {
    Gson gson = new Gson();
    return gson.fromJson(input, Bibliography.class);
  }
}
