package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import project.exceptions.AttributeNotFoundException;
import project.exceptions.InvalidAttributeException;
import project.exceptions.InvalidCreationDateException;
import project.exceptions.InvalidLoginException;
import project.exceptions.InvalidSessionIdException;
import project.exceptions.InvalidSoundException;
import project.exceptions.LoginNotFoundException;
import project.exceptions.SessionNotFoundException;
import project.exceptions.UserNotFoundException;
import project.system.Project;
import project.system.Session;
import project.system.Sound;
import project.system.User;
import project.system.feedsorting.CronologicalSourceFeedSorter;
import project.system.feedsorting.FavoriteSourcesFeedSorter;
import project.system.feedsorting.FeedSorter;
import project.system.feedsorting.MostFavoritedFeedSorter;

public class ProjectFacade {

    private Project project;
    private Map<String, Object> ids;
    private Map<Object, String> objs;
    private String[] compositionRules = {
        "PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS",
        "PRIMEIRO OS SONS COM MAIS FAVORITOS",
        "PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO",};
    private FeedSorter[] feedSorters = {
        new CronologicalSourceFeedSorter(),
        new MostFavoritedFeedSorter(),
        new FavoriteSourcesFeedSorter(),};

    public ProjectFacade() {
        project = new Project();
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

    private User userByLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new InvalidLoginException();
        }
        User user = project.getModel().findUserByLogin(login);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public void criarUsuario(String login, String senha, String nome, String email) {
        project.getModel().addUser(login, senha, nome, email);
    }

    public String getAtributoUsuario(String login, String atributo) {
        if (atributo == null || atributo.isEmpty()) {
            throw new InvalidAttributeException();
        }
        if (atributo.equals("nome")) {
            return userByLogin(login).getName();
        }
        if (atributo.equals("email")) {
            return userByLogin(login).getEmail();
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

    public String getPerfilMusical(String idSessao) {
        Session session = getSessionForId(idSessao);
        return iterableToString(session.getUser().getPostlist());
    }

    public String getVisaoDosSons(String idSessao) {
        Session session = getSessionForId(idSessao);
        return iterableToString(session.getUser().getUnsortedSoundFeed());
    }

    public String getMainFeed(String idSessao) {
        Session session = getSessionForId(idSessao);
        return iterableToString(session.getUser().getSortedSoundFeed());
    }

    public FeedSorter getSorterFromDescription(String rule) {
        for (int i = 0; i < compositionRules.length; i++) {
            if (compositionRules[i].equals(rule)) {
                return feedSorters[i];
            }
        }
        return null;
    }

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

    public String postarSom(String idSessao, String link, String dataCriacao) {
        Session session = getSessionForId(idSessao);
        Sound sound = new Sound(link, dataCriacao, session.getUser());
        if (sound.getCreationDate().isDateOld()) {
            throw new InvalidCreationDateException();
        }
        session.getUser().post(sound);
        return getIdOf(sound);
    }

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

    public String abrirSessao(String login, String senha) {
        Session session = project.login(login, senha);
        return getIdOf(session);
    }

    public String getIDUsuario(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return getIdOf(user);
    }

    public String getNumeroDeSeguidores(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return Integer.toString(user.countFollowers());
    }

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

    public void favoritarSom(String idSessao, String idSom) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        Sound sound = getSoundForId(idSom);
        user.addFavorite(sound);
    }

    public String getFontesDeSons(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return iterableToString(user.getSources());
    }

    public String getListaDeSeguidores(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return iterableToString(user.getFollowers());
    }

    public String getSonsFavoritos(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return iterableToString(user.getFavoriteList());
    }

    public String getFeedExtra(String idSessao) {
        Session session = getSessionForId(idSessao);
        User user = session.getUser();
        return iterableToString(user.getExtraSoundFeed());
    }

    public String getFirstCompositionRule() {
        return compositionRules[0];
    }

    public String getSecondCompositionRule() {
        return compositionRules[1];
    }

    public String getThirdCompositionRule() {
        return compositionRules[2];
    }

    public void encerrarSessao(String login) {
        Session session = project.getSessionOf(login);
        ids.remove(objs.remove(session));
        project.logout(login);
    }

    public void zerarSistema() {
        project.clear();
    }

    public void encerrarSistema() {
        zerarSistema();
        ids.clear();
        objs.clear();
    }
}
