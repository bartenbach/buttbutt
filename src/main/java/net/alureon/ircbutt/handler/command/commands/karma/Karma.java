package net.alureon.ircbutt.handler.command.commands.karma;

class Karma {

    private KarmaType type;
    private String item;
    private int karmaLevel;

    void setType(KarmaType type) {
        this.type = type;
    }

    void setItem(String item) {
        this.item = item;
    }

    protected void setKarmaLevel(int karma) {
        this.karmaLevel = karma;
    }

    KarmaType getType() {
        return this.type;
    }

    String getItem() {
        return this.item;
    }

    public int getKarmaLevel() {
        return this.karmaLevel;
    }
}
