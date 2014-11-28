package net.alureon.ircbutt.handler;

import net.alureon.ircbutt.IRCbutt;
import org.pircbotx.User;

public class ButtNameResponseHandler {

    private IRCbutt butt;

    public ButtNameResponseHandler(IRCbutt butt) {
        this.butt = butt;
    }

    public String getButtRespose(User user) {
        String response = null;
        int random = (int) (Math.random()*10);
        switch (random) {
            case 0:
                response = "yes butt here";
                break;
            case 1:
                response = "butt never was too great at mc";
                break;
            case 2:
                response = "butt never eatin blazin wing again...whew";
                break;
            case 3:
                response = "tryin to code....butt not havin good luck here...";
                break;
            case 4:
                response = "hello " + user.getNick().toLowerCase();
                break;
            case 5:
                response = "how r u " + user.getNick().toLowerCase();
                break;
            case 6:
                response = "butt have good net connection hbu";
                break;
            case 7:
                response = "hey mate";
                break;
            case 8:
                response = "butt poopin brb k";
                break;
            case 9:
                response = "butt never been much of conversationalist";
                break;
            case 10:
                response = "butt use to play mc a lot not no more tho";
                break;
            default:
                break;
        }
        return response;
    }

}
