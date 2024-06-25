package dto;

public class SessionManager {
    private static UserDTO loggedInUser;

    public static void login(UserDTO user) {
        loggedInUser = user;
    }

    public static void logout() {
        loggedInUser = null;
    }

    public static UserDTO getLoggedInUser() {
        return loggedInUser;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
