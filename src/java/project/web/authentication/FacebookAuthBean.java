package project.web.authentication;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Session;
import project.system.User;
import project.system.authentication.LoginFailedException;
import project.system.authentication.LogoutFailedException;
import project.system.authentication.NotLoggedInException;
import project.system.authentication.facebook.FacebookAuth;
import project.system.authentication.facebook.FacebookAuthChannel;
import project.web.ProjectBean;
import project.web.SessionBean;

/**
 * Bean responsible for managing facebook authentication.
 *
 * @author felipe
 */
@Named("faceBean")
@SessionScoped
public class FacebookAuthBean implements Serializable {

    @Inject
    private ProjectBean projectBean;
    @Inject
    private SessionBean sessionBean;
    private String faceID = "";
    private String authToken = "";
    private String faceName = "";
    private String faceEmail = "";

    /**
     * Returns the face ID.
     *
     * @return the face ID
     */
    public String getFaceID() {
        return faceID;
    }

    /**
     * Sets the face ID.
     *
     * @param faceID the new face ID
     */
    public void setFaceID(String faceID) {
        if (faceID == null) {
            return;
        }
        this.faceID = faceID;
        if (isFilled()) {
            login();
        }
    }

    /**
     * Returns the facebook auth token.
     *
     * @return the facebook auth token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the facebook auth token.
     *
     * @param authToken the new facebook auth token
     */
    public void setAuthToken(String authToken) {
        if (authToken == null) {
            return;
        }
        this.authToken = authToken;
        if (isFilled()) {
            login();
        }
    }

    /**
     * Returns the facebook account name.
     *
     * @return the facebook account name
     */
    public String getFaceName() {
        return faceName;
    }

    /**
     * Sets the facebook account name.
     *
     * @param faceName the account name
     */
    public void setFaceName(String faceName) {
        if (faceName == null) {
            return;
        }
        this.faceName = faceName;
        if (isFilled()) {
            login();
        }
    }

    /**
     * Returns the facebook account contact email.
     *
     * @return the face email
     */
    public String getFaceEmail() {
        return faceEmail;
    }

    /**
     * Sets the facebook account contact email.
     * 
     * @param faceEmail the new face email
     */
    public void setFaceEmail(String faceEmail) {
        if (faceEmail == null) {
            return;
        }
        this.faceEmail = faceEmail;
        if (isFilled()) {
            login();
        }
    }

    private void login() {
        FacebookAuth auth = FacebookAuth.getInstance();
        if (!auth.isRegistered(faceID)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("signup.xhtml?signup-type=facebook");
            } catch (IOException ex) {
            }
            return;
        }

        User user = auth.getUser(faceID);
        FacebookAuthChannel channel = auth.getChannel(user);

        try {
            channel.enterFaceid(faceID);
            channel.enterInputAuthToken(authToken);

            channel.login();

            Session session = projectBean.getProject().openNewSession(user, channel);
            sessionBean.setSession(session);

            FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");
        } catch (LoginFailedException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login pelo Facebook falhou!", null));
        } catch (IOException ex) {
            try {
                channel.logout();
            } catch (LogoutFailedException ex1) {
            }
        } catch (NotLoggedInException ex) {
            Logger.getLogger(FacebookAuthBean.class.getName()).log(Level.SEVERE, "Unexpected error!", ex);
        }
    }

    /**
     * Check if all form fields are filled.
     * 
     * @return if all form fields are filled
     */
    private boolean isFilled() {
        return !faceID.isEmpty() && !authToken.isEmpty()
                && !faceName.isEmpty() && !faceEmail.isEmpty();
    }
}
