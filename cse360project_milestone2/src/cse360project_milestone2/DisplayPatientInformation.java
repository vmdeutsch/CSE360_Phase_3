package staffPortal;

import java.util.Enumeration;
import java.util.Hashtable;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DisplayPatientInformation {
	private boolean editable=true;
	private String file;
	public DisplayPatientInformation(boolean editable,Patient pfam,String file) {
		this.editable=editable;
		this.kazouku=pfam;
		setPatientData(kazouku);
		this.file=file;
	}
	private Patient kazouku;
	private Hashtable<String,String> data=new Hashtable<>();
	private void setPatientData(Patient pt) {
		    data.clear();
		    data.put("first name",pt.getFirstName());
		    data.put("last name",pt.getLastName());
			data.put("username", pt.getUsername());
			data.put("DOB",pt.getDOB());
			data.put("email",pt.getEmail());
			data.put("insurance provider",pt.getInsuranceProvider());
			data.put("insurance id",Long.toString(pt.getInsuranceID()));
			data.put("password",pt.getPassword());
			data.put("address",pt.getAddress());
			data.put("phone",StaffPortal.parsePhone(pt.getPhoneNumber()));
			data.put("pharmacy", pt.getPharmacy());

		
	}
	private void writeBack() {
		try {

		
		kazouku.setFirstName(data.get("first name"));
		kazouku.setlastName(data.get("last name"));
		kazouku.setDOB(data.get("DOB"));
		kazouku.setEmail(data.get("email"));
		kazouku.setInsuranceProvider(data.get("insurance provider"));
		kazouku.setPassword(data.get("password"));
		kazouku.setAddress(data.get("address"));
		kazouku.setUsername(data.get("username"));
		kazouku.setPharmacy(data.get("pharmacy"));
		kazouku.setInsuranceID((int)Long.parseLong(data.get("insurance id")));
		kazouku.setPhoneNumber(Long.parseLong(data.get("phone").replace("-","")));
        } catch (NumberFormatException e) {
            showAlert("Error", "Phone number and insurance id must be all digits and or dashes");
        }
        //write data to file
        if (file != null)
            StaffPortal.writePatient(file, kazouku);
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
			if(key.equals("password"))
				jf=new PasswordField();
			TextField copy=jf;
			String value=data.get(key);
			jf.setText(value);
			koto.add(jf, 1, 0);
			
			jf.setOnKeyPressed(e->{	
					data.put(key, copy.getText());
			});
			jf.setOnKeyReleased(e->{
				data.put(key, copy.getText());
			});
			
			contain.add(koto, 0, row);
			row++;
		}
		Button save=new Button("update");
		save.setOnAction(e-> {writeBack();});
	    if(editable)
	    	contain.add(save, 0, row);
		Alert alert = new Alert(AlertType.INFORMATION);
      
		alert.getDialogPane().setContent(contain);
		alert.getDialogPane().addEventHandler(KeyEvent.ANY, e->{
		    	if(e.getCode()==KeyCode.ENTER) {
		    		e.consume();
		    		writeBack();
		    		}

		    });
		alert.showAndWait();
		
		
	}
	
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
       
        alert.showAndWait();
    }
}
