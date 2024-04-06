/* Vaughn Deutsch
 * March 9, 2023
 * Login GUI for the CSE360 project, milestone 2.
 */

package cse360project_milestone2;

public class Account {
    private String username; //synonymous with userID and patientID.
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private int phonenumber;

    public Account(String username, String password, String firstname, String lastname, String email, int phonenumber) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    // Getters
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getFirstName() {
        return firstname;
    }
    
    public String getLastName() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    
    public int getPhoneNumber() {
        return phonenumber;
    }
    
    // setters

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }
    
    public void setlastName(String lastname) {
        this.lastname = lastname;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhoneNumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String toString() {
        return "Username: " + username + "\n" +
               "Password: " + password + "\n" +
               "First Name: " + firstname + "\n" +
               "Last Name: " + lastname + "\n" +
               "Email: " + email + "\n" +
               "Phone Number: " + phonenumber;
    }
}