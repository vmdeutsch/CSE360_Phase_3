package cse360project_milestone2;

public class Nurse extends Account {
    private String degree;

    public Nurse(String username, String password, String firstname, String lastname, String email, int phonenumber, String degree) {
        super(username, password, firstname, lastname, email, phonenumber);
        this.degree = degree;
    }

    // Getter and setter for degree
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Degree: " + degree;
    }
}
