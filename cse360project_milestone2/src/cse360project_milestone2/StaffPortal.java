package staffPortal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;



import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class StaffPortal{
	//for testing
	
	private Vector<Patient> patients=new Vector<>();
	private Hashtable<Patient,String> files=new Hashtable<>();
	private Alert session=null;
	
	//public void addPatient(Patient pt) {
	public void addPatientFile(String file) {
		Patient p=readPatient(file);
		if(p==null)
			return;
		patients.add(p);
		files.put(p, file);
	}
	public void addPatient(Patient p) {
		patients.add(p);
	}
	public void addFileForPatient(Patient p,String file) {
		if(!patients.contains(p))
			return;
		files.put(p, file);
	}
	public void removePatient(Patient pt) {
		patients.remove(pt);
		if(files.contains(pt)) {
			files.remove(pt);
		}
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
	public Patient findPatientByUsername(String uname) 
	{
		Enumeration<Patient> pe=patients.elements();
		while(pe.hasMoreElements()) {
			Patient p=pe.nextElement();
			if(p.getUsername().equals(uname))
				return p;
		}
		return null;
	}
	public Patient findPatientByPhone(long phone) 
	{
		Enumeration<Patient> pe=patients.elements();
		while(pe.hasMoreElements()) {
			Patient p=pe.nextElement();
			if(p.getPhoneNumber()==phone)
				return p;
		}
		return null;
	}
	public Patient findPatientByInsuranceId(long id) 
	{
		Enumeration<Patient> pe=patients.elements();
		while(pe.hasMoreElements()) {
			Patient p=pe.nextElement();
			if(p.getInsuranceID()==id)
				return p;
		}
		return null;
	}
	public Patient findByFileName(String name) {
		Enumeration<String> dekey=files.elements();
		Enumeration<Patient> pe=files.keys();
		while(dekey.hasMoreElements()) {
			String path=dekey.nextElement();
			Patient sres=pe.nextElement();
			if(path.equals(name))
				return sres;
		}
		return null;
	}
	public Enumeration<Patient> getPatients(){
		return patients.elements();
	}
	public String getFileForPatient(Patient p) {
		if(!files.contains(p))
			return null;
		return files.get(p);
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
		GridPane scont=new GridPane();
		moto_miru_suru(ku);
		kensaku_chihou_suru(scont);
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
					Button kawaru=new Button("edit");
					rinku.setStyle("-fx-padding: 16;");
					kawaru.setStyle("-fx-padding: 16;");
				    kawaru.setOnAction(e-> {
								DisplayPatientInformation info=new DisplayPatientInformation(editable,hito,files.get(hito));
								info.showWindow();
							});
				    rinku.setOnAction(e->{
				    	//知らない
				    });
					dcont.add(label(hito.getDOB()), 1, row);
				    dcont.add(label(hito.getPharmacy()), 2, row);
				    dcont.add(label(parsePhone(hito.getPhoneNumber())), 3, row);
					dcont.add(rinku, 0, row);
					dcont.add(kawaru, 4, row);
					
					
					row++;
			}
		}
		scont.add(dcont, 1, 1);
		ku.add(scont, 1, 0);
		
	}
	private GridPane subete=new GridPane();
	private TextField kensakuchihou=null;
	private void moto_miru_suru(GridPane k_chi) {
		GridPane hidari_gawa=new GridPane();
		Button msg=new Button("Dashboard");
		msg.setOnAction(e->{
			//not implemented
		});
		hidari_gawa.add(msg, 0, 0);
		Button btn=new Button("Messages");
		btn.setOnAction(e->{
			//not implemented
		});
		
		hidari_gawa.add(btn, 0, 1);
		btn=new Button("Help");
		btn.setOnAction(e->{
			//not implemented
		});
		hidari_gawa.add(btn, 0, 2);
		btn=new Button("Logout");
		btn.setOnAction(e->{
			if(session!=null) {
				session.close();
			}
			CurrentUser.setUsername(null);
		});
		hidari_gawa.add(btn, 0, 3);
		hidari_gawa.setStyle("-fx-padding: 16;-fx-vgap: 16;");
		k_chi.add(hidari_gawa, 0, 0);
	}
	private void kensaku_chihou_suru(GridPane ku){
		GridPane chihou=new GridPane();
		kensakuchihou=new TextField("");
		chihou.add(kensakuchihou, 0, 0);
		Button suru=new Button("search");
		suru.setOnAction(e-> {
				kensaku=kensakuchihou.getText();
				atarashi_risuto(subete);
			});
		
		
		chihou.add(suru, 1, 0);
		ku.add(chihou, 1, 0);
	}
	//clear structures
	public void clearPatients() {
		patients.clear();
		files.clear();
	}
	//write all the patients to the file system
	public void writeStructures() {
		Enumeration<String> dekey=files.elements();
		Enumeration<Patient> pe=files.keys();
		while(pe.hasMoreElements()) {
			Patient pt=pe.nextElement();
			String path=dekey.nextElement();
			writePatient(path,pt);
		}
	}
	public void statDirectory() {
		 // Directory containing patient files
	    String directoryPath = "/home/ivan/school/CSE360/project/";
	    
	    // Get all files in the directory
	    File directory = new File(directoryPath);
	    File[] files = directory.listFiles();
	    
	    // If the directory is not found or empty, return
	    if (files == null || files.length == 0) {
	        System.out.println("No patient files found in the directory.");
	        return;
	    }
	    
	    // Iterate through each file in the directory and add it as a patient file
	    for (File file : files) {
	        if (file.isFile()) {
	            addPatientFile(file.getAbsolutePath());
	        }
	    }

	}
	public void start() {
		statDirectory();//read upon launching the UI
	    // Display the patient information
	    subete.setGridLinesVisible(true);
	    atarashi_risuto(subete);
	  
	    ScrollPane scroll = new ScrollPane(subete);
	    Alert alert = new Alert(AlertType.NONE);
	    alert.setTitle("Staff Portal");
	    alert.getButtonTypes().add(ButtonType.CLOSE);
	    alert.getDialogPane().setContent(scroll);
	    alert.setResizable(true);
	    session=alert;
	    //override enter key
	    alert.getDialogPane().addEventHandler(KeyEvent.ANY, e->{
	    	if(e.getCode()==KeyCode.ENTER) {
	    		e.consume();
	    		if(kensakuchihou==null)
	    			return;
	    		if(!kensakuchihou.getText().isBlank()) {
	    			kensaku=kensakuchihou.getText();
					atarashi_risuto(subete);
	    		}
	    		
	    	}
	    });
	    alert.show();
	}
	//IO METHODS
	//for reading account files
    //when initilizing buffered reader you must first mark spot zero
    //buff.mark(0);, not working
    //you could try without that but might not work
    public static String getFieldFromFile(File f,String field) throws IOException{
    	BufferedReader buff=new BufferedReader(new FileReader(f));//fopen("file","r");
    	String line;
    	while((line=buff.readLine())!=null) {
    		if(line.startsWith(field+":")) {
    			String retval=line.substring(line.indexOf(":")+1);
    			while(retval.startsWith(" "))
    				retval=retval.substring(1);
    			buff.close();
    			return retval;
    		}
    	}
    	buff.close();
    	return null;
    }
    //Patient(String username, String password, String firstname, String lastname, String email, String phonenumber, String insuranceProvider, int insuranceID, String dob, String address)
	public static Patient readPatient(String file) {
		try {
		
		
		File buff=new File(file);
		Patient p=new Patient(getFieldFromFile(buff, "Username"),getFieldFromFile(buff, "Password"),getFieldFromFile(buff, "First Name"),
				getFieldFromFile(buff, "Last Name"),getFieldFromFile(buff, "Email"),Long.parseLong(getFieldFromFile(buff, "Phone Number"))
				,getFieldFromFile(buff, "Insurance Provider"),Integer.parseInt(getFieldFromFile(buff, "Insurance ID")),
				getFieldFromFile(buff, "Date of Birth"),getFieldFromFile(buff, "Address"), getFieldFromFile(buff,"Pharmacy"));

		
		return p;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void writePatient(String file,Patient p) {
		try {
			FileWriter fw=new FileWriter(file);
			fw.write(p.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
