package org.opengis.cite.wms13.nsg.testsuite.getcapabilities;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.opengis.cite.wms13.nsg.keyword.NsgDfddKeywordMatcherFromFile;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.latlon.ets.core.keyword.DfddKeywordMatcher;
import de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.AbstractBaseGetCapabilitiesFixture;

/**
 * Tests if the service contains at least one expected keywords.
 * 
 * @author Jim Beatty (modified/fixed 06-June-2017 for NSG tests) based on main class by:
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class NsgGetCapabilitiesKeywordTest extends AbstractBaseGetCapabilitiesFixture {

    private static final DfddKeywordMatcher KEYWORD_MATCHER = new NsgDfddKeywordMatcherFromFile();

    @Test(description = "NSG WMS Implementation Profile version 1.3.0, Requirement 3")
    public void wmsCapabilitiesContainsKeywordFromNASRegister()
                            throws XPathFactoryConfigurationException, XPathExpressionException {
        List<String> keywords = parseKeywords( wmsCapabilities );
        boolean atLeastOneKeywordIsFromDfdd = KEYWORD_MATCHER.containsAtLeastOneDfddKeyword( keywords );

        assertTrue( atLeastOneKeywordIsFromDfdd,
                    "Invalid keywords, expected is at least one keyword from DFDD, but is " + keywords );
    }

    private List<String> parseKeywords( Document wmsCapabilities )
                            throws XPathFactoryConfigurationException, XPathExpressionException {
        String xPathExpr = "//wms:WMS_Capabilities/wms:Service/wms:KeywordList/wms:Keyword";
        NodeList keywordNodes = (NodeList) createXPath().evaluate( xPathExpr, wmsCapabilities, XPathConstants.NODESET );
        List<String> keywords = new ArrayList<String>();
        for ( int keywordNodeIndex = 0; keywordNodeIndex < keywordNodes.getLength(); keywordNodeIndex++ ) {
            Node keywordNode = keywordNodes.item( keywordNodeIndex );
            String keyword = keywordNode.getTextContent();
            if ( keyword != null )
                keywords.add( keyword.trim() );
        }
        return keywords;
    }

    private XPath createXPath()
                            throws XPathFactoryConfigurationException {
        XPathFactory factory = XPathFactory.newInstance( XPathConstants.DOM_OBJECT_MODEL );
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext( NS_BINDINGS );
        return xpath;
    }

}