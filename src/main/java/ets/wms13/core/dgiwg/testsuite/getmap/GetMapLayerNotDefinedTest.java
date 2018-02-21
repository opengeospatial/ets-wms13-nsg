package ets.wms13.core.dgiwg.testsuite.getmap;

import static de.latlon.ets.core.assertion.ETSAssert.assertStatusCode;
import static de.latlon.ets.core.assertion.ETSAssert.assertXPath;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.EXCEPTIONS_PARAM;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.FORMAT_PARAM;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.GET_MAP;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.IMAGE_PNG;
import static de.latlon.ets.wms13.core.domain.DGIWGWMS.LAYERS_PARAM;
import static de.latlon.ets.wms13.core.util.request.WmsRequestBuilder.getSupportedFormat;
import static org.testng.Assert.assertTrue;

import java.net.URI;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.jersey.api.client.ClientResponse;

import de.latlon.ets.core.error.ErrorMessage;
import de.latlon.ets.core.error.ErrorMessageKey;
import de.latlon.ets.wms13.core.dgiwg.testsuite.getmap.BaseGetMapFixture;
import de.latlon.ets.wms13.core.domain.ProtocolBinding;
import de.latlon.ets.wms13.core.util.ServiceMetadataUtils;

/**
 * Tests if the all GetMap request parameters are supported.
 * 
 * @author Jim Beatty (modified/fixed 06-June-2017), by extension using lower-level fix
 */
public class GetMapLayerNotDefinedTest extends BaseGetMapFixture {

    @BeforeMethod
    public void clearRequest() {
        this.reqEntity.removeKvp( FORMAT_PARAM );
        this.reqEntity.removeKvp( LAYERS_PARAM );
        this.reqEntity.removeKvp( EXCEPTIONS_PARAM );
    }

    @Test(description = "DGIWG - Web Map Service 1.3 Profile, 6.6.3., S.19, Requirement 25_26")
    public void wmsGetMapLayerNotDefined( ITestContext testContext )
                            throws XPathExpressionException, XPathFactoryConfigurationException {
        URI endpoint = ServiceMetadataUtils.getOperationEndpoint( this.wmsCapabilities, GET_MAP, ProtocolBinding.GET );

        String requestFormat = null;
        if ( ServiceMetadataUtils.parseSupportedFormats( wmsCapabilities, GET_MAP ).contains( IMAGE_PNG ) )
            requestFormat = IMAGE_PNG;
        else
            requestFormat = getSupportedFormat( wmsCapabilities, GET_MAP );

        this.reqEntity.addKvp( FORMAT_PARAM, requestFormat );

        this.reqEntity.addKvp( LAYERS_PARAM, "LayerNotDefined" );
        this.reqEntity.addKvp( EXCEPTIONS_PARAM, "XML" );

        ClientResponse rsp = wmsClient.submitRequest( this.reqEntity, endpoint );

        // storeResponseImage( rsp, "Requirement25_26", "simple", requestFormat
        // );

        assertTrue( rsp.hasEntity(), ErrorMessage.get( ErrorMessageKey.MISSING_XML_ENTITY ) );
        assertStatusCode( rsp.getStatus(), 200 );

        Document entity = rsp.getEntity( Document.class );
        // assertSimpleWMSCapabilities( entity );

        String xPathXml = "//*[name() = 'ServiceExceptionReport']";
        assertXPath( xPathXml, entity, NS_BINDINGS );
        Node node = (Node) createXPath().evaluate( xPathXml, entity, XPathConstants.NODE );

        boolean returnedOK = false;

        NodeList childNodes = (NodeList) node.getChildNodes();
        for ( int childIndx = 0; childIndx < childNodes.getLength(); childIndx++ ) {
            Node node2 = (Node) childNodes.item( childIndx );

            if ( node2.getNodeName().equals( "ServiceException" ) ) {
                NamedNodeMap nm = node2.getAttributes();
                for ( int attrIndx = 0; attrIndx < nm.getLength(); attrIndx++ ) {
                    Attr attr = (Attr) nm.item( attrIndx );
                    String attrName = attr.getName().toLowerCase();
                    String attrValue = attr.getValue();

                    if ( attrName.equals( "code" ) && attrValue.equals( "LayerNotDefined" ) ) {
                        returnedOK = true;
                        break;
                    }
                }
                break;
            }
        }
        assertTrue( returnedOK, "Results of didnot return 'LayerNotDefined'" );
    }

    private XPath createXPath()
                            throws XPathFactoryConfigurationException {
        XPathFactory factory = XPathFactory.newInstance( XPathConstants.DOM_OBJECT_MODEL );
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext( NS_BINDINGS );
        return xpath;
    }

}