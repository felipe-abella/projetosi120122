package project.system.authentication.facebook;

import java.util.LinkedList;
import java.util.List;
import project.system.User;
import project.system.authentication.AuthChannel;
import project.system.authentication.AuthChannelListener;
import project.system.authentication.AuthChannelState;
import project.system.authentication.LoginFailedException;
import project.system.authentication.LogoutFailedException;

public class FacebookAuthChannel implements AuthChannel {

    private User user;
    private AuthChannelState state;
    private List<AuthChannelListener> listeners;
    private String inputFaceid, inputAuthToken;

    FacebookAuthChannel(User user) {
        this.user = user;
        state = AuthChannelState.CLOSED;
        listeners = new LinkedList<AuthChannelListener>();
        inputFaceid = inputAuthToken = null;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public AuthChannelState getState() {
        return state;
    }

    @Override
    public FacebookAuth getAuthenticator() {
        return FacebookAuth.getInstance();
    }

    public void enterFaceid(String inputFaceid) {
        this.inputFaceid = inputFaceid;
    }

    public void enterInputAuthToken(String inputAuthToken) {
        this.inputAuthToken = inputAuthToken;
    }

    private void open() {
        state = AuthChannelState.OPEN;
        for (AuthChannelListener listener : listeners) {
            listener.opened(this);
        }
    }

    private void close() {
        state = AuthChannelState.CLOSED;
        for (AuthChannelListener listener : listeners) {
            listener.closed(this);
        }
    }

    @Override
    public void login() throws LoginFailedException {
        if (state == AuthChannelState.CLOSED) {
            if (!FacebookAuth.getInstance().isRegistered(user) ||
                    !FacebookAuth.getInstance().getFaceid(user).equals(inputFaceid)) {
                throw new FacebookLoginFailedException();
            }
            open();
        }
    }

    @Override
    public void logout() throws LogoutFailedException {
        if (state == AuthChannelState.OPEN) {
            close();
        }
    }

    @Override
    public void addListener(AuthChannelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(AuthChannelListener listener) {
        listeners.remove(listener);
    }
}
