package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class ButtNameResponseHandler {

    private IRCbutt butt;

    public ButtNameResponseHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public void buttRespond(GenericMessageEvent event) {
        int random = (int) (Math.random()*10);
        switch (random) {
            case 0:
                butt.getButtChatHandler().buttChat(event, "yes butt here");
                break;
            case 1:
                butt.getButtChatHandler().buttChat(event, "butt never was too great at mc");
                break;
            case 2:
                butt.getButtChatHandler().buttChat(event, "butt never eatin blazin wing again");
                break;
            case 3:
                butt.getButtChatHandler().buttChat(event, "tryin to code....butt not havin good luck here");
                break;
            case 4:
                butt.getButtChatHandler().buttChat(event, "hello " + event.getUser().getNick().toLowerCase());
                butt.getButtChatHandler().buttChat(event, "how r u?");
                break;
            case 5:
                butt.getButtChatHandler().buttChat(event, "how r u " + event.getUser().getNick().toLowerCase());
                break;
            case 6:
                butt.getButtChatHandler().buttChat(event, "butt have very fast connection :)");
                break;
            case 7:
                butt.getButtChatHandler().buttChat(event, "hey mate");
                break;
            case 8:
                butt.getButtChatHandler().buttChat(event, "butt poopin brb k");
                break;
            case 9:
                butt.getButtChatHandler().buttChat(event, "hey");
                break;
            case 10:
                butt.getButtChatHandler().buttChat(event, "butt use to play mc a lot");
                break;
            default:
                break;
        }
    }

}
