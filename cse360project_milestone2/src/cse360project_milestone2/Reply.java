//Bhavya Patel

package cse360project_milestone2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.File;

public class Reply {
    private String filename; 
    public Reply(String filename) {
        this.filename = filename;
    }
    public void showWindow() {
    	String cu=CurrentUser.getUsername();
    	Stage stage = new Stage();
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        TextArea conversationArea = new TextArea();
        conversationArea.setEditable(false); 
        loadConversation(conversationArea); 
        TextArea replyArea = new TextArea();
        replyArea.setPromptText("Enter your reply...");
        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
            String replyMessage = replyArea.getText().trim();
            if (replyMessage.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Reply");
                alert.setContentText("Please enter a reply before sending.");
                alert.showAndWait();
                return;
            }
            try{
            	System.getProperty("user.dir");
                String messagefiledestination = "src/cse360project_milestone2/messages/"+filename;
            	try (FileWriter writer = new FileWriter(messagefiledestination, true)) {
					writer.write("\n" + cu + ": " + replyMessage);
					stage.close();
				}
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> stage.close());
        root.getChildren().addAll(conversationArea, replyArea, sendButton, backButton);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    private void loadConversation(TextArea conversationArea) {
        try {
        	System.getProperty("user.dir");
            String messagefiledestination = "src/cse360project_milestone2/messages/"+filename;
            File file = new File(messagefiledestination);  
            if (file.exists()) { // Check if the file exists
                List<String> lines = Files.readAllLines(file.toPath());
                String conversation = String.join("\n", lines);
                conversationArea.setText(conversation);
            } else {
                conversationArea.setText("File not found: " + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
            conversationArea.setText("Error loading conversation");
        }
    }  
}
