package ets.wms13.core.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.latlon.ets.wms13.core.domain.DGIWGWMS;

/**
 * Encapsulates the parameters of a KVP request (GET).
 * 
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 * @author Jim Beatty (modified/fixed 24-May-2017)
 */
public class WmsKvpRequest_FIX
{

    private final Map<String, String> kvps = new HashMap<String, String>();

    /**
     * Add a new key value pair. If the key already exists the old value will be overwritten. If the key is
     * <code>null</code> the KVP will be ignored.
     * 
     * @param key
     *            never <code>null</code>
     * @param value
     *            may be <code>null</code>
     */
    public void addKvp( String key, String value ) 
    {
    	/* --- old code
        if ( key != null )
            kvps.put( key, encode( value ) );
        */
    	if ( key != null )
    	{
    		String encodedValue = encode(value);
    		if (( key == DGIWGWMS.LAYERS_PARAM ) || ( key == DGIWGWMS.QUERY_LAYERS_PARAM ))
    		{
    			if ( encodedValue.contains("+") && value.contains(" "))
    			{
    				String tmpStr = value;
    				encodedValue = "";
    				while ( tmpStr.length() > 0 )
    				{
    					int indx = tmpStr.indexOf(' ');
    					if ( indx < 0 ) 
    						indx = tmpStr.length();
    					if (encodedValue.length() > 0 )
    						encodedValue += "%20";		// --- ASCII space character equivalent
    					encodedValue += encode(tmpStr.substring(0, indx));
    					if ( indx+1 <= tmpStr.length())
    						tmpStr = tmpStr.substring(indx+1);
    					else
    						tmpStr = "";
    				}    				
    			}        
    		}
    		kvps.put(key, encodedValue);
    	}
    }

    /**
     * @return the KVPs as query string (e.g. key1=value1&amp;key2=value2)
     */
    public String asQueryString() {
        StringBuilder sb = new StringBuilder();
        for ( Entry<String, String> kvp : kvps.entrySet() ) {
            if ( sb.length() > 1 )
                sb.append( '&' );
            sb.append( kvp.getKey() ).append( '=' ).append( kvp.getValue() );
        }
        return sb.toString();
    }

    /**
     * Removes the KVP with the passed key, if existing.
     * 
     * @param key
     *            of the KVP to remove, may be <code>null</code> (nothing happens)
     */
    public void removeKvp( String key ) {
        kvps.remove( key );
    }

    private String encode( String value ) {
        try {
            return URLEncoder.encode( value, "UTF-8" );
        } catch ( UnsupportedEncodingException e ) {
            // UTF-8 should be available
        }
        return value;
    }

}