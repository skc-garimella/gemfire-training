package portableDataExchange.serializer;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializer;
import com.gemstone.gemfire.pdx.PdxWriter;

/**
 * An example of a PdxSerializer. This serializer handles PortfolioPdx and
 * PositionPdx objects by delegating to serializers for those individual object
 * types.
 * </p>
 * 
 * @author GemStone Systems, Inc.
 * @since 6.6
 */
public class ExamplePdxSerializer implements PdxSerializer {
  
  private final PdxSerializer portfolioSerializer = new PortfolioSerializer();
  private final PdxSerializer positionSerializer = new PositionSerializer();

  /**
   * Serialize the given object using the PdxWriter if possible.
   */
  @Override
  public boolean toData(Object o, PdxWriter out) {
    //handle PortfolioPdx objects
    if (o instanceof PortfolioPdx) {
      return portfolioSerializer.toData(o, out);
    }
    
    //handle PositionPdx objects
    if (o instanceof PositionPdx) {
      return positionSerializer.toData(o, out);
    }
    
    //If the object type is something we don't understand, return false
    //to allow the serialization framework to try to serialize the object
    //using java serialization
    return false;
  }

  @Override
  public Object fromData(Class<?> clazz, PdxReader in) {
    //handle PortfolioPdx objects
    if (clazz.equals(PortfolioPdx.class)) {
      return portfolioSerializer.fromData(clazz, in);
    }
    
    //handle PositionPdx objects
    if (clazz.equals(PositionPdx.class)) {
      return positionSerializer.fromData(clazz, in);
    }

    //If the object type is something we don't understand, return null
    return null;
  }
}
