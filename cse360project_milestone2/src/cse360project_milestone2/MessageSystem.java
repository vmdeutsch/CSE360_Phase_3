package cse360project_milestone2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;

import javafx.scene.input.MouseEvent;


public class MessageSystem extends Application {
	private VBox messageList;
	private String filename;
    @Override
    public void start(Stage primaryStage) {
        // Root Layout
        BorderPane root = new BorderPane();

        // Left Pane (Navigation)
        VBox leftPane = new VBox();
        leftPane.setPadding(new Insets(20));
        leftPane.setSpacing(20);

        // Add navigation buttons (replace with your actual buttons)
        leftPane.getChildren().addAll(
                new Button("Dashboard"),
                new Button("Appointments"),
                new Button("Messages"),
                new Button("Logout")
        );

        // Center Pane (Message List)
        messageList = new VBox();
        messageList.setPadding(new Insets(20));
        messageList.setSpacing(10);
        loadMessagesFromFiles();

        // Bottom Right (New Message Button)
        Button newMessageButton = new Button("New Message");
        newMessageButton.setAlignment(Pos.BOTTOM_RIGHT);
        BorderPane.setAlignment(newMessageButton, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(newMessageButton, new Insets(10));

        // Assemble Layout
        root.setLeft(leftPane);
        root.setCenter(messageList);
        root.setBottom(newMessageButton);
        
        newMessageButton.setOnAction(event -> {
            NewMessage newMessage = new NewMessage(); 
            newMessage.showWindow(this);
            //refreshMessageList();
        });

        // Scene and Stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("PediatrEase Clinic");
        primaryStage.setScene(scene);
        primaryStage.show();
    
    }
    
    
    public void loadMessagesFromFiles() {
    	//CurrentUser.setUsername("Gavin");
    	String cu=CurrentUser.getUsername();
    	System.getProperty("user.dir");
    	String messagefiledestination = "src/cse360project_milestone2/messages/";
        File directory = new File(messagefiledestination); 
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt")&& name.contains(CurrentUser.getUsername()));

        if (files != null && files.length > 0) {

            for (File file : files) {
                filename = file.getName();
                String fname=filename;
                System.out.println("Reading file: " + filename);
                String[] parts = filename.split("_");
                String temp= parts[1];
                String[] part2 = temp.split("-");
                String recievername = part2[0];
                String date = part2[1].substring(0, 8); // Assuming MMDDHHmm format
                messageList.getChildren().add(createMessageThread(recievername, date, fname));
            }
        } else {
            // Display "No Messages" if no text files found
            Label noMessagesLabel = new Label("No Messages");
            noMessagesLabel.setAlignment(Pos.CENTER);
            messageList.getChildren().add(noMessagesLabel); 
        }
    }
    
    public void refreshMessageList(){
        messageList.getChildren().clear(); 
        loadMessagesFromFiles();
    }


 // Helper method to create message thread items
 public HBox createMessageThread(String sender, String date, String name) {
     HBox thread = new HBox();
     thread.setPadding(new Insets(5));
     thread.setSpacing(10);
     thread.setStyle("-fx-background-color: lightgray;");
     thread.getChildren().addAll(new Label(sender), new Label(date));

     // Add event handling to open Reply window
     thread.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
         Reply replyWindow = new Reply(name);
         replyWindow.showWindow();
     });

     return thread;
 }

 // ... (rest of the code - same as before)
    

    public static void main(String[] args) {
        launch(args);
    }
}