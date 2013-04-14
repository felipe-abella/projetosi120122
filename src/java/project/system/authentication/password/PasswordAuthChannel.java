package project.system.authentication.password;

import java.util.LinkedList;
import java.util.List;
import project.system.User;
import project.system.authentication.AuthChannelListener;
import project.system.authentication.AuthChannel;
import project.system.authentication.AuthChannelState;
import project.system.authentication.LogoutFailedException;

public class PasswordAuthChannel implements AuthChannel {

    private User user;
    private String password;
    private AuthChannelState state;
    private List<AuthChannelListener> listeners;

    PasswordAuthChannel(User user) {
        this.user = user;
        password = null;
        state = AuthChannelState.CLOSED;
        listeners = new LinkedList<AuthChannelListener>();
    }

    @Override
    public User getUser() {
        return user;
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
    public void login() throws PasswordLoginFailedException {
        if (state == AuthChannelState.CLOSED) {
            if (!getAuthenticator().havePassword(user)) {
                throw new NoPasswordException();
            }
            if (!getAuthenticator().checkPassword(user, password)) {
                throw new WrongPasswordException();
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

    public void enterPassword(String password) {
        this.password = password;
    }

    @Override
    public AuthChannelState getState() {
        return state;
    }

    @Override
    public PasswordAuth getAuthenticator() {
        return PasswordAuth.getInstance();
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
