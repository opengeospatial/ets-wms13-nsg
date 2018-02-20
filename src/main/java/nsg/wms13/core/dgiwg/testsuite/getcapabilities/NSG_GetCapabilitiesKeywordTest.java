package nsg.wms13.core.dgiwg.testsuite.getcapabilities;

import static org.testng.Assert.assertTrue;

import java.util.List;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.annotations.Test;

import ets.wms13.core.dgiwg.testsuite.getcapabilities.GetCapabilitiesKeywordTest;

/**
 * Tests if the service contains at least one expected keywords.
 * 
 * @author Jim Beatty (modified/fixed 06-June-2017 for NSG tests) based on main class by:
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class NSG_GetCapabilitiesKeywordTest extends GetCapabilitiesKeywordTest 
{
	private static final String KEYWORD_FILE = "nsg.dfdd.keywords";
	
	
    @Test(description = "NSG WMS Implementation Profile version 1.3.0, Requirement 3")
    public void wmsCapabilitiesContainsKeywordFromNASRegister()
                    throws XPathFactoryConfigurationException, XPathExpressionException {
        List<String> keywords = parseKeywords( wmsCapabilities );

        assertTrue( containsAtLeastOneKeyword( keywords, KEYWORD_FILE ),
                    "Invalid keywords, expected is at least one keyword from DFDD, but is " + keywords );
    }

}