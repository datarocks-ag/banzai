package org.datarocks.banzai.examples.bibjsonpipe;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Bibliography {
  String title;
  List<Author> author;
  String type;
  String year;
  Journal journal;
  List<Link> link;
  List<Identifier> identifier;
}
