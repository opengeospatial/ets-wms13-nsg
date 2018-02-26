package org.opengis.cite.wms13.nsg.keyword;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import de.latlon.ets.core.keyword.DfddKeywordMatcher;

/**
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz </a>
 */
public class NsgDfddKeywordMatcherFromFileTest {

    private DfddKeywordMatcher dfddKeywordMatcher = new NsgDfddKeywordMatcherFromFile();

    @Test
    public void testContainsAtLeastOneDfddKeyword_emptyList() {
        List<String> keywords = Collections.emptyList();
        boolean containsAtLeastOneDfddKeyword = dfddKeywordMatcher.containsAtLeastOneDfddKeyword( keywords );

        assertThat( containsAtLeastOneDfddKeyword, is( false ) );
    }

    @Test
    public void testContainsAtLeastOneDfddKeyword_listContainingOneDfddKeyword() {
        List<String> keywords = Collections.singletonList( "Cultural" );
        boolean containsAtLeastOneDfddKeyword = dfddKeywordMatcher.containsAtLeastOneDfddKeyword( keywords );

        assertThat( containsAtLeastOneDfddKeyword, is( true ) );
    }

    @Test
    public void testContainsAtLeastOneDfddKeyword_listContainingTwoDfddOneOtherKeywords() {
        List<String> keywords = Arrays.asList( "other", "Elevation", "Maritime Limits" );
        boolean containsAtLeastOneDfddKeyword = dfddKeywordMatcher.containsAtLeastOneDfddKeyword( keywords );

        assertThat( containsAtLeastOneDfddKeyword, is( true ) );
    }

    @Test
    public void testContainsAtLeastOneDfddKeyword_listContainingTwoOtherKeywords() {
        List<String> keywords = Arrays.asList( "other1", "other2" );
        boolean containsAtLeastOneDfddKeyword = dfddKeywordMatcher.containsAtLeastOneDfddKeyword( keywords );

        assertThat( containsAtLeastOneDfddKeyword, is( false ) );
    }

}
