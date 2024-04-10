package cse360project_milestone2;

public class CurrentUser {
    private static String username;
    private static String role;
    private static String currentPatient;

    private CurrentUser() {}

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CurrentUser.username = username;
    }
    
    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        CurrentUser.role = role;
    }
    
    public static String getCurrentPatient() {
        return currentPatient;
    }
    
    public static void setCurrentPatient(String currentPatient) {
        CurrentUser.currentPatient = currentPatient;
    }
}
