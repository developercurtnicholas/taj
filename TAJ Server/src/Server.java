import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;


public class Server {
	
	private ServerSocket ss;
	private Socket s;

	public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/taj";
	public static final String DB_USER_NAME = "root";
	public static final String DB_PASSWORD = "";
	
	public static void main(String[] args){
		Server server = new Server();
	}
	
	//Constructor
	public Server(){
		this.listen();
	}
	
	//Listen for clients connecting
	private void listen(){
		try{
			//Server listening on port 100
			ss = new ServerSocket(100);
			s = new Socket();
			
			while(true){
				
				//Accept incoming connections
				s = ss.accept();
				if(s.isConnected()){
					new Handler(s);
				}

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	
	//Class to handle each client that is connected
	private class Handler extends Thread implements Runnable{
		
		private Socket s;
		private ObjectOutputStream out;
		private ObjectInputStream in;
		private java.sql.Connection con;
		
		public Handler(Socket s){
			this.s = s;
			this.connectToDB();
			setUpStreams();
			this.start();
		}
		
		//Set up the input and output streams for the handler
		private void setUpStreams(){
			try{
				this.in = new ObjectInputStream(s.getInputStream());
				this.out = new ObjectOutputStream(s.getOutputStream());
			}catch(Exception e){
				e.printStackTrace();
			} 
		}
		
		private void registerProvisional(User u){
			
			System.out.println("Server contacted");
			String sql = "INSERT INTO users(firstname,middlename,lastname,"
					+ "address,trn,license,expires"
					+ ") VALUES(?,?,?,?,?,?,?)";
			
			try{
				
				PreparedStatement stmt = (PreparedStatement)this.con.prepareStatement(sql);

				stmt.setString(1,u.firstname);
				stmt.setString(2,u.middlename);
				stmt.setString(3,u.lastname);
				stmt.setString(4,u.address);
				stmt.setString(5,u.trn);
				stmt.setString(6,u.license);
				stmt.setString(7,u.expiry_years);
				
				stmt.execute();
				out.writeObject(true);

				
			}catch(Exception e){
				e.printStackTrace();
				try {
					out.writeObject(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		//This is where we connect to the database
		private void connectToDB(){
			try {
				this.con = DriverManager.getConnection(CONNECTION_STRING,DB_USER_NAME
						,DB_PASSWORD);
						
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		private void getAll(){
			String sql = "SELECT * FROM users";
			
			try{
				
				PreparedStatement stmt = (PreparedStatement)this.con.prepareStatement(sql);
		
				ResultSet rs = stmt.executeQuery();
				final ArrayList<User> users = new ArrayList();
				while(rs.next()){
					
					//Return the details of the logged in user
					User x = new User();
					x.firstname = rs.getString("firstname");
					x.middlename = rs.getString("middlename");
					x.lastname = rs.getString("lastname");
					x.address = rs.getString("address");
					x.license = rs.getString("license");
					x.trn = rs.getString("trn");
					x.expiry_years = rs.getString("expires");
					x.vehicle = rs.getString("vehicle");
					x.zone_code = rs.getString("zone_code");
					x.zone_name = rs.getString("zone_name");
					
					users.add(x);
				}
				
				out.writeObject(users);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		private void upgrade(User u){
				String sql = "UPDATE users SET zone_code = ? , zone_name = ?"
						+ ", vehicle = ?, expires = ?, license = 'Private' WHERE trn = ?";
			
			try{
				
				PreparedStatement stmt = (PreparedStatement)this.con.prepareStatement(sql);
				stmt.setString(1, u.zone_code);
				stmt.setString(2, u.zone_name);
				stmt.setString(3, u.vehicle);
				stmt.setString(4, u.expiry_years);
				stmt.setString(5, u.trn);
				
				if(stmt.executeUpdate() > 0){
					out.writeObject(true);
				}else{
					out.writeObject(false);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		private void deleteUser(String trn){
			String sql = "DELETE FROM users WHERE trn = ?";
		
			try{
				
				PreparedStatement stmt = (PreparedStatement)this.con.prepareStatement(sql);
				stmt.setString(1,trn);
				if(stmt.executeUpdate() > 0){
					out.writeObject(true);
				}else{
					out.writeObject(false);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		@Override
		public void run(){
			
			while(true){
				try{
					Request r = (Request)in.readObject();
					switch(r.request){
					
					//Register the provisional user
						case "PROVISIONAL":{
							registerProvisional(r.user);
							break;
						}		
						case "GET ALL":{
							this.getAll();
							break;
						}
						
						case "UPGRADE":{
							this.upgrade(r.user);
							break;
						}
						
						case  "DELETE" :{
							this.deleteUser(r.trn);
							break;
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					break;
				}
			}
		}
	}
}
