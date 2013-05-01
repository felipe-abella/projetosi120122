package project.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import project.system.Link;
import project.system.SimpleDate;
import project.system.Sound;
import project.system.Tag;
import project.system.User;

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

    /**
     * Returns a list with all tags associated to this sound.
     *
     * @return a list with the name of the tags
     */
    public List<String> getTagNameList() {
        List<String> result = new ArrayList<String>();

        if (sound != null) {
            /* My sincere apologies for this */
            for (Tag tag : sound.getAuthor().getTagList()) {
                if (tag.getSoundList().contains(sound))
                    result.add(tag.getName());
            }
        }

        return result;
    }

    public void tagSelect(SelectEvent se) {
        System.out.println("SE");
    }
    
    public void tagUnselect(UnselectEvent ue) {
        System.out.println("UE");
    }
    
    public void setTagNameList(List<String> tagNames) {
        User author = sound.getAuthor();
        
        System.out.println("SUBMIT");
        
        /* My sincere apologies for this */
        for (Tag tag : author.getTagList()) {
            tag.getSoundList().remove(sound);
        }
        
        for (String tagName : tagNames) {
            Tag tag = author.getTagWithName(tagName);
            if (tag == null)
                tag = author.addTag(tagName);
            
            tag.getSoundList().add(sound);
        }
    }

    public List<String> tagNameListComplete(String prefix) {
        List<String> ret = new ArrayList<String>();
        ret.add(prefix);
        for (Tag tag : sound.getAuthor().getTagList()) {
            if (!tag.getName().equals(prefix)) {
                ret.add(tag.getName());
            }
        }
        return ret;
    }
}
