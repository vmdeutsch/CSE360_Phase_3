package cse360project_milestone2;

import java.util.Enumeration;
import java.util.Vector;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StaffPortal extends Application{
	 public static void main(String[] args) {
	        launch(args);
	    }
	private Vector<String> patients=new Vector<>();
	//private Vector<Patient> patients;
	
	//public void addPatient(Patient pt) {
	public void addPatient(String namae) {
		
		patients.add(namae);
		//patients.add(pt);
	}
	
	
	private String kensaku="";
	private boolean editable=true;
	private void atarashi_risuto(GridPane ku) {
		int row=1;
		ku.getChildren().clear();
		kensaku_chihou_suru(ku);
		Enumeration<String> risto=patients.elements();
		while(risto.hasMoreElements()) {
			//Patient hito=risto.nextElement();
			final String namae=risto.nextElement();
			//namae=hito.getFullName();
			if(namae.toLowerCase().contains(kensaku)) {
					GridPane retsu=new GridPane();
					Button rinku=new Button(namae);
					
				    rinku.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {
								DisplayPatientInformation info=new DisplayPatientInformation(editable);
								
								//info.showWindow(hito);
								info.showWindow();
							}});
					//retsu.add(new Label(hito.getDOB()), 1, 0);
				    //retsu.add(new Label(hito.getPharmacy()), 2, 0);
				    //retsu.add(new Label(hito.getPhoneNumber()), 3, 0);
					retsu.add(rinku, 0, 0);
					ku.add(retsu, 0, row);
					row++;
			}
		}
	}
	private void kensaku_chihou_suru(GridPane ku){
		GridPane chihou=new GridPane();
		TextField kensakuchihou=new TextField();
		chihou.add(kensakuchihou, 0, 0);
		Button suru=new Button("検索する");
		suru.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				kensaku=kensakuchihou.getText();
			}});
		chihou.add(suru, 1, 0);
		ku.add(chihou, 0, 0);
	}
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		addPatient("rufuria");
		addPatient("yamamoto");
		GridPane subete=new GridPane();
		atarashi_risuto(subete);
		arg0.setScene(new Scene(subete, 1000, 250));
        arg0.show();
	}

}
