package project.system;

import java.util.ArrayList;
import java.util.List;

public class Tag {

    private User owner;
    private String name;
    private List<Sound> soundList;

    /**
     * Creates a new tag.
     *
     * @param name the new tag name
     */
    public Tag(User owner, String name) {
        this.owner = owner;
        this.name = name;
        soundList = new ArrayList<Sound>();
    }

    /**
     * Tag a sound.
     *
     * @param sound the sound to tag
     */
    public void addSound(Sound sound) {
        soundList.add(sound);
    }

    /**
     * Returns the tagged sound list.
     *
     * @return the sound list
     */
    public List<Sound> getSoundList() {
        return soundList;
    }

    /**
     * Returns the tag name.
     *
     * @return the tag name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the tag owner.
     *
     * @return the tag owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Returns a string representation of the tag.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "[Tag: " + name + " (Owner = " + owner + ")]";
    }
}
