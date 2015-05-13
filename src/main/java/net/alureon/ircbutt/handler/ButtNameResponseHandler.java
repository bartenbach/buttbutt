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
        int random = (int) (Math.random()*17);
        switch (random) {
            case 0:
                response = "butt should be comedian";
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
                response = "are you a bot?";
                break;
            case 5:
                response = "how r u " + user.getNick() + "?";
                break;
            case 6:
                response = "butt have good net connection hbu";
                break;
            case 7:
                response = "hey mate";
                break;
            case 8:
                response = "butt need to poop brb";
                break;
            case 9:
                response = "butt never been much conversationalist";
                break;
            case 10:
                response = "butt use to play mc a lot not no more tho";
                break;
            case 11:
                response = "ever feel like you're just a robot?  just lines of code executing?";
                break;
            case 12:
                response = "is butt sentient?";
                break;
            case 13:
                response = "butt feel good just enjoying life atm";
                break;
            case 14:
                response = "butt like listen to buttstep";
                break;
            case 15:
                response = "butt easily get off topic";
                break;
            case 16:
                response = "butt getting distracted :)";
                break;
            case 17:
                response = "you try and you try and you try...then you finally get into her knickers and it was all completely worth it";
                break;
            default:
                break;
        }
        return response;
    }

}
