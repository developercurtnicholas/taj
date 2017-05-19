import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

//The model connects to the server
public class Model {
	
	private Socket s;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Controller controller;
	public Model(Controller c){
		
		this.controller = c;
		//If we have established a connection to the server, set up the streams
		if(this.establishConnection()){
			this.setUpStreams();
		}
	}
	
	//Establish a connection to the server
	private boolean establishConnection(){
		try{
			s = new Socket("localhost",100);
			if(s.isConnected()){
				System.out.println("We have connected to the server");
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	public void deleteRecord(String trn){
		
		try {
			Request r = new Request("DELETE");
			r.trn = trn;
			out.writeObject(r);
			boolean result =(Boolean)in.readObject();
			if(result){
				controller.deleted();
			}else{
				controller.notDeleted();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void registerProvisional(Request r){
		
		try {
			out.writeObject(r);
			boolean result =(Boolean)in.readObject();
			if(result){
				controller.registrationSuccess();
			}else{
				controller.registrationFailed();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void upgradeToPrivate(User u){
		
		try{
			Request r = new Request("UPGRADE");
			r.user = u;
			out.writeObject(r);
			boolean result = (boolean) in.readObject();
			if(result){
				controller.upgradeSuccess();
			}else{
				controller.upgradeFailed();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void getAllRecords(){
		try{
			Request r = new Request("GET ALL");
			out.writeObject(r);
			ArrayList<User> users = (ArrayList<User>)in.readObject();
			if(users != null){
				if(users.size() > 0){
					controller.loadUsers(users);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	private void setUpStreams(){
		try{
			out = new ObjectOutputStream(s.getOutputStream());
			in  = new ObjectInputStream(s.getInputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
