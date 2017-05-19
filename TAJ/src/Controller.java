import java.util.ArrayList;

public class Controller {
	
	private Model model;
	private View view;
	
	//Where the program starts
	public static void main(String[] args){
		
		Controller controller = new Controller();
		
		controller.begin();
	}
	
	public boolean registerProvisional(Request r){
		System.out.println("Got here too");
		this.model.registerProvisional(r);
		return false;
	}
	
	public void registrationSuccess(){
		this.view.regSuccess();
	}
	
	public void deleted(){
		this.view.recordDeleted();
	}
	public void notDeleted(){
		this.view.deleteFail();
	}
	
	public void deleteRecord(String trn){
		this.model.deleteRecord(trn);
	}
	
	public void loadUsers(ArrayList<User> users){
		this.view.loadUsers(users);
	}
	public void getAllRecords(){
		this.model.getAllRecords();
	}
	
	public void upgradeSuccess(){
		this.view.upgradeSuccess();
	}
	
	public void upgradeFailed(){
		
		this.view.upgradeFailed();
	}
	
	public void upgradeToPrivate(User u){
		this.model.upgradeToPrivate(u);
	}
	
	public void registrationFailed(){
		this.view.regFail();
	}
	
	//Set up the view the model etc...
	private void begin(){
		model = new Model(this);
		loadView();
	}
	
	private void loadView(){
		view = new View(this);
	}
}
