package cse360project_milestone2;

public class Patient extends Account {
    private String insuranceProvider;
    private int insuranceID;
    private String dob;
    private String address;
    // Add any additional attributes specific to Patient class here

    public Patient(String username, String password, String firstname, String lastname, String email, int phonenumber, String insuranceProvider, int insuranceID, String dob, String address) {
        super(username, password, firstname, lastname, email, phonenumber);
        this.insuranceProvider = insuranceProvider;
        this.insuranceID = insuranceID;
        this.dob = dob;
        this.address = address;
        // Initialize any additional attributes specific to Patient class here
    }

    // Getter and setter for insuranceProvider
    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }
    
    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }
    
    public void setDOB(String dob) {
        this.dob = dob;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    // You can override toString method if you want to include insurance provider
    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Insurance Provider: " + insuranceProvider + "\n" +
               "Insurance ID: " + insuranceID + "\n" +
               "Date of Birth: " + dob + "\n" +
               "Address: " + address;
    }

    // Add any additional methods specific to Patient class here
}