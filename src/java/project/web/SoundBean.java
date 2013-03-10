package project.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import project.system.Link;
import project.system.SimpleDate;
import project.system.Sound;

/**
 * Bean responsible for encapsulating a Sound.
 *
 * This bean will be created by another beans (for example, returned in the feed
 * list) and shouldn't be instantiated with the default constructor.
 */
@Named("soundBean")
@SessionScoped
public class SoundBean implements Serializable {

    private Sound sound;

    /**
     * This constructor shouldn't be used.
     *
     * @throws IllegalStateException always
     */
    public SoundBean() {
        throw new IllegalStateException("This is not meant to be called");
    }

    /**
     * Constructs a new SoundBean.
     *
     * @param sound Sound to encapsulate
     */
    public SoundBean(Sound sound) {
        this.sound = sound;
    }

    /**
     * Returns the sound's link.
     *
     * @return the sound's link
     */
    public Link getLink() {
        return sound.getLink();
    }

    /**
     * Returns the sound's creation date
     *
     * @return the sound's creation date
     */
    public SimpleDate getCreationDate() {
        return sound.getCreationDate();
    }

    /**
     * Returns the sound's author
     *
     * @return the sound's author
     */
    public UserBean getAuthor() {
        return new UserBean(sound.getAuthor());
    }

    /**
     * Returns the encapsulated sound.
     *
     * @return the sound
     */
    public Sound getSound() {
        return sound;
    }
}
