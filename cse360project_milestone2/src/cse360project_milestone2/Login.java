/* Vaughn Deutsch
 * March 9, 2023
 * Login GUI for the CSE360 project, milestone 2.
 */

package cse360project_milestone2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class Login extends Application{
	
    private final List<Account> validAccounts = new ArrayList<>();
	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        // Create grid pane layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        
        /*
        StaffPortal staffPortal = new StaffPortal();
        staffPortal.start();
        */

        // Declartion of Labels:
        // The username label.
        Label usernameLabel = new Label("Username:");
        //GridPane.setConstraints(usernameLabel, 0, 0);
        
        // The password label.
        Label passwordLabel = new Label("Password:");
        //GridPane.setConstraints(passwordLabel, 0, 1);

        //Text Field declarations.
        // Where the username is actually entered.
        TextField usernameInput = new TextField();
        //GridPane.setConstraints(usernameInput, 1, 0);

        // Where the password is actually entered
        PasswordField passwordInput = new PasswordField();
        //GridPane.setConstraints(passwordInput, 1, 1);

        // Button declarations.
        // The login button.
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String enteredUsername = usernameInput.getText();
            String enteredPassword = passwordInput.getText();
            boolean loggedIn = false;
            String accountType = null;

            // Check if the account exists based on the file system
            File patientAccountFile = new File("src/cse360project_milestone2/accounts/patients/" + enteredUsername + ".txt");
            File doctorAccountFile = new File("src/cse360project_milestone2/accounts/doctors/" + enteredUsername + ".txt");
            File nurseAccountFile = new File("src/cse360project_milestone2/accounts/nurses/" + enteredUsername + ".txt");

            if (patientAccountFile.exists()) {
                loggedIn = checkCredentials(patientAccountFile, enteredPassword);
                accountType = "patient";
            } else if (doctorAccountFile.exists()) {
                loggedIn = checkCredentials(doctorAccountFile, enteredPassword);
                accountType = "doctor";
            } else if (nurseAccountFile.exists()) {
                loggedIn = checkCredentials(nurseAccountFile, enteredPassword);
                accountType = "nurse";
            }

            if (loggedIn) {
                // Close the login GUI
                CurrentUser.setUsername(enteredUsername);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                if (accountType.equals("patient")) {
                    PatientPortal patientPortal = new PatientPortal();
                    patientPortal.start(primaryStage);
                    // Display login confirmation for patient
                } else {
                    // Open the staff portal for doctors and nurses
                    StaffPortal staffPortal = new StaffPortal();
                    try {
                        staffPortal.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                // Login failed
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Invalid Username or Password");
                alert.setContentText("Please check your username and password and try again.");
                alert.showAndWait();
            }
        });

        
        // The create and account button
        Button createaccountButton = new Button("Create an Account");
        GridPane.setConstraints(createaccountButton, 1, 3);
        createaccountButton.setOnAction(e -> {
            primaryStage.close(); // Close login window
            createAccount(primaryStage); // Open create account window
        });
        
        Image image = new Image(getClass().getResourceAsStream("images/login.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300); // Adjust width as needed
        imageView.setFitHeight(331);
        GridPane.setConstraints(imageView, 1, 0);
        gridPane.add(imageView, 0, 0, 1, 5);
        
        GridPane.setColumnSpan(imageView, 1); // Span only one column for the image

        // the following displays the labels, buttons, and text fields declared above.
        gridPane.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, createaccountButton);
        Scene scene = new Scene(gridPane, 600, 400);
        GridPane.setConstraints(usernameLabel, 2, 0); // Adjust column to 1
        GridPane.setConstraints(usernameInput, 3, 0); // Adjust column to 2
        GridPane.setConstraints(passwordLabel, 2, 1); // Adjust column to 1
        GridPane.setConstraints(passwordInput, 3, 1); // Adjust column to 2
        GridPane.setConstraints(loginButton, 3, 2); // Adjust column to 2
        GridPane.setConstraints(createaccountButton, 3, 3); // Adjust column to 2

        primaryStage.setScene(scene);

        primaryStage.show();
        
    }
	
	private void createAccount(Stage primaryStage) {
        Stage createAccountStage = new Stage();
        createAccountStage.setTitle("Create Account");

        // Create grid pane layout for the create account form
        GridPane createAccountPane = new GridPane();
        createAccountPane.setPadding(new Insets(20, 20, 20, 20));
        createAccountPane.setVgap(10);
        createAccountPane.setHgap(10);
        
        // Label declarations:
        Label firstnameLabel = new Label("First Name:");
        GridPane.setConstraints(firstnameLabel, 0, 0);
        
        Label lastnameLabel = new Label("Last Name:");
        GridPane.setConstraints(lastnameLabel, 0, 1);
        
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 2);
        
        Label dobLabel = new Label("Date of Birth:");
        GridPane.setConstraints(dobLabel, 0, 3);
        
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 4);
        
        Label phonenumberLabel = new Label("Phone Number:");
        GridPane.setConstraints(phonenumberLabel, 0, 5);

        Label addressLabel = new Label("Address:");
        GridPane.setConstraints(addressLabel, 0, 6);
        
        Label insuranceLabel = new Label("Insurance Provider:");
        GridPane.setConstraints(insuranceLabel, 0, 7);
        
        Label insuranceIDLabel = new Label("Insurance ID Number:");
        GridPane.setConstraints(insuranceIDLabel, 0, 8);
        
        Label pharmacyLabel = new Label("Pharmacy:");
        GridPane.setConstraints(pharmacyLabel, 0, 9);
        
        // Text field declarations:
        TextField firstnameInput = new TextField();
        GridPane.setConstraints(firstnameInput, 1, 0);
        
        TextField lastnameInput = new TextField();
        GridPane.setConstraints(lastnameInput, 1, 1);
        
        TextField passwordInput = new TextField();
        GridPane.setConstraints(passwordInput, 1, 2);
        
        TextField dobInput = new TextField();
        GridPane.setConstraints(dobInput, 1, 3);
        
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 4);
        
        TextField phonenumberInput = new TextField();
        GridPane.setConstraints(phonenumberInput, 1, 5);
        
        TextField addressInput = new TextField();
        GridPane.setConstraints(addressInput, 1, 6);
        
        TextField insuranceInput = new TextField();
        GridPane.setConstraints(insuranceInput, 1, 7);
        
        TextField insuranceIDInput = new TextField();
        GridPane.setConstraints(insuranceIDInput, 1, 8);
        
        TextField pharmacyInput = new TextField();
        GridPane.setConstraints(pharmacyInput, 1, 9);
        
        // Button declarations:
        Button createaccountButton = new Button("Create Account");
        GridPane.setConstraints(createaccountButton, 1, 10);
        createaccountButton.setOnAction(e -> {
        	String firstname = firstnameInput.getText();
            String lastname = lastnameInput.getText();
            String password = passwordInput.getText();
            String email = emailInput.getText();
            String phonenumberStr = phonenumberInput.getText(); // Phone number as String
            String insuranceProvider = insuranceInput.getText();
            String dob = dobInput.getText();
            String address = addressInput.getText();
            String insuranceIDString = insuranceIDInput.getText();
            String pharmacy = pharmacyInput.getText();
            long phonenumber = 0;
            
            if (!phonenumberStr.isEmpty()) {
                try {
                    phonenumber = Long.parseLong(phonenumberStr); //
                } catch (NumberFormatException ex) {
                    // phonenumberInput.getText() is not convertible to int
                    // Handle the error here, such as showing a dialog box
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Invalid Phone Number");
                    alert.setHeaderText("Phone Number Format Error");
                    alert.setContentText("Please enter a valid phone number.");
                    alert.showAndWait();
                    return; // Exit the method without proceeding further
                }
            }
            	
            if (firstname.isEmpty() || lastname.isEmpty() || password.isEmpty() || email.isEmpty() || dob.isEmpty() ||
                    phonenumberStr.isEmpty() || address.isEmpty() || insuranceProvider.isEmpty() || insuranceIDString.isEmpty() || pharmacy.isEmpty()) {
                    // Show dialog box for missing information
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Create Account Error");
                    alert.setHeaderText("Incomplete Information");
                    alert.setContentText("Please fill in all fields.");
                    alert.showAndWait();
                } else {
                    // Proceed to create the account
                    int insuranceID = Integer.parseInt(insuranceIDString);
                    //int phonenumber = Integer.parseInt(phonenumberStr); // Convert String to int
                    char firstChar = firstname.charAt(0);
                    String username = Character.toUpperCase(firstChar) + lastname + phonenumber;

                    // Create new Account object
                    Patient newAccount = new Patient(username, password, firstname, lastname, email, phonenumber, insuranceProvider, insuranceID, dob, address, pharmacy);
                    System.out.println(newAccount);
                    validAccounts.add(newAccount);
                    
                    String fileName = "src/cse360project_milestone2/accounts/patients/" + username + ".txt";
                    try {
                        File file = new File(fileName);
                        if (file.createNewFile()) {
                            System.out.println("File created: " + file.getName());
                            try (FileWriter writer = new FileWriter(file)) {
                                writer.write(newAccount.toString()); // Write account details to file
                            }
                        } else {
                            System.out.println("File already exists.");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    
                    createAccountStage.close(); // Close create account window
                    primaryStage.show(); // Show login window
                }
        });

        createAccountPane.getChildren().addAll(firstnameLabel, lastnameLabel, passwordLabel, dobLabel, emailLabel, phonenumberLabel, addressLabel, insuranceLabel, insuranceIDLabel, firstnameInput, passwordInput,  lastnameInput, dobInput, emailInput, phonenumberInput, addressInput, insuranceInput, insuranceIDInput, pharmacyLabel, pharmacyInput, createaccountButton);
        Scene scene = new Scene(createAccountPane, 400, 400);
        createAccountStage.setScene(scene);
        createAccountStage.show();
    }
	
	private boolean checkCredentials(File accountFile, String enteredPassword) {
	    try (BufferedReader reader = new BufferedReader(new FileReader(accountFile))) {
	        Map<String, String> accountDetails = new HashMap<>();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            // Split the line into key and value using colon (:) as delimiter
	            String[] parts = line.split(":");
	            if (parts.length == 2) {
	                String key = parts[0].trim();
	                String value = parts[1].trim();
	                accountDetails.put(key, value);
	            }
	        }
	        // Check if all necessary fields are present
	        if (accountDetails.containsKey("Password")) {
	            String storedPassword = accountDetails.get("Password");
	            // Check if entered password matches stored password
	            if (enteredPassword.equals(storedPassword)) {
	                return true; // Passwords match, login successful
	            }
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    return false; // Login failed
	}
	
    public static void main(String[] args) {
        launch(args);
    }
}
