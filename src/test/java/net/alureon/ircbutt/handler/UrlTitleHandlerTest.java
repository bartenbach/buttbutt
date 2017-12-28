package net.alureon.ircbutt.handler;

import org.junit.Assert;
import org.junit.Test;

/**
 * A quick and dirty test for URL titles.
 */
public final class UrlTitleHandlerTest {

    /**
     * This does nothing actually.
     */
    @Test
    public void testGetYouTubeUrl() {
        String url = "https://www.youtube.com/watch?v=Nlm-zuOx6Kk";
        String title = UrlTitleHandler.getTitle(url);
        Assert.assertEquals("[590] LoboJack HS-21 Padlock Opened With Slide Hammer", title);
    }
}
