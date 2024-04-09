package PatientPortal;

public class InsuranceInfo {
    private String provider;
    private String policyNumber;
    private String holderName;

    // Constructor
    public InsuranceInfo(String provider, String policyNumber, String holderName) {
        this.provider = provider;
        this.policyNumber = policyNumber;
        this.holderName = holderName;
    }

    // Getters and Setters
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "InsuranceInfo{" +
                "provider='" + provider + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", holderName='" + holderName + '\'' +
                '}';
    }
}
