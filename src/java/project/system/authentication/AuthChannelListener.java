package project.system.authentication;

public interface AuthChannelListener {

    public void opened(AuthChannel authSession);

    public void closed(AuthChannel authSession);
}
