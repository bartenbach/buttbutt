package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;

/**
 * Created by alureon on 5/13/15.
 */

public class MoreHandler {


    private String more;
    private String more2;
    private String more3;


    public void setMore(String more) {
        this.more = more;
    }

    public void setMore2(String more) {
        this.more2 = more;
    }

    public void setMore3(String more) {
        this.more3 = more;
    }

    public void handleMore(BotResponse response) {
        if (this.more != null) {
            response.chat(this.more);
            this.more = null;
        } else {
            if (this.more2 != null) {
                response.chat(this.more2);
                this.more2 = null;
            } else {
                if (this.more3 != null) {
                    response.chat(this.more3);
                    this.more3 = null;
                }
            }
        }
    }

    public void clearMore() {
        this.more = null;
        this.more2 = null;
        this.more3 = null;
    }

}
