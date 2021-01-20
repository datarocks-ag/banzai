package org.datarocks.banzai.examples.bibjsonpipe;

import java.util.Locale;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.datarocks.banzai.processor.AbstractSingleItemProcessor;

@SuperBuilder
public class BibliographyCapitalizeProcessor extends AbstractSingleItemProcessor<Bibliography> {

  @Override
  public Bibliography processImpl(
      @NonNull String correlationId, @NonNull Bibliography bibliography) {
    bibliography.setAuthor(
        bibliography.getAuthor().stream()
            .map(author -> new Author(author.name.toUpperCase(Locale.ROOT)))
            .collect(Collectors.toList()));
    return bibliography;
  }
}
