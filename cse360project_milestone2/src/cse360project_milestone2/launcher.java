package appointmentGUI;



import javafx.application.Application;
import javafx.stage.Stage;

public class launcher extends Application {
	 public static void main(String[] args) {
		 launch(args);
	    }

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		/*arg0.setScene(new Scene(null,300,300));
		arg0.show();*/
		Stage st = new Stage();
		Appointments portal=new Appointments();
        portal.start(st);
	}
}
