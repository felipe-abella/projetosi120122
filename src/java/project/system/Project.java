package project.system;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import project.exceptions.InvalidLoginException;
import project.exceptions.InvalidPasswordException;
import project.exceptions.UserNotFoundException;

public class Project implements Serializable {
    private Set<Session> sessions;
    private Map<User, Session> userSession;
    private ProjectModel model;
    
    public Project() {
        sessions = new TreeSet<Session>();
        userSession = new TreeMap<User, Session>();
        model = new ProjectModel();
    }
    
    public ProjectModel getModel() {
        return model;
    }
    
    public Session login(String login, String password) {
        if (login == null || login.isEmpty())
            throw new InvalidLoginException();
        
        User user = model.findUserByLogin(login);
        
        if (user == null)
            throw new UserNotFoundException();
        if (!user.getPassword().equals(password))
            throw new InvalidPasswordException();
        
        Session result = userSession.get(user);
        if (result == null) {
            result = new Session(user);
            sessions.add(result);
            userSession.put(user, result);
        }
        
        return result;
    }
    
    public void logout(String login) {
        if (login == null || login.isEmpty())
            throw new InvalidLoginException();
        
        User user = model.findUserByLogin(login);
        
        if (user == null)
            throw new InvalidLoginException();
        
        Session session = userSession.get(user);
        if (session != null) {
            sessions.remove(session);
            userSession.remove(user);
        }
    }
    
    public Session getSessionOf(String login) {
        return userSession.get(model.findUserByLogin(login));
    }
    
    public void clear() {
        userSession.clear();
        sessions.clear();
        model.clear();
    }
}
