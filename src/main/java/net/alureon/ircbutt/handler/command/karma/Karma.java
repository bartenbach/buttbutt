package net.alureon.ircbutt.handler.command.karma;

/**
 * Created by alureon on 5/24/15.
 */

public class Karma {

    private KarmaType type;
    private String item;
    private int karmaLevel;

    protected void setType(KarmaType type) {
        this.type = type;
    }

    protected void setItem(String item) {
        this.item = item;
    }

    protected void setKarmaLevel(int karma) {
        this.karmaLevel = karma;
    }

    public KarmaType getType() {
        return this.type;
    }

    public String getItem() {
        return this.item;
    }

    public int getKarmaLevel() {
        return this.karmaLevel;
    }
}
