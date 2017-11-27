package net.alureon.ircbutt.command;

import com.google.common.collect.ImmutableSortedSet;
import net.alureon.ircbutt.IRCbuttTest;
import net.alureon.ircbutt.response.BotResponse;
import org.junit.Test;
import org.mockito.Mockito;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * Tests for the give command.
 */
public final class GiveCommandTest {

    /**
     * Test to see if the give command works as expected.
     */
    @Test
    public void testGiveHandler() {
        String command = "!give alureon $USER";
        GenericMessageEvent event = Mockito.mock(GenericMessageEvent.class);
        MessageEvent messageEvent = Mockito.mock(MessageEvent.class);
        User user = Mockito.mock(User.class);
        Mockito.when(event.getUser()).thenReturn(user);
        Mockito.when(event.getUser().getNick()).thenReturn("alureon");
        Channel channel = Mockito.mock(Channel.class);
        final ImmutableSortedSet<User> typicalGuava = ImmutableSortedSet.of(user);
        Mockito.when(messageEvent.getChannel()).thenReturn(channel);
        Mockito.when(messageEvent.getChannel().getUsers()).thenReturn(typicalGuava);

        //Mockito.when(IRCUtils.userIsInChannel(messageEvent, "alureon")).thenReturn(true);
        // TODO somehow we need to mock a channel of users because this does verification
        BotResponse response = IRCbuttTest.getCommandHandler().handleCommand(event, command);
        //System.out.println(response.getMessage());
        //Assert.assertEquals("alureon: alureon", response.getMessage());
        //System.out.println(response.getMessage());
    }
}
