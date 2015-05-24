package net.alureon.ircbutt;

/**
 * Created by alureon on 5/24/15.
 */

public class Karma {

    private KarmaType type;
    private String item;
    private int karmaLevel;

    public void setType(KarmaType type) {
        this.type = type;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setKarma(int karma) {
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
