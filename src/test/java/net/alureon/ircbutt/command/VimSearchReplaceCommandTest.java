package net.alureon.ircbutt.command;

import net.alureon.ircbutt.command.commands.VimSearchReplaceCommand;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A test of Vim's search and replace functionality.
 */
public final class VimSearchReplaceCommandTest {

    /**
     * A test of the Vim search and replace functionality.
     */
    @Test
    public void testSearchAndReplace() {
        String lastMessage = "apparently at like 2 AM i put like $350 work of lockpicking tools in my cart.";
        String command = "s/work/worth/";
        String replaced = new VimSearchReplaceCommand().searchAndReplace(command, lastMessage);
        Assert.assertEquals("apparently at like 2 AM i put like $350 worth of lockpicking tools in my cart.",
                replaced);
        String lastMessage2 = "i has a lot of mistakes when i has buttbutt";
        String command2 = "s/has/made/g";
        String command3 = "s/has/made/";
        String replaced2 = new VimSearchReplaceCommand().searchAndReplace(command2, lastMessage2);
        String replaced3 = new VimSearchReplaceCommand().searchAndReplace(command3, lastMessage2);
        Assert.assertEquals("i made a lot of mistakes when i made buttbutt", replaced2);
        Assert.assertEquals("i made a lot of mistakes when i has buttbutt", replaced3);

        Pattern p = Pattern.compile("s/.*/.*/g?");
        Matcher m = p.matcher(command);
        Assert.assertTrue(m.find());
        Matcher m2 = p.matcher(command2);
        Assert.assertTrue(m2.find());
        Matcher m3 = p.matcher("something the shouldn't match");
        Assert.assertFalse(m3.find());
    }
}
