package cse360project_milestone2;

public class Doctor extends Account {
    private String degree;
    // Add any additional attributes specific to Doctor class here

    public Doctor(String username, String password, String firstname, String lastname, String email, int phonenumber, String degree) {
        super(username, password, firstname, lastname, email, phonenumber);
        this.degree = degree;
        // Initialize any additional attributes specific to Doctor class here
    }

    // Getter and setter for degree
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    // You can override toString method if you want to include degree
    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Degree: " + degree;
    }

    // Add any additional methods specific to Doctor class here
}
