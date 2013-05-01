package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.exceptions.AttributeNotFoundException;
import project.exceptions.InvalidAttributeException;
import project.exceptions.InvalidCircleIdException;
import project.exceptions.InvalidCircleNameException;
import project.exceptions.InvalidCreationDateException;
import project.exceptions.InvalidLoginException;
import project.exceptions.InvalidPasswordException;
import project.exceptions.InvalidSessionIdException;
import project.exceptions.InvalidSoundException;
import project.exceptions.InvalidTagException;
import project.exceptions.InvalidUserIdException;
import project.exceptions.LoginNotFoundException;
import project.exceptions.SessionNotFoundException;
import project.exceptions.TagNotFoundException;
import project.exceptions.UserNotFoundException;
import project.system.Circle;
import project.system.Project;
import project.system.Session;
import project.system.Sound;
import project.system.Tag;
import project.system.User;
import project.system.authentication.LogoutFailedException;
import project.system.authentication.password.PasswordAuth;
import project.system.authentication.password.PasswordAuthChannel;
import project.system.feedsorting.ChronologicalSourceFeedSorter;
import project.system.feedsorting.FavoriteSourcesFeedSorter;
import project.system.feedsorting.FeedSorter;
import project.system.feedsorting.MostFavoritedFeedSorter;
import project.system.recommender.FriendRecommender;

/**
 * Implements the Façade to be used by EasyAccept.
 */
public class ProjectFacade {

    private Project project;
    private Map<String, Object> ids;
    private Map<Object, String> objs;
    /**
     * Valid composition rules for the main feed construction.
     */
    public String[] compositionRules = {
        "PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS",
        "PRIMEIRO OS SONS COM MAIS FAVORITOS",
        "PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO",};
    private FeedSorter[] feedSorters = {
        new ChronologicalSourceFeedSorter(),
        new MostFavoritedFeedSorter(),
        new FavoriteSourcesFeedSorter(),};

    /**
     * Constructs the ProjectFacade.
     */
    public ProjectFacade() {
        project = Project.getInstance();
        ids = new HashMap<String, Object>();
        objs = new HashMap<Object, String>();
    }

    private String getIdOf(Object obj) {
        if (objs.containsKey(obj)) {
            return objs.get(obj);
        }

        String newId = obj.toString();
        ids.put(newId, obj);
        objs.put(obj, newId);

        return newId;
    }

    private Object getObjectForId(String id) {
        if (!ids.containsKey(id)) {
            throw new RuntimeException("Oops, internal error.");
        }
        return ids.get(id);
    }

    private Sound getSoundForId(String soundId) {
        if (soundId == null || soundId.isEmpty() || !ids.containsKey(soundId)) {
            throw new InvalidSoundException();
        }
        return (Sound) ids.get(soundId);
    }

