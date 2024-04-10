package cse360project_milestone2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientPortal extends Application {
	private Button insuranceButton, prescriptionsButton, medicalHistoryButton, appointmentsButton, dashboardButton;
	private GridPane contentPane;
	private String patientId;
	private TextField nameField;
    private TextField phoneField;
    private TextField emailField;
    private TextField addressField;
    private TextField pharmacyField;
    private TextField providerField;
    private TextField policyNumberField;
    private TextField birthdateField;
    private VBox profileBox;
    private Button editProfileButton;
    private Button saveProfileButton;
    
    @Override
    public void start(Stage primaryStage) {
        patientId = CurrentUser.getUsername();;
        // Instantiate text fields
        nameField = new TextField();
        phoneField = new TextField();
        emailField = new TextField();
        addressField = new TextField();
        pharmacyField = new TextField();
        providerField = new TextField();
        policyNumberField = new TextField();
        birthdateField = new TextField();

        // Load patient data from file
        loadPatientData(patientId);

        // Create content pane
        contentPane = new GridPane(); // Initialize the contentPane

        // Create UI elements
        // Left-side menu
        VBox menuPane = new VBox();
        menuPane.setPadding(new Insets(10));
        menuPane.setSpacing(10);
        dashboardButton = new Button("Dashboard"); // Corrected assignment
        Button messagesButton = new Button("Messages");
        Button helpButton = new Button("Help");
        Button logoutButton = new Button("Logout");
        menuPane.getChildren().addAll(dashboardButton, messagesButton, helpButton, logoutButton);

        // Content area
        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(20));
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);

        // Profile section
        profileBox = new VBox();
        profileBox.setPadding(new Insets(10));
        profileBox.setSpacing(5);
        profileBox.setStyle("-fx-background-color: #e0f2f1; -fx-padding: 15;");

        // Add patient information to profile section
        profileBox.getChildren().addAll(
                new Label("Name: " + nameField.getText()),
                new Label("Phone Number: " + phoneField.getText()),
                new Label("Date of Birth: " + birthdateField.getText()),
                new Label("Email: " + emailField.getText()),
                new Label("Address: " + addressField.getText()),
                new Label("Pharmacy: " + pharmacyField.getText())
        );

        // Other sections style
        String sectionStyle = "-fx-background-color: #e0f2f1; -fx-padding: 10;";

        // Placeholder sections
        Button insuranceButton = new Button("View Insurance");
        Button prescriptionsButton = new Button("View Prescriptions");
        Button medicalHistoryButton = new Button("View Medical History");
        Button appointmentsButton = new Button("View Appointments");

        // Adjust button sizes and styles
        insuranceButton.setMinSize(300, 100);
        insuranceButton.setStyle(sectionStyle);
        prescriptionsButton.setMinSize(300, 100);
        prescriptionsButton.setStyle(sectionStyle);
        medicalHistoryButton.setMinSize(300, 100);
        medicalHistoryButton.setStyle(sectionStyle);
        appointmentsButton.setMinSize(250, 100);
        appointmentsButton.setStyle(sectionStyle);

        // Align buttons with the medical history section
        mainGrid.getColumnConstraints().addAll(
                new ColumnConstraints(300), // Column 0
                new ColumnConstraints(5),  // Gap Column
                new ColumnConstraints(250), // Column 1
                new ColumnConstraints(300),  // Gap Column
                new ColumnConstraints(30)  // Column 2, width as needed for the profile box
        );

        medicalHistoryButton.setMinHeight(250); // Min height as needed to fill the space below
        appointmentsButton.setMinHeight(250);
        
        // Add elements to the main grid
        mainGrid.add(insuranceButton, 0, 0, 3, 1);
        mainGrid.add(prescriptionsButton, 0, 1);
        mainGrid.add(medicalHistoryButton, 0, 2, 3, 1); // Span 3 columns
        mainGrid.add(appointmentsButton, 2, 2);
        mainGrid.add(profileBox, 2, 0, 1, 2); // Span 2 rows

        // Create main layout
        BorderPane root = new BorderPane();
        root.setLeft(menuPane);
        root.setCenter(mainGrid);
        
     // Edit Profile button functionality
