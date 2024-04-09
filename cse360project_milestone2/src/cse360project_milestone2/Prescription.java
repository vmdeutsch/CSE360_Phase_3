package PatientPortal;

public class Prescription {
    private String medicationName;
    private String dosage;
    private String frequency;
    private String duration;
    private String instructions;

    // Constructor
    public Prescription(String medicationName, String dosage, String frequency, String duration, String instructions) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.instructions = instructions;
    }

    // Getters and setters (You can generate these using IDE shortcuts or manually define them)
}
