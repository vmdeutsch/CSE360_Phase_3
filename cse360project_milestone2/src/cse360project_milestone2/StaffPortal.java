package staffPortal;

import java.util.Enumeration;
import java.util.Vector;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class StaffPortal{
	//for testing
	
	private Vector<Patient> patients=new Vector<>();
	
	//public void addPatient(Patient pt) {
	public void addPatient(Patient pt) {

		patients.add(pt);
	}
	public void removePatient(Patient pt) {
		patients.remove(pt);
	}
	public boolean findPatient(Patient pt) {
		Enumeration<Patient> pe=patients.elements();
		while(pe.hasMoreElements()) {
			Patient p=pe.nextElement();
			if(p.equals(pt))
				return true;
		}
		return false;
	}
	
	private String kensaku="";
	private boolean editable=true;
	//set the write permissions, true for yes
	public void setWriteable(boolean writeable) {
		editable=writeable;
	}
	private Label label(String test) {
		Label l=new Label(test);
		l.setStyle("-fx-padding: 16;");
		return l;
	}
	public static String parsePhone(Long phone) {
		String phones=Long.toString(phone);
		if(phones.length()<7) 
			return phones;
		String d1=phones.substring(0,3);
		String d2=phones.substring(3,6);
		return d1+"-"+d2+"-"+phones.substring(6);
	}
	private void atarashi_risuto(GridPane ku) {
		int row=1;
		ku.getChildren().clear();
		GridPane dcont=new GridPane();
		kensaku_chihou_suru(ku);
		dcont.setGridLinesVisible(true);
		dcont.add(label("Patient Name"), 0, 0);
		dcont.add(label("Date Of Birth"), 1, 0);
	    dcont.add(label("Pharmacy"), 2, 0);
	    dcont.add(label("Phone Number"), 3, 0);
		Enumeration<Patient> risto=patients.elements();
		
		while(risto.hasMoreElements()) {
			Patient hito=risto.nextElement();
			final String namae=hito.getLastName()+", "+hito.getFirstName();
			//namae=hito.getFullName();
			if(namae.toLowerCase().contains(kensaku)) {
					Hyperlink rinku=new Hyperlink(namae);
					rinku.setStyle("-fx-padding: 16;");
				    rinku.setOnAction(e-> {
								DisplayPatientInformation info=new DisplayPatientInformation(editable,hito);
								info.showWindow();
							});
					dcont.add(label(hito.getDOB()), 1, row);
				    dcont.add(label(hito.getPharmacy()), 2, row);
				    dcont.add(label(parsePhone(hito.getPhoneNumber())), 3, row);
					dcont.add(rinku, 0, row);
					
					
					row++;
			}
		}
		ku.add(dcont, 0, 1);
	}
	private GridPane subete=new GridPane();
	private void kensaku_chihou_suru(GridPane ku){
		GridPane chihou=new GridPane();
		TextField kensakuchihou=new TextField("");
		chihou.add(kensakuchihou, 0, 0);
		Button suru=new Button("search");
		suru.setOnAction(e-> {
				kensaku=kensakuchihou.getText();
				atarashi_risuto(subete);
			});
		chihou.add(suru, 1, 0);
		ku.add(chihou, 0, 0);
	}

	public void start() {
		// TODO Auto-generated method stub
		addPatient(launcher.readPatient("/home/ivan/Downloads/JDoe512241251.txt"));
		
		
	
		subete.setGridLinesVisible(true);
		atarashi_risuto(subete);
		ScrollPane scroll=new ScrollPane(subete);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.getDialogPane().setContent(scroll);
		alert.setResizable(true);
		alert.showAndWait();
	}

}
