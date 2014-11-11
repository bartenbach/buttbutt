package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.Channel;
import org.pircbotx.User;

public class ButtNameResponseHandler {

    private IRCbutt butt;

    public ButtNameResponseHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void buttRespond(Channel channel, User user) {
        int random = (int) (Math.random()*10);
        switch (random) {
            case 0:
                butt.getButtChatHandler().buttChat(channel, "yes butt here");
                break;
            case 1:
                butt.getButtChatHandler().buttChat(channel, "butt never was too great at mc");
                break;
            case 2:
                butt.getButtChatHandler().buttChat(channel, "butt never eatin blazin wing again...whew");
                break;
            case 3:
                butt.getButtChatHandler().buttChat(channel, "tryin to code....butt not havin good luck here...");
                break;
            case 4:
                butt.getButtChatHandler().buttChat(channel, "hello " + user.getNick().toLowerCase());
                butt.getButtChatHandler().buttChat(channel, "how r u?");
                break;
            case 5:
                butt.getButtChatHandler().buttChat(channel, "how r u " + user.getNick().toLowerCase());
                break;
            case 6:
                butt.getButtChatHandler().buttChat(channel, "butt have good net connection hbu");
                break;
            case 7:
                butt.getButtChatHandler().buttChat(channel, "hey mate");
                break;
            case 8:
                butt.getButtChatHandler().buttChat(channel, "butt poopin brb k");
                break;
            case 9:
                butt.getButtChatHandler().buttChat(channel, "hey");
                break;
            case 10:
                butt.getButtChatHandler().buttChat(channel, "butt use to play mc a lot not no more tho");
                break;
            default:
                break;
        }
    }

}
