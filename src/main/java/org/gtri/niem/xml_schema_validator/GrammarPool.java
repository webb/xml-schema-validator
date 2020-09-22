
package org.gtri.niem.xml_schema_validator;

import org.apache.xerces.impl.xs.util.XSGrammarPool;
import org.apache.xerces.xni.grammars.Grammar;

public class GrammarPool
    extends XSGrammarPool {
  public void
  cacheGrammars(
      String grammarType,
      Grammar[] grammars) {
    Logger.getInstance().trace("in {}.{}({}, {})", getClass(), "cacheGrammars", grammarType, grammars);
  }

}
