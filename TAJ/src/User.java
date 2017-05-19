import java.io.Serializable;

public class User implements Serializable {
	
	String username;
	String firstname;
	String middlename;
	String lastname;
	String trn;
	String dob;
	String address;
	String license;
	String expiry_years;
	String request;
	String password;
	String zone_code;
	String zone_name;
	String vehicle;
	
	public User(String f, String m ,String l, String t, String d,String a,
			String li,String exp){
		
		this.firstname = f;
		this.middlename = m;
		this.lastname = l;
		this.trn = t;
		this.dob = d;
		this.address = a;
		this.license = li;
		this.expiry_years = exp;
	}
	
	public User(){
		
	}
	
	public void setRequest(String r){
		this.request = r;
	}
}
