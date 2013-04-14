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

    public String getFaceID() {
        return faceID;
    }

    public void setFaceID(String faceID) {
        if (faceID == null) {
            return;
        }
        this.faceID = faceID;
        System.out.println("FaceID: " + faceID);
        if (isFilled()) {
            login();
        }
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        if (authToken == null) {
            return;
        }
        this.authToken = authToken;
        System.out.println("AuthToken: " + authToken);
        if (isFilled()) {
            login();
        }
    }

    public String getFaceName() {
        return faceName;
    }

    public void setFaceName(String faceName) {
        if (faceName == null) {
            return;
        }
        this.faceName = faceName;
        System.out.println("FaceName: " + faceName);
        if (isFilled()) {
            login();
        }
    }

    public String getFaceEmail() {
        return faceEmail;
    }

    public void setFaceEmail(String faceEmail) {
        if (faceEmail == null) {
            return;
        }
        this.faceEmail = faceEmail;
        System.out.println("FaceEmail: " + faceEmail);
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

    private boolean isFilled() {
        return !faceID.isEmpty() && !authToken.isEmpty()
                && !faceName.isEmpty() && !faceEmail.isEmpty();
    }
}
