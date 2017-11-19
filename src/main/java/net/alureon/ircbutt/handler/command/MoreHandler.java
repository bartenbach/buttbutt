package net.alureon.ircbutt.handler.command;

import net.alureon.ircbutt.BotResponse;

import java.util.ArrayList;

public class MoreHandler {


    private final ArrayList<String> more = new ArrayList<>();
    private static final String defaultNoMore = "butt don't have any more";
    private String noMore = defaultNoMore;

    public void addMore(String more) {
        this.more.add(more);
    }

    void setNoMore(String more) {
        this.noMore = more;
    }

    public void handleMore(BotResponse response) {
        if (!this.more.isEmpty()) {
            String e = this.more.get(0);
            this.more.remove(0);
            response.chat(e);
        } else {
            response.chat(this.noMore);
        }
    }

    public void clearMore() {
        this.more.clear();
        this.noMore = defaultNoMore;
    }

}
