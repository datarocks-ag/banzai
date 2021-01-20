package org.datarocks.banzai.examples.bibjsonpipe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BibJsonPipelineTest {
  private static final String BIB_JSON_OUTPUT =
      "{\"title\":\"Open Bibliography for Science, Technology and Medicine\",\"author\":[{\"name\":\"RICHARD JONES\"},{\"name\":\"MARK MACGILLIVRAY\"},{\"name\":\"PETER MURRAY-RUST\"},{\"name\":\"JIM PITMAN\"},{\"name\":\"PETER SEFTON\"},{\"name\":\"WILLIAM WAITES\"}],\"type\":\"article\",\"year\":\"2011\",\"journal\":{\"name\":\"Journal of Cheminformatics\"},\"link\":[{\"url\":\"http://www.jcheminf.com/content/3/1/47\"}],\"identifier\":[{\"type\":\"doi\",\"id\":\"10.1186/1758-2946-3-47\"}]}";
  private static final String BIB_JSON_INPUT =
      "{\"title\":\"Open Bibliography for Science, Technology and Medicine\",\"author\":[{\"name\":\"Richard Jones\"},{\"name\":\"Mark MacGillivray\"},{\"name\":\"Peter Murray-Rust\"},{\"name\":\"Jim Pitman\"},{\"name\":\"Peter Sefton\"},{\"name\":\"William Waites\"}],\"type\":\"article\",\"year\":\"2011\",\"journal\":{\"name\":\"Journal of Cheminformatics\"},\"link\":[{\"url\":\"http://www.jcheminf.com/content/3/1/47\"}],\"identifier\":[{\"type\":\"doi\",\"id\":\"10.1186/1758-2946-3-47\"}]}";

  @Test
  void testPipeline() {
    BibJsonPipeline bibJsonPipeline = new BibJsonPipeline();
    String bibAfterProcessing = bibJsonPipeline.processBibJson(BIB_JSON_INPUT);
    assertEquals(BIB_JSON_OUTPUT, bibAfterProcessing);
  }
}