    private Session getSessionForId(String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            throw new InvalidSessionIdException();
        }
        if (!ids.containsKey(sessionId)) {
            throw new SessionNotFoundException();
        }
        return (Session) ids.get(sessionId);
    }

    private User getUserForId(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new InvalidUserIdException();
        }
        Object user = ids.get(userId);
        if (user == null || !(user instanceof User)) {
            throw new UserNotFoundException();
        }
        return (User) user;
    }

    private Circle getCircleForId(String circleId) {
        if (circleId == null || circleId.isEmpty()) {
            throw new InvalidCircleIdException();
        }

        return (Circle) ids.get(circleId);
    }

    private Tag getTagForId(String tagId) {
        if (tagId == null || tagId.isEmpty()) {
            throw new InvalidTagException();
        }
        return (Tag) ids.get(tagId);
    }

    private Tag getTagForUserAndName(User user, String tagName) {
        if (tagName == null || tagName.isEmpty()) {
            throw new InvalidTagException();
        }
        for (Tag tag : user.getTagList()) {
            if (tag.getName().equals(tagName)) {
                return tag;
            }
        }
        return null;
    }

    private User getUserByLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new InvalidLoginException();
        }
        User user = project.getModel().findUserByLogin(login);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    /**
     * Creates a new user in the Project.
     *
     * @param login User's login
     * @param senha User's password
     * @param nome User's name
     * @param email User's email
     */
    public void criarUsuario(String login, String senha, String nome, String email) {
        PasswordAuth.getInstance().registerUser(
                project.getModel().addUser(login, nome, email), senha);
    }

    /**
     * Retrieves a attribute from a User.
     *
     * @param login User's login
     * @param atributo Attribute of interest, must be "nome" or "email"
     * @return The attribute's value
     */
    public String getAtributoUsuario(String login, String atributo) {
        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAttributeException();
        }
        if (atributo.equals("nome")) {
            return getUserByLogin(login).getName();
        }
        if (atributo.equals("email")) {
            return getUserByLogin(login).getEmail();
        }
        throw new AttributeNotFoundException();
    }

    private <T> String iterableToString(Iterable<T> iterable) {
        String result = "{";
        boolean first = true;
        for (Object obj : iterable) {
            if (first == false) {
                result += ",";
            }
            first = false;
            result += obj.toString();
        }
        result += "}";
        return result;
    }

    /**
     * Returns a user's postlist.
     *
     * @param idSessao User's session id
     * @return User's postlist
     */
    public String getPerfilMusical(String idSessao) {
        Session session = getSessionForId(idSessao);
        return iterableToString(session.getUser().getPostlist());
    }

    /**
     * Returns a user's sound feed.
     *
     * @param idSessao User's session id
     * @return User's sound feed
     */
    public String getVisaoDosSons(String idSessao) {
        Session session = getSessionForId(idSessao);
        return iterableToString(session.getUser().getUnsortedSoundFeed());
    }

    /**
     * Returns a user's main feed.
     *
     * @param idSessao User's session id
     * @return User's main feed
     */
    public String getMainFeed(String idSessao) {
        Session session = getSessionForId(idSessao);
        return iterableToString(session.getUser().getSortedSoundFeed());
    }

    private FeedSorter getSorterFromDescription(String rule) {
        for (int i = 0; i < compositionRules.length; i++) {
            if (compositionRules[i].equals(rule)) {
                return feedSorters[i];
            }
        }
        return null;
    }

    /**
     * Sets a user's preferred main feed sorting rule.
     *
     * The rule must be one of the listed in "compositionRules".
     *
     * @param idSessao User's session id
     * @param rule Sorting rule
     */
    public void setMainFeedRule(String idSessao, String rule) {
        Session session = getSessionForId(idSessao);
        if (rule == null || rule.isEmpty()) {
            throw new IllegalArgumentException("Regra de composição inválida");
        }
        FeedSorter sorter = getSorterFromDescription(rule);
        if (sorter == null) {
            throw new IllegalArgumentException("Regra de composição inexistente");
        }

        session.getUser().setFeedSorter(sorter);
    }

    /**
     * Posts a new sound.
     *
     * @param idSessao Session's id of the User who posted
     * @param link Sound link
     * @param dataCriacao Sound creation date
     * @return the sound id
     */
    public String postarSom(String idSessao, String link, String dataCriacao) {
        Session session = getSessionForId(idSessao);
        Sound sound = new Sound(link, dataCriacao, session.getUser());
        if (sound.getCreationDate().isDateOld()) {
            throw new InvalidCreationDateException();
        }
        session.getUser().post(sound);
        return getIdOf(sound);
    }

    /**
     * Retrieves an attribute of the sound.
     *
     * @param idSom Sound id
     * @param atributo The attribute of interest, must be "dataCriacao"
     * @return The value of the attribute
     */
    public String getAtributoSom(String idSom, String atributo) {
        Sound sound = getSoundForId(idSom);
        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAttributeException();
        }
        if (atributo.equals("dataCriacao")) {
            return sound.getCreationDate().toString();
        }
        throw new AttributeNotFoundException();
    }

    /**
     * Opens a new session.
     *
     * @param login User's login
     * @param senha User's password
     * @return Id of the new session
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.isEmpty()) {
            throw new InvalidLoginException();
        }

        User user = project.getModel().findUserByLogin(login);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!PasswordAuth.getInstance().checkPassword(user, senha)) {
            throw new InvalidPasswordException();
        }

        List<Session> openSessions = project.getSessionsOf(user);
        Session session;

        if (!openSessions.isEmpty()) {
            session = openSessions.get(0);
        } else {
            session = PasswordAuth.openNewSession(user, senha);
        }

        if (session == null) {
            throw new RuntimeException("PasswordAuth.openNewSession even after assuring the password was right!");
        }

        return getIdOf(session);
    }

    /**
     * Returns the id of some session's user.
     *
     * @param idSessao Session's id
     * @return the user id
     */
    public String getIDUsuario(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return getIdOf(user);
    }

    /**
     * Returns the number of followers of a user.
     *
     * @param idSessao User's session id
     * @return the number of followers
     */
    public String getNumeroDeSeguidores(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return Integer.toString(user.countFollowers());
    }

    /**
     * Sets a user as a follower of another one.
     *
     * @param idSessao Session id of the user who's gonna follow
     * @param login Login of the user who will be followed
     */
    public void seguirUsuario(String idSessao, String login) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        if (login == null || login.isEmpty() || login.equals(user.getLogin())) {
            throw new InvalidLoginException();
        }
        User user2 = project.getModel().findUserByLogin(login);
        if (user2 == null) {
            throw new LoginNotFoundException();
        }

        project.getModel().addUserFollowing(user, user2);
    }

    /**
     * Sets a sound as one favorite user's sound.
     *
     * @param idSessao User's session id
     * @param idSom Sound id
     */
    public void favoritarSom(String idSessao, String idSom) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        Sound sound = getSoundForId(idSom);
        user.addFavorite(sound);
    }

    /**
     * Returns a user's sound sources.
     *
     * @param idSessao User's session id
     * @return user's sound sources
     */
    public String getFontesDeSons(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return iterableToString(user.getSources());
    }

    /**
     * Returns a user's followers list.
     *
     * @param idSessao User's session id
     * @return user's followers list
     */
    public String getListaDeSeguidores(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return iterableToString(user.getFollowers());
    }

    /**
     * Returns a list of a user's favorite sounds.
     *
     * @param idSessao User's session id
     * @return the list of the user's favorite sounds
     */
    public String getSonsFavoritos(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return iterableToString(user.getFavoriteList());
    }

    /**
     * Returns a user's extra feed.
     *
     * @param idSessao User's session id
     * @return user's extra feed
     */
    public String getFeedExtra(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return iterableToString(user.getExtraSoundFeed());
    }

    /**
     * Creates a new user social circle.
     *
     * @param idSessao User's session id
     * @param nome New circle's name
     * @return circle's id
     */
    public String criarLista(String idSessao, String nome) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();

        if (nome == null || nome.isEmpty()) {
            throw new InvalidCircleNameException();
        }

        user.addCircle(nome);

        return getIdOf(user.getCircle(nome));
    }

    /**
     * Adds a user to a social circle.
     *
     * @param idSessao Session of the circle's owner.
     * @param idLista Name of the circle.
     * @param idUsuario User to add in the circle.
     */
    public void adicionarUsuario(String idSessao, String idLista, String idUsuario) {
        User toAddUser = getUserForId(idUsuario);
        Circle circle = getCircleForId(idLista);
        Session session = getSessionForId(idSessao);

        circle.addUser(toAddUser);
    }

    /**
     * Returns the feed for a given circle.
     *
     * @param idSessao Session of circle's owner.
     * @param idLista Circle's name
     * @return the feed
     */
    public String getSonsEmLista(String idSessao, String idLista) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        Circle circle = getCircleForId(idLista);

        FeedSorter oldFeedSorter = user.getFeedSorter();
        user.setFeedSorter(new ChronologicalSourceFeedSorter());

        List<Sound> feed = circle.getFeed();

        user.setFeedSorter(oldFeedSorter);

        return iterableToString(feed);
    }

    /**
     * Return the first composition rule.
     *
     * @return the first composition rule
     */
    public String getFirstCompositionRule() {
        return compositionRules[0];
    }

    /**
     * Return the second composition rule.
     *
     * @return the second composition rule
     */
    public String getSecondCompositionRule() {
        return compositionRules[1];
    }

    /**
     * Return the third composition rule.
     *
     * @return the third composition rule
     */
    public String getThirdCompositionRule() {
        return compositionRules[2];
    }

    /**
     * Return the amount of common favorites the logged user has with another.
     *
     * @param idSessao the user's session
     * @param idUsuario the another user's id
     * @return the amount of common favorites
     */
    public String getNumFavoritosEmComum(String idSessao, String idUsuario) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        User user2 = getUserForId(idUsuario);

        return Integer.toString(project.getStats().getCommonFavoritesCount(user, user2));
    }

    /**
     * Return the amount of common sources the logged user has with another.
     *
     * @param idSessao the user's session id
     * @param idUsuario the another user's id
     * @return the amount of common sources
     */
    public String getNumFontesEmComum(String idSessao, String idUsuario) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        User user2 = getUserForId(idUsuario);

        return Integer.toString(project.getStats().getCommonSourcesCount(user, user2));
    }

    /**
     * Returns the recommended friends for the logged user.
     *
     * @param idSessao the user's session id
     * @return the recommended friends
     */
    public String getFontesDeSonsRecomendadas(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();

        return iterableToString(FriendRecommender.getFriendRecommendations(user, user.getSources()));
    }

    /**
     * Creates a new tag for a user.
     *
     * @param idSessao The user session's id
     * @param tag the new tag name
     * @return the new tag id
     */
    public String criarTag(String idSessao, String tag) {
        Session session = getSessionForId(idSessao);

        if (tag == null || tag.isEmpty()) {
            throw new InvalidTagException();
        }

        User user = session.getUser();

        if (getTagForUserAndName(user, tag) != null) {
            throw new InvalidTagException();
        }

        Tag tagObj = user.addTag(tag);
        return getIdOf(tagObj);
    }

    /**
     * Tag a given sound.
     *
     * @param idSessao The tag owner session id
     * @param tag The tag
     * @param som The sound to tag
     */
    public void adicionarTagASom(String idSessao, String tag, String som) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        Tag tagObj = getTagForUserAndName(user, tag);
        
        if (tagObj == null)
            throw new TagNotFoundException();
        
        Sound sound = getSoundForId(som);

        tagObj.addSound(sound);
    }

    /**
     * Returns a list with all tags in a sound.
     *
     * @param idSessao The session id
     * @param som The sound id
     * @return the list
     */
    public String getListaTagsEmSom(String idSessao, String som) {
        Session session = getSessionForId(idSessao);
        User sessionUser = session.getUser();
        Sound sound = getSoundForId(som);
        List<String> result = new ArrayList<String>();

        for (User user : project.getModel().getUsers()) {
            for (Tag tag : user.getTagList()) {
                if (tag.getSoundList().contains(sound)) {
                    result.add(tag.toString());
                }
            }
        }

        return iterableToString(result);
    }

    /**
     * Returns the name of a tag.
     *
     * @param idSessao the session id
     * @param tag the tag id
     * @return the name
     */
    public String getNomeTag(String idSessao, String tag) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        Tag tagObj = getTagForId(tag);
        if (!user.getTagList().contains(tagObj))
            throw new TagNotFoundException();
        return tagObj.getName();
    }

    /**
     * Return a list with a user's tags.
     *
     * @param idSessao the user's session id
     * @return the list
     */
    public String getTagsDisponiveis(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        List<String> result = new ArrayList<String>();

        for (Tag tag : user.getTagList()) {
            result.add(tag.toString());
        }

        return iterableToString(result);
    }

    /**
     * Closes a user's session.
     *
     * @param login user's login
     */
    public void encerrarSessao(String login) {

        if (login == null || login.isEmpty()) {
            throw new InvalidLoginException();
        }

        User user = project.getModel().findUserByLogin(login);

        if (user == null) {
            throw new InvalidLoginException();
        }

        List<Session> sessions = project.getSessionsOf(user);

        for (Session session : sessions) {
            try {
                ids.remove(objs.remove(session));
                project.closeSession(session);
            } catch (LogoutFailedException ex) {
                throw new RuntimeException("Password logout failed!");
            }
        }
    }

    /**
     * Clears the system.
     */
    public void zerarSistema() {
        encerrarSistema();
        project.clear();
        ids.clear();
        objs.clear();
    }

    /**
     * Closes all system sessions.
     */
    public void reiniciarSistema() {
        encerrarSistema();
    }

    /**
     * Closes all system sessions.
     */
    public void encerrarSistema() {
        project.clearSessions();
    }
}
