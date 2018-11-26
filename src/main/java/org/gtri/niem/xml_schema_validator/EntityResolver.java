
package org.gtri.niem.xml_schema_validator;

import java.io.IOException;
import org.xml.sax.ext.EntityResolver2;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class EntityResolver
  implements EntityResolver2
{
  public InputSource getExternalSubset(String name,
                                       String baseURI)
    throws SAXException,
           IOException {
    Logger.getInstance().info("Call of {}.{}", XMLHandler.class, "getExternalSubset(...)");
    Logger.getInstance().trace("getExternalSubset({}, {})", name, baseURI);
    // TODO
    InputSource returnValue = super.getExternalSubset(name, baseURI);
    Logger.getInstance().trace("getExternalSubset yields {}", returnValue);
    return returnValue;
  }

  public InputSource resolveEntity(String name,
                                   String publicId,
                                   String baseURI,
                                   String systemId)
    throws SAXException,
           IOException {
    Logger.getInstance().info("Call of {}.{}", XMLHandler.class, "resolveEntity(...)");
    Logger.getInstance().trace("resolveEntity({}, {}, {}, {})", name, publicId, baseURI, systemId);
    // TODO
    InputSource returnValue = super.resolveEntity(name, publicId, baseURI, systemId);
    Logger.getInstance().trace("resolveEntity yields {}", returnValue);
    return returnValue;
  }

  public InputSource resolveEntity(String publicId,
                                   String systemId)
    throws SAXException,
           IOException {
    Logger.getInstance().info("Call of {}.{}", XMLHandler.class, "resolveEntity(...)");
    Logger.getInstance().trace("resolveEntity({}, {})", publicId, systemId);
    // TODO
    InputSource returnValue = super.resolveEntity(publicId, systemId);
    Logger.getInstance().trace("resolveEntity yields {}", returnValue);
    return returnValue;
  }

}
