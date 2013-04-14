package project.system.authentication;

/**
 * Represents the possible states of an auth channel.
 */
public enum AuthChannelState {
    /**
     * Means the channel is open (i.e., the user is logged in).
     */
    OPEN,
    
    /**
     * Means the channel is closed (i.e., the user is logged out).
     */    
    CLOSED,
}
