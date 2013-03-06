package project.web;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import project.system.Project;
import project.system.Sound;

@Named("projectBean")
@ApplicationScoped
public class ProjectBean {

    private Project project;

    public ProjectBean() {
        project = new Project();
        /* Testing purposes: */
        //createSampleState();
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
    }

    public Project getProject() {
        return project;
    }
}
