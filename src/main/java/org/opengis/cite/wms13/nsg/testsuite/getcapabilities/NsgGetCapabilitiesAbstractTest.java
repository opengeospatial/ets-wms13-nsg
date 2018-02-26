package org.opengis.cite.wms13.nsg.testsuite.getcapabilities;

import static de.latlon.ets.wms13.core.domain.DGIWGWMS.GET_CAPABILITIES;
import static org.testng.Assert.assertTrue;

import java.net.URI;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import com.sun.jersey.api.client.ClientResponse;

import de.latlon.ets.wms13.core.dgiwg.testsuite.getcapabilities.AbstractBaseGetCapabilitiesFixture;
import de.latlon.ets.wms13.core.domain.ProtocolBinding;
import de.latlon.ets.wms13.core.util.ServiceMetadataUtils;

/**
 * Tests if the capabilities contains a valid value for Abstract.
 * 
 * @author Jim Beatty (modified/fixed 06-June-2017 for NSG tests) based on main class by:
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz</a>
 */
public class NsgGetCapabilitiesAbstractTest extends AbstractBaseGetCapabilitiesFixture {

    private static final String NSG_EXPECTED_ABSTRACT = "This service implements the NSG OGC WMS 1.3 profile version 2.0.";

    @Test(description = "NSG WMS Implementation Profile version 1.3.0, Requirement 2")
    public void wmsCapabilitiesAbstractContainsProfile( ITestContext testContext )
                            throws XPathFactoryConfigurationException, XPathExpressionException {
        URI endpoint = ServiceMetadataUtils.getOperationEndpoint( this.wmsCapabilities, GET_CAPABILITIES,
                                                                  ProtocolBinding.GET );
        ClientResponse rsp = wmsClient.submitRequest( this.reqEntity, endpoint );

        String abstractValue = parseAbstract( rsp );
        assertTrue( abstractValue.contains( NSG_EXPECTED_ABSTRACT ), "Abstract is not valid, must contain the string '"
                                                                     + NSG_EXPECTED_ABSTRACT + " but is '"
                                                                     + abstractValue + "'" );
    }

    private String parseAbstract( ClientResponse rsp )
                            throws XPathFactoryConfigurationException, XPathExpressionException {
        String xPathAbstract = "//wms:WMS_Capabilities/wms:Service/wms:Abstract";
        XPathFactory factory = XPathFactory.newInstance( XPathConstants.DOM_OBJECT_MODEL );
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext( NS_BINDINGS );
        return (String) xpath.evaluate( xPathAbstract, rsp.getEntity( Document.class ), XPathConstants.STRING );
    }

}