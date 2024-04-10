//Bhavya Patel

package cse360project_milestone2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.input.MouseEvent;

public class MessageSystem {
	private VBox messageList;
	private String filename;
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        VBox leftPane = new VBox();
        leftPane.setPadding(new Insets(20));
        leftPane.setSpacing(20);
        leftPane.getChildren().addAll(
                new Button("Dashboard"),
                new Button("Messages"),
                new Button("Logout")
        );
        messageList = new VBox();
        messageList.setPadding(new Insets(20));
        messageList.setSpacing(10);
        loadMessagesFromFiles();
        Button dashboardButton = (Button) leftPane.getChildren().get(0);
        dashboardButton.setOnAction(event -> {
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
        Button newMessageButton = new Button("New Message");
        newMessageButton.setAlignment(Pos.BOTTOM_RIGHT);
        BorderPane.setAlignment(newMessageButton, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(newMessageButton, new Insets(10));
        root.setLeft(leftPane);
        root.setCenter(messageList);
        root.setBottom(newMessageButton);
        newMessageButton.setOnAction(event -> {
            NewMessage newMessage = new NewMessage(); 
            newMessage.showWindow(this);
        });
        Button logoutButton =(Button) leftPane.getChildren().get(2);
        logoutButton.setOnAction(e->{
			if(primaryStage!=null) {
				primaryStage.close();
			    Login login = new Login();
				CurrentUser.setUsername(null);
				login.start(primaryStage);
			}
		});
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("PediatrEase Clinic");
        primaryStage.setScene(scene);
        primaryStage.show();    
    }
    
    public void loadMessagesFromFiles() {
    	System.getProperty("user.dir");
    	String messagefiledestination = "src/cse360project_milestone2/messages/";
        File directory = new File(messagefiledestination); 
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt")&& name.contains(CurrentUser.getUsername()));
        if (files != null && files.length > 0) {
            for (File file : files) {
                filename = file.getName();
                String fname=filename;
                String[] parts = filename.split("_");
                String temp= parts[1];
                String[] part2 = temp.split("-");
                String recievername = part2[0];
                String date = part2[1].substring(0, 8);
                messageList.getChildren().add(createMessageThread(recievername, date, fname));
            }
        } 
        else {
            Label noMessagesLabel = new Label("No Messages");
            noMessagesLabel.setAlignment(Pos.CENTER);
            messageList.getChildren().add(noMessagesLabel); 
        }
    }
    
    public void refreshMessageList(){
        messageList.getChildren().clear(); 
        loadMessagesFromFiles();
    }

    public HBox createMessageThread(String sender, String date, String name) {
    	HBox thread = new HBox();
	    thread.setPadding(new Insets(5));
	    thread.setSpacing(10);
	    thread.setStyle("-fx-background-color: lightgray;");
	    thread.getChildren().addAll(new Label(sender), new Label(date));
	    thread.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
	         Reply replyWindow = new Reply(name);
	         replyWindow.showWindow();
	     });
	     return thread;
    }
}   
