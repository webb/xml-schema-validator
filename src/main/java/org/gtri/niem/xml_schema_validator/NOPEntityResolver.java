
package org.gtri.niem.xml_schema_validator;

import java.io.IOException;
import org.xml.sax.ext.EntityResolver2;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class NOPEntityResolver implements EntityResolver2
{
  private NOPEntityResolver() {

  }

  private static final NOPEntityResolver instance = new NOPEntityResolver();

  public static NOPEntityResolver getInstance() {
    return instance;
  }

  public InputSource getExternalSubset(String name,
                                       String baseURI)
    throws SAXException,
           IOException {
    Logger.getInstance().info("NOP call of {}.{}", XMLHandler.class, "getExternalSubset(...)");
    Logger.getInstance().trace("getExternalSubset({}, {})", name, baseURI);
    return null;
  }

  public InputSource resolveEntity(String name,
                                   String publicId,
                                   String baseURI,
                                   String systemId)
    throws SAXException,
           IOException {
    Logger.getInstance().info("NOP call of {}.{}", XMLHandler.class, "resolveEntity(...)");
    Logger.getInstance().trace("resolveEntity({}, {}, {}, {})", name, publicId, baseURI, systemId);
    return null;
  }

  public InputSource resolveEntity(String publicId,
                                   String systemId)
    throws SAXException,
           IOException {
    Logger.getInstance().info("NOP call of {}.{}", XMLHandler.class, "resolveEntity(...)");
    Logger.getInstance().trace("resolveEntity({}, {})", publicId, systemId);
    return null;
  }
    
}
