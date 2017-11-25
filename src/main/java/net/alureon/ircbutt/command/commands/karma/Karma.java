package net.alureon.ircbutt.command.commands.karma;

/**
 * Class representing an object's name, it's Karma level, and the operation we're performing on its Karma level.
 */
class Karma {

    /**
     * The type of Karma operation this is.  Could be INCREMENT or DECREMENT.
     */
    private KarmaType type;
    /**
     * The item this Karma is referring to.
     */
    private String item;

    /**
     * Sets the type of this Karma operation.
     * @param type The corresponding KarmaType (INCREMENT/DECREMENT)
     */
    void setType(final KarmaType type) {
        this.type = type;
    }

    /**
     * Sets the item this Karma operation pertains to.
     * @param item The item that this Karma operation pertains to.
     */
    void setItem(final String item) {
        this.item = item;
    }

    /**
     * Returns the type of Karma operation taking place (INCREMENT/DECREMENT).
     * @return The KarmaType pertaining to this operation.
     */
    KarmaType getType() {
        return this.type;
    }

    /**
     * Return's the item pertaining to this Karma operation.
     * @return The item pertaining to this Karma operation.
     */
    String getItem() {
        return this.item;
    }
}
