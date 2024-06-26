package cse360project_milestone2;

public class Patient extends Account {
    private String insuranceProvider;
    private int insuranceID;
    private String dob;
    private String address;
    private String pharmacy;

    public Patient(String username, String password, String firstname, String lastname, String email, long phonenumber, String insuranceProvider, int insuranceID, String dob, String address, String pharmacy) {
        super(username, password, firstname, lastname, email, phonenumber);
        this.insuranceProvider = insuranceProvider;
        this.insuranceID = insuranceID;
        this.dob = dob;
        this.address = address;
        this.pharmacy = pharmacy;
    }

    // Getter and setter for insuranceProvider
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
    
    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getPharmacy() {
        return pharmacy;
    }
    
    public String getDOB() {
        return dob;
    }

    public int getInsuranceID() {
        return insuranceID;
    }
    
    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    
    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Insurance Provider: " + insuranceProvider + "\n" +
               "Insurance ID: " + insuranceID + "\n" +
               "Date of Birth: " + dob + "\n" +
               "Address: " + address + "\n" +
               "Pharmacy: " + pharmacy; 
    }
}