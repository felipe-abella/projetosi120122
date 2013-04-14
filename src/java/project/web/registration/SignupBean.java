package project.web.registration;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Project;
import project.system.ProjectModel;
import project.system.User;
import project.system.authentication.LoginFailedException;
import project.system.authentication.NotLoggedInException;
import project.system.authentication.facebook.FacebookAuth;
import project.system.authentication.facebook.FacebookAuthChannel;
import project.web.ProjectBean;
import project.web.SessionBean;
import project.web.authentication.FacebookAuthBean;

/**
 * Bean responsible for managing the registering of new users on the system.
 */
@Named("signupBean")
@RequestScoped
public class SignupBean implements Serializable {

    private String signupType;
    private String login, name, email;
    @Inject
    private ProjectBean projectBean;
    @Inject
    private SessionBean sessionBean;
    @Inject
    private FacebookAuthBean faceBean;

    /**
     * Returns new user's login.
     *
     * @return new user's login
     */
    public String getLogin() {
        if (login == null && "facebook".equals(signupType)) {
            login = faceBean.getFaceID();
        }
        System.out.println("faceBean Login:" + faceBean.getFaceID());
        return login;
    }

    /**
     * Returns new user's name.
     *
     * @return new user's name
     */
    public String getName() {
        if (name == null && "facebook".equals(signupType)) {
            name = faceBean.getFaceName();
        }
        return name;
    }

    /**
     * Return new user's email.
     *
     * @return new user's email
     */
    public String getEmail() {
        if (email == null && "facebook".equals(signupType)) {
            email = faceBean.getFaceEmail();
        }
        return email;
    }

    /**
     * Returns the signup type.
     *
     * The signup type determines what triggered the sign-up, might be
     * "facebook" for a facebook login attempt, for example.
     *
     * @return the signup type
     */
    public String getSignupType() {
        return signupType;
    }

    /**
     * Sets the new user's login.
     *
     * @param login new "new user's login"
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Sets the new user's name.
     *
     * @param name new "new user's name"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the new user's email.
     *
     * @param email new "new user's email"
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the signup type.
     *
     * @param signupType the new signup type
     */
    public void setSignupType(String signupType) {
        System.out.println("Set signup type: " + signupType);
        this.signupType = signupType;
    }

    /**
     * Returns if a password <b>must</b> be registered for the new account.
     *
     * @return if the password is required
     */
    public boolean isPasswordRequired() {
        return !"facebook".equals(signupType);
    }

    /**
     * Creates the new user.
     * 
     * @return the new user
     */
    User createUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        Project project = projectBean.getProject();
        ProjectModel model = project.getModel();

        if (model.isLoginTaken(login)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este login já é cadastrado!", null));
            return null;
        }

        if (model.isEmailTaken(email)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este email já é cadastrado!", null));
            return null;
        }

        User result = model.addUser(login, name, email);

        if ("facebook".equals(signupType)) {
            try {
                FacebookAuth auth = FacebookAuth.getInstance();
                auth.register(result, faceBean.getFaceID());

                FacebookAuthChannel channel = auth.getChannel(result);
                channel.enterFaceid(faceBean.getFaceID());
                channel.enterInputAuthToken(faceBean.getAuthToken());

                channel.login();

                sessionBean.setSession(projectBean.getProject().openNewSession(result, channel));
            } catch (LoginFailedException ex) {
                Logger.getLogger(SignupBean.class.getName()).log(Level.SEVERE, "Unexpected error!", ex);
            } catch (NotLoggedInException ex) {
                Logger.getLogger(SignupBean.class.getName()).log(Level.SEVERE, "Unexpected error!", ex);
            }
        }

        context.addMessage(null, new FacesMessage("O usuário foi criado!"));

        return result;
    }
}
