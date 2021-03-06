package project.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.inject.Named;
import project.system.Circle;
import project.system.Project;
import project.system.Sound;
import project.system.User;
import project.system.authentication.facebook.FacebookAuth;
import project.system.authentication.password.PasswordAuth;

/**
 * Bean responsible for the system Project instance.
 *
 * This bean initializes the system Project instance (possibly with some test
 * data) and make it accessible to all other beans (with injection).
 */
@Named("projectBean")
@ApplicationScoped
public class ProjectBean implements SystemEventListener {

    private Project project;

    /**
     * Creates a new ProjectBean, instantiating the Project object.
     *
     * After instantiating the Project object, this function might also populate
     * it with some test data.
     */
    public ProjectBean() {
        project = null;
        loadSystem();
        if (project != null)
            Project.setInstance(project);
        
        project = Project.getInstance();
        project.clear();

        /* Testing purposes: */
        createSampleState();
    }

    private void createSampleState() {
        for (int c = 'a'; c <= 'z'; c++) {
            String s = String.valueOf((char) c);
            PasswordAuth.getInstance().registerUser(project.getModel().addUser(s, s, s),
                    s);
        }
        for (int c = 'b'; c <= 'r'; c++) {
            String s = String.valueOf((char) c);
            project.getModel().addUserFollowing(project.getModel().findUserByLogin("a"),
                    project.getModel().findUserByLogin(s));
        }

        project.getModel().findUserByLogin("b").post(new Sound("youtube", "30/11/2015", project.getModel().findUserByLogin("b")));
        project.getModel().findUserByLogin("b").post(new Sound("youtube2134", "30/11/2016", project.getModel().findUserByLogin("b")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));
        project.getModel().findUserByLogin("c").post(new Sound("youtube234234", "30/11/2017", project.getModel().findUserByLogin("c")));

        project.getModel().findUserByLogin("z").post(new Sound("blabla", "30/11/2017", project.getModel().findUserByLogin("z")));
        project.getModel().findUserByLogin("y").post(new Sound("blabla2", "30/11/2017", project.getModel().findUserByLogin("y")));

        User a = project.getModel().findUserByLogin("a");
        User b = project.getModel().findUserByLogin("b");
        User c = project.getModel().findUserByLogin("c");
        User y = project.getModel().findUserByLogin("y");
        User z = project.getModel().findUserByLogin("z");

        project.getModel().addUserFollowing(z, b);
        project.getModel().addUserFollowing(y, b);
        project.getModel().addUserFollowing(y, c);

        a.addFavorite(z.getPostlist().get(0));
        a.addFavorite(b.getPostlist().get(0));
        a.addFavorite(b.getPostlist().get(1));

        z.addFavorite(y.getPostlist().get(0));
        z.addFavorite(b.getPostlist().get(0));

        y.addFavorite(b.getPostlist().get(0));
        y.addFavorite(b.getPostlist().get(1));

        a.addCircle("C1");
        Circle c1 = a.getCircle("C1");
        c1.addUser(b);
        //c1.addUser(c);
    }

    /**
     * Returns the Project instance.
     *
     * @return the Project instance
     */
    public Project getProject() {
        return project;
    }

    private void loadSystem() {
        try {
            ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream("system.dat")));
            project = (Project)is.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ProjectBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProjectBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void saveSystem() {
        try {
                ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("system.dat")));
                os.writeObject(project);
                os.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProjectBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ProjectBean.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (event instanceof PreDestroyApplicationEvent) {
            saveSystem();
        }
    }

    @Override
    public boolean isListenerForSource(Object source) {
        return true;
    }
}
