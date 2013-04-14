package project.system.authentication;

/**
 * Represents a listener for an AuthChannel.
 */
public interface AuthChannelListener {

    /**
     * Event called when the channel opens.
     *
     * @param authChannel the auth channel
     */
    public void opened(AuthChannel authChannel);

    /**
     * Event called when the channel closes.
     *
     * @param authChannel the auth channel
     */
    public void closed(AuthChannel authChannel);
}
