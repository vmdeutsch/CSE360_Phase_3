package cse360project_milestone2;

import java.util.Enumeration;
import java.util.Hashtable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DisplayPatientInformation {
	private boolean editable=true;
	public DisplayPatientInformation(boolean editable) {
		this.editable=editable;
	}
	//private Patient kazouku;
	private Hashtable<String,String> data=new Hashtable<>();
	//private void setPatientData(Patient pt) {
			//data.put("CAC",pt.getCAC());
	//}
	private void writeBack() {
		//kazouku.putCAC(data.get("CAC"));
	}
	
	public void showWindow() 
	{
		GridPane contain=new GridPane();
		Enumeration<String> keys=data.keys();
		int row=0;
		while(keys.hasMoreElements()) {
			final String key=keys.nextElement();
			GridPane koto=new GridPane();
			koto.add(new Label(key), 0, 0);
			TextField jf=new TextField();
			String value=data.get(key);
			jf.setText(value);
			koto.add(jf, 1, 0);
			Button save=new Button("update");
			save.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
							data.put(key, jf.getText());
							writeBack();
					}});
			if(editable)
				koto.add(save, 2, 0);
			contain.add(koto, 0, row);
			row++;
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.getDialogPane().setExpandableContent(contain);
		alert.showAndWait();
		
	}
}
