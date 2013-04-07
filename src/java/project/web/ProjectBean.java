package project.web;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import project.system.Circle;
import project.system.Project;
import project.system.Sound;
import project.system.User;

/**
 * Bean responsible for the system Project instance.
 *
 * This bean initializes the system Project instance (possibly with some test
 * data) and make it accessible to all other beans (with injection).
 */
@Named("projectBean")
@ApplicationScoped
public class ProjectBean {

    private Project project;

    /**
     * Creates a new ProjectBean, instantiating the Project object.
     *
     * After instantiating the Project object, this function might also populate
     * it with some test data.
     */
    public ProjectBean() {
        project = Project.getInstance();
        project.clear();
        
        /* Testing purposes: */
        createSampleState();
    }

    private void createSampleState() {
        for (int c = 'a'; c <= 'z'; c++) {
            String s = String.valueOf((char) c);
            project.getModel().addUser(s, s, s, s);
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
        
        User a = project.getModel().findUserByLogin("a");
        User b = project.getModel().findUserByLogin("b");
        User c = project.getModel().findUserByLogin("c");
        
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
}
