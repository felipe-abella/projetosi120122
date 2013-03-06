package project.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import project.system.Link;
import project.system.SimpleDate;
import project.system.Sound;

@Named("soundBean")
@SessionScoped
public class SoundBean implements Serializable {
    private Sound sound;
    
    public SoundBean() {
        throw new IllegalStateException("This is not meant to be called");
    }

    public SoundBean(Sound sound) {
        this.sound = sound;
    }
    
    public Link getLink() {
        return sound.getLink();
    }
    
    public SimpleDate getCreationDate() {
        return sound.getCreationDate();
    }
    
    public UserBean getAuthor() {
        return new UserBean(sound.getAuthor());
    }
}
