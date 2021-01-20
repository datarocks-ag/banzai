package org.datarocks.banzai.examples.bibjsonpipe;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class BibJsonToBibliographyTransformerTest {

  private static final String BIB_JSON =
      "{\"title\":\"Open Bibliography for Science, Technology and Medicine\",\"author\":[{\"name\":\"Richard Jones\"},{\"name\":\"Mark MacGillivray\"},{\"name\":\"Peter Murray-Rust\"},{\"name\":\"Jim Pitman\"},{\"name\":\"Peter Sefton\"},{\"name\":\"Ben O'Steen\"},{\"name\":\"William Waites\"}],\"type\":\"article\",\"year\":\"2011\",\"journal\":{\"name\":\"Journal of Cheminformatics\"},\"link\":[{\"url\":\"http://www.jcheminf.com/content/3/1/47\"}],\"identifier\":[{\"type\":\"doi\",\"id\":\"10.1186/1758-2946-3-47\"}]}";

  @Test
  void testTransformation() {
    BibJsonToBibliographyTransformer transformer =
        BibJsonToBibliographyTransformer.builder().build();
    assertNotNull(transformer.process(UUID.randomUUID().toString(), BIB_JSON));
  }
}
