package cse360project_milestone2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewMessage {
	private String recipient;
	
    public void showWindow(MessageSystem input) {
    	//CurrentUser.setUsername("Gavin");
    	String cu=CurrentUser.getUsername();
    	Stage stage = new Stage();
        stage.setTitle("New Message");

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        // Recipient Name Field
        TextField recipientField = new TextField();
        recipientField.setPromptText("Recipient Name");

        // Message Content Area
        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Enter your message...");
        
        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
        	stage.close();
            recipient = recipientField.getText();
            String message = messageArea.getText();
            if (recipient.isEmpty() || message.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Missing Information");
                alert.setContentText("Please enter both a recipient and a message.");
                alert.showAndWait();
                
                return; // Don't proceed with sending
            }
            

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
            String filename = cu + "_" + recipient + "-" + now.format(formatter) + ".txt";
            String messagefiledestination = "src/cse360project_milestone2/messages/" + filename;
            try (FileWriter writer = new FileWriter(messagefiledestination)) {
                writer.write(cu + ": " + message);
                /*Stage oldMessageSystemStage = (Stage) sendButton.getScene().getWindow();
                oldMessageSystemStage.close();
                MessageSystem messageSystem = new MessageSystem();
                Stage messageSystemStage = new Stage();
                messageSystem.start(messageSystemStage);*/
                input.refreshMessageList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            stage.close();
            // You might want to refresh the MessageSystem view here (if needed)
        });
        
         
        // Add components to layout
        root.getChildren().addAll(recipientField, messageArea, sendButton, backButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