//        editProfileButton = new Button("Edit");
//        editProfileButton.setStyle("-fx-background-color: #4db6ac; -fx-text-fill: white;");
//        editProfileButton.setOnAction(e -> {
//            // Enable editing mode
//            setEditable(true);
//        });
//
//        // Save Profile button functionality
//        Button saveProfileButton = new Button("Save");
//        saveProfileButton.setStyle("-fx-background-color: #4db6ac; -fx-text-fill: white;");
//        saveProfileButton.setOnAction(e -> {
//            // Disable editing mode
//            setEditable(false);
//            // Save changes to file or database
//            saveChanges();
//        });
//
//        // Add Save and edit Profile button to the profileBox
//        profileBox.getChildren().addAll(editProfileButton, saveProfileButton);

        // Set the actions for buttons
        insuranceButton.setOnAction(e -> {
            mainGrid.getChildren().clear();
            mainGrid.add(showInsuranceSection(contentPane), 0, 0, 3, 1);
        });
        prescriptionsButton.setOnAction(e -> {
            mainGrid.getChildren().clear();
            mainGrid.add(showPrescriptionsSection(contentPane), 0, 0, 3, 1);
        });
        medicalHistoryButton.setOnAction(e -> {
            mainGrid.getChildren().clear();
            mainGrid.add(showMedicalHistorySection(contentPane), 0, 0, 3, 1);
        });

	messagesButton.setOnAction(event -> {
            MessageSystem messageSystem = new MessageSystem();
            Stage messagesStage = new Stage();
            messageSystem.start(messagesStage);
            primaryStage.close();
        });
        
        appointmentsButton.setOnAction(event -> {
            Appointments Appointment = new Appointments();
            Stage AppointmentsStage = new Stage();
            Appointment.start(AppointmentsStage);
            primaryStage.close();
        });

		logoutButton.setOnAction(e->{
			if(primaryStage!=null) {
				primaryStage.close();
			    Login login = new Login();
				CurrentUser.setUsername(null);
			    try {
			        login.start(primaryStage);
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
			}
			
		});
        // Set action for dashboard button
        dashboardButton.setOnAction(e -> {
            // Clear the mainGrid
            mainGrid.getChildren().clear();

            // Recreate the original layout
            mainGrid.add(insuranceButton, 0, 0, 3, 1);
            mainGrid.add(prescriptionsButton, 0, 1);
            mainGrid.add(medicalHistoryButton, 0, 2, 3, 1); // Span 3 columns
            mainGrid.add(appointmentsButton, 2, 2);
            mainGrid.add(profileBox, 2, 0, 1, 2); // Span 2 rows

            // Refresh profile section with updated patient information
            profileBox.getChildren().clear();
            profileBox.getChildren().addAll(
                    new Label("Name: " + nameField.getText()),
                    new Label("Phone Number: " + phoneField.getText()),
                    new Label("Date of Birth: " + birthdateField.getText()),
                    new Label("Email: " + emailField.getText()),
                    new Label("Address: " + addressField.getText()),
                    new Label("Pharmacy: " + pharmacyField.getText())
////                    editProfileButton,
////                    saveProfileButton // Include the Save Profile button when viewing the dashboard
);
        });

        // Create scene and stage
        Scene scene = new Scene(root, 800, 550);
        primaryStage.setTitle("Patient Portal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
//    private void setEditable(boolean editable) {
//        nameField.setEditable(editable);
//        phoneField.setEditable(editable);
//        emailField.setEditable(editable);
//        addressField.setEditable(editable);
//        pharmacyField.setEditable(editable);
//    }
//    
//    private void saveChanges() {
//        // Update existing labels with new text
//        for (Node node : profileBox.getChildren()) {
//            if (node instanceof Label) {
//                Label label = (Label) node;
//                String labelText = label.getText();
//                if (labelText.startsWith("Name:")) {
//                    label.setText("Name: " + nameField.getText());
//                } else if (labelText.startsWith("Phone Number:")) {
//                    label.setText("Phone Number: " + phoneField.getText());
//                } else if (labelText.startsWith("Date of Birth:")) {
//                    label.setText("Date of Birth: " + birthdateField.getText());
//                } else if (labelText.startsWith("Email:")) {
//                    label.setText("Email: " + emailField.getText());
//                } else if (labelText.startsWith("Address:")) {
//                    label.setText("Address: " + addressField.getText());
//                } else if (labelText.startsWith("Pharmacy:")) {
//                    label.setText("Pharmacy: " + pharmacyField.getText());
//                }
//            }
//        }
//    }
    
    private void loadPatientData(String patientId) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/cse360project_milestone2/accounts/patients/"+patientId + ".txt"));
            String line;
            String firstName = null;
            String lastName = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String field = parts[0].trim();
                    String value = parts[1].trim();
                    switch (field) {
                        case "First Name":
                            firstName = value;
                            break;
                        case "Last Name":
                            lastName = value;
                            break;
                        case "Date of Birth":
                            birthdateField.setText(value); // Assuming birthdateField is your TextField for date of birth
                            break;
                        case "Phone Number":
                            phoneField.setText(value);
                            break;
                        case "Email":
                            emailField.setText(value);
                            break;
                        case "Address":
                            addressField.setText(value);
                            break;
                        case "Pharmacy":
                            pharmacyField.setText(value);
                            break;
                        case "Insurance Provider":
                            providerField.setText(value);
                            break;
                        case "Insurance ID":
                            policyNumberField.setText(value);
                            break;
                        // Add cases for other fields as needed
                    }
                }
            }
            reader.close();
            
            // Concatenate first name and last name
            if (firstName != null && lastName != null) {
                String fullName = firstName + " " + lastName;
                nameField.setText(fullName);
            } else if (firstName != null) {
                nameField.setText(firstName);
            } else {
                nameField.setText(""); // Clear the name field if both first name and last name are not available
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	private InsuranceInfo loadInsuranceData(String patientId) {
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader("src/cse360project_milestone2/accounts/patients/"+patientId + ".txt"));
	        String line;
	        String provider = null;
	        String policyNumber = null;
	        String holderName = null;
	        String firstName = null;
	        String lastName = null;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(": ");
	            if (parts.length == 2) {
	                String field = parts[0].trim();
	                String value = parts[1].trim();
	                switch (field) {
	                    case "Insurance Provider":
	                        provider = value;
	                        break;
	                    case "Insurance ID":
	                        policyNumber = value;
	                        break;
	                    case "First Name":
	                        firstName = value;
	                        break;
	                    case "Last Name":
	                        lastName = value;
	                        break;
	                }
	            }
	        }
	        reader.close();
	        // Concatenate first name and last name to get the full holder's name
	        if (firstName != null && lastName != null && provider != null && policyNumber != null) {
	            holderName = firstName + " " + lastName;
	            return new InsuranceInfo(provider, policyNumber, holderName);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	private Node showInsuranceSection(GridPane contentPane) {
	    // Clear the content pane before adding new elements
	    contentPane.getChildren().clear();

	    // Create labels and text fields for insurance section
	    Label insuranceLabel = new Label("Insurance Information");
	    Label providerLabel = new Label("Provider:");
	    TextField providerField = new TextField();
	    Label policyNumberLabel = new Label("Policy Number:");
	    TextField policyNumberField = new TextField();
	    Label holderNameLabel = new Label("Holder Name:");
	    TextField holderNameField = new TextField();

	    // Load insurance data from file
	    InsuranceInfo insuranceInfo = loadInsuranceData(patientId);

	    // Populate text fields with insurance data
	    if (insuranceInfo != null) {
	        providerField.setText(insuranceInfo.getProvider());
	        policyNumberField.setText(insuranceInfo.getPolicyNumber());
	        holderNameField.setText(insuranceInfo.getHolderName());
	    }

	    // Add elements to the content pane
	    contentPane.add(insuranceLabel, 0, 0);
	    contentPane.add(providerLabel, 0, 1);
	    contentPane.add(providerField, 1, 1);
	    contentPane.add(policyNumberLabel, 0, 2);
	    contentPane.add(policyNumberField, 1, 2);
	    contentPane.add(holderNameLabel, 0, 3);
	    contentPane.add(holderNameField, 1, 3);

	    // Return the content pane as a Node
	    return contentPane;
	}

	// private Node showPrescriptionsSection(GridPane contentPane) {
	//     // Clear the content pane before adding new elements
	//     contentPane.getChildren().clear();

	//     // Create labels and list view for prescriptions section
	//     Label prescriptionsLabel = new Label("Prescriptions");
	//     ListView<String> prescriptionsListView = new ListView<>();
	//     // Add prescriptions to the list view (replace the example strings with actual prescription data)
	//     prescriptionsListView.getItems().addAll("Prescription 1", "Prescription 2", "Prescription 3");

	//     // Add elements to the content pane
	//     contentPane.add(prescriptionsLabel, 0, 0);
	//     contentPane.add(prescriptionsListView, 0, 1);

	//     // Return the content pane as a Node
	//     return contentPane;
	// }

    private Node showPrescriptionsSection(GridPane contentPane) {
	    // Clear the content pane before adding new elements
	    contentPane.getChildren().clear();

	    // Create labels and list view for prescriptions section
	    Label prescriptionsLabel = new Label("Prescriptions");
	    ListView<String> prescriptionsListView = new ListView<>();

	    // Load prescriptions data from file
	    List<String> prescriptions = loadPrescriptions(patientId);

	    // Add prescriptions to the list view
	    prescriptionsListView.getItems().addAll(prescriptions);

	    // Add elements to the content pane
	    contentPane.add(prescriptionsLabel, 0, 0);
	    contentPane.add(prescriptionsListView, 0, 1);

	    // Return the content pane as a Node
	    return contentPane;
	}

	private List<String> loadPrescriptions(String patientId) {
	    List<String> prescriptions = new ArrayList<>();
	    String filename = "src/cse360project_milestone2/prescriptions/" + patientId + ".txt";
	    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            prescriptions.add(line);
	        }
	    } catch (IOException e) {
	        // If the file doesn't exist, create a blank file
	        createBlankFile(filename);
	    }
	    return prescriptions;
	}

	// private Node showMedicalHistorySection(GridPane contentPane) {
	//     // Clear the content pane before adding new elements
	//     contentPane.getChildren().clear();

	//     // Create labels and text area for medical history section
	//     Label medicalHistoryLabel = new Label("Medical History");
	//     TextArea medicalHistoryTextArea = new TextArea();
	//     // Add medical history details to the text area (replace the example text with actual medical history data)
	//     medicalHistoryTextArea.setText("Medical history details...");

	//     // Add elements to the content pane
	//     contentPane.add(medicalHistoryLabel, 0, 0);
	//     contentPane.add(medicalHistoryTextArea, 0, 1);

	//     // Return the content pane as a Node
	//     return contentPane;
	// }

    private Node showMedicalHistorySection(GridPane contentPane) {
	    // Clear the content pane before adding new elements
	    contentPane.getChildren().clear();

	    // Create labels and text area for medical history section
	    Label medicalHistoryLabel = new Label("Medical History");
	    TextArea medicalHistoryTextArea = new TextArea();

	    // Load medical history data from file
	    String medicalHistory = loadMedicalHistory(patientId);

	    // Set medical history text in the text area
	    medicalHistoryTextArea.setText(medicalHistory);

	    // Add elements to the content pane
	    contentPane.add(medicalHistoryLabel, 0, 0);
	    contentPane.add(medicalHistoryTextArea, 0, 1);

	    // Return the content pane as a Node
	    return contentPane;
	}

	private void createBlankFile(String filename) {
	    try {
	        File file = new File(filename);
	        file.getParentFile().mkdirs(); // Create directories if they don't exist
	        file.createNewFile(); // Create the file
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	private String loadMedicalHistory(String patientId) {
	    String filename = "src/cse360project_milestone2/medicalhistory/" + patientId + ".txt";
	    StringBuilder medicalHistory = new StringBuilder();
	    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            medicalHistory.append(line).append("\n"); // Append each line of medical history data
	        }
	    } catch (IOException e) {
	        // If the file doesn't exist, create a blank file
	        createBlankFile(filename);
	    }
	    return medicalHistory.toString();
	}

    public static void main(String[] args) {
        launch(args);
    }
}
