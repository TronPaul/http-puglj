package http_puglj.steam;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException(long steamId) {
        super("Player with steam-id=" + steamId + " not found");
    }
}
