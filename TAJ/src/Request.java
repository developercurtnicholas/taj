import java.io.Serializable;

public class Request implements Serializable {
	
	String request;
	String trn;
	User user;
	
	Request(String request){
		this.request = request;
	}
}
