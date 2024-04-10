package cse360project_milestone2;

//import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Appointments{
	
    private Map<String, Map<String, String>> appointments;
    private String currUser; //store current user temp var
    private String currRole; // store current Role in temp 
    private String currPatient;
    private String selectedAppointment;


 
    public void start(Stage primaryStage) {
        // Initialize appointments
    	appointments = new HashMap<>();
        //get user
        currUser = CurrentUser.getUsername();
        currPatient = CurrentUser.getCurrentPatient();
        
        //set user role
        currRole = CurrentUser.getRole();
    	initializeAppointments();
        // UI Components
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        // Greeting Label
        Label greetingLabel = new Label("Greetings " + currUser);
        borderPane.setTop(greetingLabel);

        // Appointment List
        ListView<String> appointmentListView = new ListView<>();
        updateAppointmentListView(appointmentListView);
        appointmentListView.setOnMouseClicked(event -> {
            selectedAppointment = appointmentListView.getSelectionModel().getSelectedItem();
            if (selectedAppointment != null) {
                borderPane.setCenter(createAppointmentPane(selectedAppointment));
            }
        });
        borderPane.setLeft(appointmentListView);

        // Buttons
        Button saveNurseButton = new Button("Save Nurse Questionnaire and Vitals");
        Button saveDoctorButton = new Button("Save Doctor's Notes");
        Button backButton = new Button("Back");
        Button logoutButton = new Button("Logout");
        Button createNewAppointmentButton = new Button("Create New Appointment");

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(saveNurseButton, saveDoctorButton, backButton, logoutButton, createNewAppointmentButton);
        borderPane.setBottom(buttonBox);
        BorderPane.setMargin(buttonBox, new Insets(10, 0, 0, 0));

        saveDoctorButton.setOnAction(event -> {
            if (!(currRole == "Doctor")) {
                showAlert("You are not authorized to edit the Doctor's Notes.");
            } else {
                TextArea doctorNotesArea = (TextArea) borderPane.getCenter().lookup("#doctorNotesArea");
                if (doctorNotesArea.getText().isEmpty()) {
                    showAlert("Doctor's Examination cannot be empty!");
                } else {
                    saveAppointment(selectedAppointment);
                }
            }
        });

        saveNurseButton.setOnAction(event -> {
        	  if (currRole == "Patient") {
        	      showAlert("You are not authorized to edit this field.");
        	  } else {
        	      // Get references to TextArea and TextFields
        	      TextArea nurseQuestionnaireArea = (TextArea) borderPane.getCenter().lookup("#nurseQuestionnaireArea");
        	      TextField heartRateField = (TextField) borderPane.getCenter().lookup("#heartRateField");
        	      TextField temperatureField = (TextField) borderPane.getCenter().lookup("#temperatureField");
        	      TextField bloodPressureField = (TextField) borderPane.getCenter().lookup("#bloodPressureField");
        	      TextField pulseRateField = (TextField) borderPane.getCenter().lookup("#pulseRateField");

        	      // Check if any fields are empty
        	      if (nurseQuestionnaireArea.getText().isEmpty() ||
        	          heartRateField.getText().isEmpty() ||
        	          temperatureField.getText().isEmpty() ||
        	          bloodPressureField.getText().isEmpty() ||
        	          pulseRateField.getText().isEmpty()) {
        	          showAlert("Nurse vitals and questionnaire cannot be empty!");
        	      } else {
        	          // Save the appointment
        	          saveAppointment(selectedAppointment);
        	      }
        	  }
        	});


        backButton.setOnAction(event -> {
            String role = CurrentUser.getRole();
            if (role.equals("Patient")) {
                PatientPortal patientPortal = new PatientPortal(); 
                Stage patientPortalStage = new Stage();
                patientPortal.start(patientPortalStage);
            } else if (role.equals("Doctor") || role.equals("Nurse")) {
                StaffPortal staffPortal = new StaffPortal(); 
                staffPortal.start();
            }
            primaryStage.close(); 
     });

     logoutButton.setOnAction(event -> {
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
        createNewAppointmentButton.setOnAction(event -> {
            // Create New Appointment button clicked
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("New Appointment");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the appointment date (MM/DD/YY):");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newAppointmentDate -> {
                appointments.put(newAppointmentDate, new HashMap<>());
                updateAppointmentListView(appointmentListView);
            });
        });

        Scene scene = new Scene(borderPane, 800, 600); // Set larger initial size
        primaryStage.setScene(scene);
        primaryStage.setTitle("Doctor Appointment Page");
        primaryStage.show();
    }

    private void saveAppointment(String selectedAppointment) {
        Map<String, String> appointmentFields = appointments.get(selectedAppointment);

        // Update appointment fields with new values from UI
        Scene scene = Stage.getWindows().get(0).getScene(); // Get the scene
        TextField heartRateField = (TextField) scene.lookup("#heartRateField");
        TextField temperatureField = (TextField) scene.lookup("#temperatureField");
        TextField bloodPressureField = (TextField) scene.lookup("#bloodPressureField");
        TextField pulseRateField = (TextField) scene.lookup("#pulseRateField");
        TextArea doctorNotesArea = (TextArea) scene.lookup("#doctorNotesArea");
        TextArea nurseQuestionnaireArea = (TextArea) scene.lookup("#nurseQuestionnaireArea");

        appointmentFields.put("Heart Rate", heartRateField.getText());
        appointmentFields.put("Temperature", temperatureField.getText());
        appointmentFields.put("Blood Pressure", bloodPressureField.getText());
        appointmentFields.put("Pulse Rate", pulseRateField.getText());
        appointmentFields.put("Doctor's Notes", doctorNotesArea.getText());
        appointmentFields.put("Nurse Questionnaire", nurseQuestionnaireArea.getText());
        
        try {
            Path directoryPath = Paths.get("src/cse360project_milestone2/appointments"); 
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            String fileName = currPatient.replaceAll("\\s+", "") + "_" + selectedAppointment.replaceAll("/", "") + ".txt";
            Path filePath = directoryPath.resolve(fileName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()));
            for (Map.Entry<String, String> entry : appointmentFields.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
            writer.close();
            showAlert("Appointment saved successfully!");
        } catch (IOException e) {
            showAlert("Error saving appointment: " + e.getMessage());
        }
    }


    private void updateAppointmentListView(ListView<String> appointmentListView) {
        appointmentListView.getItems().clear();
        appointmentListView.getItems().addAll(appointments.keySet());
    }

    private GridPane createAppointmentPane(String appointmentDate) {
        GridPane appointmentPane = new GridPane();
        appointmentPane.setPadding(new Insets(10));
        appointmentPane.setVgap(8);
        appointmentPane.setHgap(10);

        Map<String, String> appointmentFields = appointments.get(appointmentDate);

        // Patient Vitals Section
        Label patientVitalsLabel = new Label("Vitals:");
        appointmentPane.add(patientVitalsLabel, 0, 0);

        Label heartRateLabel = new Label("Heart Rate:");
        appointmentPane.add(heartRateLabel, 0, 1);
        TextField heartRateField = new TextField();
        heartRateField.setId("heartRateField"); // Set an ID for lookup
        heartRateField.setText(appointmentFields.getOrDefault("Heart Rate", ""));
        appointmentPane.add(heartRateField, 1, 1);

        Label temperatureLabel = new Label("Temperature:");
        appointmentPane.add(temperatureLabel, 0, 2);
        TextField temperatureField = new TextField();
        temperatureField.setId("temperatureField"); // Set an ID for lookup
        temperatureField.setText(appointmentFields.getOrDefault("Temperature", ""));
        appointmentPane.add(temperatureField, 1, 2);

        Label bloodPressureLabel = new Label("Blood Pressure:");
        appointmentPane.add(bloodPressureLabel, 0, 3);
        TextField bloodPressureField = new TextField();
        bloodPressureField.setId("bloodPressureField"); // Set an ID for lookup
        bloodPressureField.setText(appointmentFields.getOrDefault("Blood Pressure", ""));
        appointmentPane.add(bloodPressureField, 1, 3);

        Label pulseRateLabel = new Label("Pulse Rate:");
        appointmentPane.add(pulseRateLabel, 0, 4);
        TextField pulseRateField = new TextField();
        pulseRateField.setId("pulseRateField"); // Set an ID for lookup
        pulseRateField.setText(appointmentFields.getOrDefault("Pulse Rate", ""));
        appointmentPane.add(pulseRateField, 1, 4);

        // Nurse Questionnaire Section
        Label nurseQuestionnaireLabel = new Label("Nurse Questionnaire:");
        appointmentPane.add(nurseQuestionnaireLabel, 0, 5);

        TextArea nurseQuestionnaireArea = new TextArea();
        nurseQuestionnaireArea.setId("nurseQuestionnaireArea"); // Set an ID for lookup
        nurseQuestionnaireArea.setText("Nurse Questionnaire:\n" +
                "- What is your eye color?\n" +
                "- What is your hair color?\n" +
                "- Any allergies?");
        appointmentPane.add(nurseQuestionnaireArea, 1, 5, 2, 1);

        // Doctor's Examination Section
        Label doctorExamLabel = new Label("Doctor's Examination:");
        appointmentPane.add(doctorExamLabel, 0, 7);
        
        
        TextArea doctorNotesArea = new TextArea();
        doctorNotesArea.setId("doctorNotesArea"); // Set an ID for lookup
        doctorNotesArea.setText(appointmentFields.getOrDefault("Doctor's Notes", ""));
        appointmentPane.add(doctorNotesArea, 1, 7, 2, 1);

        return appointmentPane;
    }

    private void initializeAppointments() {
        File directory = new File("src/cse360project_milestone2/appointments");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String username = "";
                    String date = "";
                    int underscoreIndex = fileName.lastIndexOf("_");
                    int dotIndex = fileName.lastIndexOf(".");
                    if (underscoreIndex != -1 && dotIndex != -1 && dotIndex > underscoreIndex) {
                        username = fileName.substring(0, underscoreIndex);
                        date = fileName.substring(underscoreIndex + 1, dotIndex);
                    } else {
                        // Log an error or handle the invalid file name
                    }
                    if(currRole.equals("Doctor")|| currRole.equals("Nurse")||currRole.equals("Patient")){
                        if (currPatient.equals(username)) {
                                appointments.put(date, readAppointmentFromFile(file));
                        }
                    }
                }
            }
        }
    }

    private Map<String, String> readAppointmentFromFile(File file) {
        Map<String, String> appointmentFields = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    appointmentFields.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointmentFields;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
}
