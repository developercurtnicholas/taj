import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class View extends JFrame implements ActionListener,ListSelectionListener {
	
	private Controller controller;
	
	//Text Fields
	private JTextField username; //username field
	private JPasswordField password;// password field
	
	
	private ArrayList<User> users; 
	
	//Register
	private JTextField vehicle;
	private JTextField zone_code;
	private JTextField zone_name;
	
	private JTextField usernameReg;
	private JTextField firstname;
	private JTextField middlename;
	private JTextField lastname;
	private JTextField currentAddress;
	private JTextField trn;
	private JTextField day;
	private JTextField month;
	private JTextField year;
	private JPasswordField regPass;
	private ArrayList<JTextField> regComponents;
	
	
	private Dimension textFieldDimens = new Dimension(293,30);
	
	//Buttons
	private JButton loginButton; 
	private JButton submitregister;
	private JButton backToLogin;
	private JButton backToMain;
	private JButton logOut;
	
	private JButton upgradeToPrivate;
	private JButton upgrade;
	private JButton createProvisional;
	private JButton deleteLicense;
	
	private Dimension buttonDimens = new Dimension(350,30);
	
	//List
	JList records = new JList();
	
	
	//Panels
	private JPanel loginPanel;
	private JPanel unameLabelAndField;
	private JPanel passLabelAndField;
	private JPanel registerPanel;
	private JPanel userDetails;
	
	//Colors
	private Color blue = new Color(25,181,254);
	private Color green = new Color(46,204,113);
	private Color red = new Color(239,72,54);
	
	//Window dimensions
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	
	
	private User lastSelected;
	
	//Constructor where the view is created
	public View(Controller controller){
		
		this.controller = controller;
		
		//Set the window props
		this.setWindowProperties();
		this.loadLoginView();
		
		
		//Initialize the initial components
	}
	
	//Loads the login screen on start up
	public void loadLoginView(){
		
		loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel,BoxLayout.Y_AXIS));
		
		username = new JTextField();
		username.setMaximumSize(this.textFieldDimens);
		
		password = new JPasswordField();
		password.setMaximumSize(this.textFieldDimens);
		

		
		loginButton = new JButton("Login");
		loginButton.setAlignmentX(CENTER_ALIGNMENT);
		loginButton.setMaximumSize(this.buttonDimens);
		loginButton.setBackground(green);
		loginButton.addActionListener(this);
	
		
		
		unameLabelAndField = new JPanel();
		unameLabelAndField.setLayout(new BoxLayout(unameLabelAndField,BoxLayout.X_AXIS));
		
		unameLabelAndField.add(new JLabel("Username:"));
		unameLabelAndField.add(username);
		
		passLabelAndField = new JPanel();
		passLabelAndField.setLayout(new BoxLayout(passLabelAndField,BoxLayout.X_AXIS));
		
		passLabelAndField.add(new JLabel("Password:"));
		passLabelAndField.add(password);
		
		loginPanel.add(unameLabelAndField);
		loginPanel.add(Box.createRigidArea(new Dimension(0,10)));
		loginPanel.add(passLabelAndField);
		loginPanel.add(Box.createRigidArea(new Dimension(0,10)));
		loginPanel.add(loginButton);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(0,150)));
		this.add(loginPanel);
		this.setVisible(true);
	}
	
	public void upgradeSuccess(){
		JOptionPane.showMessageDialog(getContentPane(), "Upgrade was successful");
	}
	
	public void upgradeFailed(){
		JOptionPane.showMessageDialog(getContentPane(), "Upgrade Failed");
	}
	
	public void loadRegisterView(){
		
		//Panel for registration components
		registerPanel = new JPanel();
		registerPanel.setLayout(new BoxLayout(registerPanel,BoxLayout.Y_AXIS));
		
		//textfields

		firstname = new JTextField();
		firstname.setToolTipText("Enter first name");
		middlename = new JTextField();
		middlename.setToolTipText("Enter middle name");
		lastname = new JTextField();
		lastname.setToolTipText("Enter last name");
		currentAddress = new JTextField(); 
		currentAddress.setToolTipText("Enter your address");
		
		Dimension d = new Dimension(293/3,30);
		
		day = new JTextField();
		day.setMaximumSize(d);
		month = new JTextField();
		month.setMaximumSize(d);
		year = new JTextField();
		year.setMaximumSize(d);
		
		trn = new JTextField();
		trn.setToolTipText("Enter Trn number");
		
		//submit button
		submitregister = new JButton("Submit");
		submitregister.setBackground(green);
		submitregister.setMaximumSize(buttonDimens);
		submitregister.setAlignmentX(CENTER_ALIGNMENT);
		submitregister.addActionListener(this);
		
		//Back Button 
		backToLogin = new JButton("Back");
		backToLogin.setBackground(blue);
		backToLogin.setMaximumSize(buttonDimens);
		backToLogin.setAlignmentX(CENTER_ALIGNMENT);
		backToLogin.addActionListener(this);
		
		JLabel title = new JLabel("Register For Provisional License");
		title.setAlignmentX(CENTER_ALIGNMENT);
		
		//DOB
		JPanel dob = new JPanel();
		dob.setLayout(new BoxLayout(dob,BoxLayout.X_AXIS));
		
		dob.add(day);
		dob.add(month);
		dob.add(year);
		
		
		//Adding all textfields to the panel
		registerPanel.add(title);
		
		registerPanel.add(Box.createRigidArea(new Dimension(0,10)));
		registerPanel.add(new JLabel("First name"));
		registerPanel.add(firstname);
		
		
		registerPanel.add(Box.createRigidArea(new Dimension(0,10)));
		registerPanel.add(new JLabel("Middle name"));
		registerPanel.add(middlename);
		
		registerPanel.add(Box.createRigidArea(new Dimension(0,10)));
		registerPanel.add(new JLabel("Last name"));
		registerPanel.add(lastname);
		
		registerPanel.add(Box.createRigidArea(new Dimension(0,10)));
		registerPanel.add(new JLabel("Date of Birth dd/mm/yyyy"));
		registerPanel.add(dob);
		
		registerPanel.add(Box.createRigidArea(new Dimension(0,10)));
		registerPanel.add(new JLabel("Current Address"));
		registerPanel.add(currentAddress);
		
		registerPanel.add(Box.createRigidArea(new Dimension(0,10)));
		registerPanel.add(new JLabel("Trn number 9 digits"));
		registerPanel.add(trn);
		
		registerPanel.add(Box.createRigidArea(new Dimension(0,10)));
		registerPanel.add(submitregister);
		registerPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		registerPanel.add(backToLogin);
		
		//Adding all textfields to a list
		regComponents = new ArrayList<JTextField>();
		regComponents.add(firstname);
		regComponents.add(middlename);
		regComponents.add(lastname);
		regComponents.add(currentAddress);
		regComponents.add(trn);
		
		
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		//Set the size of all the textfields in the list
		this.setTextFieldSize(regComponents);
		
		//Add the panel to the window
		this.add(registerPanel);
		this.revalidate();
		this.repaint();
		this.setVisible(true);
		
	}
	
	public void removeComponent(){
		this.getContentPane().removeAll();
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
	}
	
	//Set the window dimensions etc...
	private void setWindowProperties(){
		
		this.setSize(new Dimension(WIDTH,HEIGHT));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
	}
	
	
	private boolean checkEmpty(ArrayList<JTextField> comps){
		
		for(JTextField c : comps){
			if(!match(c.getText(),"[A-Za-z0-9\\s]{1,}")){
				System.out.println(c.getText());
				return true; 
			}
		}
		return false;
	}
	
	private void setTextFieldSize(ArrayList<JTextField> comps){
		for(JTextField c : comps){
			c.setMaximumSize(this.textFieldDimens);
		}
	}
	
	private boolean clientSideValidateProv(){
		
		//Check if text fields are empty
		if(checkEmpty(regComponents)){
			JOptionPane.showMessageDialog(getContentPane(), "No Field Can be empty");
			return false;
		}
		//Validate trn
		if(!match(trn.getText(),"[0-9]{9}")){
			JOptionPane.showMessageDialog(getContentPane(), "Trn should be nine digits");
			return false;
		}
		//Validate DOB
		if(!match(day.getText(),"(([0-2][1-9]{1})|(3[0-1]{1}))")){
			JOptionPane.showMessageDialog(getContentPane(), "Day should be in the range "
					+ "01-31");
			return false;
		}
		if(!match(month.getText(),"(0[1-9]{1})|(1[1-2]{1})")){
			JOptionPane.showMessageDialog(getContentPane(), "Month should be in the range"
					+ " 01 - 12");
			return false;
		}
		
		//Exception handling for if the year is blank 
		int y;
		try{
			y = Integer.parseInt(year.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(getContentPane(), "That is not a valid year");
			return false;
		}
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int age = currentYear - y;
		if(y < 1910){
			JOptionPane.showMessageDialog(getContentPane(),"You can't be that old");
			return false;
		}else if(age < 17){
			JOptionPane.showMessageDialog(getContentPane(), "Yo need to be at least 17");
			return false;
		}	
		return true;
	}
	
	public static boolean match(String entry, String regex){
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(entry);
		while(m.find()){
			if(m.group().equals(entry)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	public void regFail(){
		JOptionPane.showMessageDialog(getContentPane(), "Registration failed, try again");
	}
	
	public void regSuccess(){
		JOptionPane.showMessageDialog(getContentPane(), "User Registered");
	}
	
	public void loadUsers(ArrayList<User> users){
		this.users = new ArrayList<User>();
		this.users = users;
	}
	
	private ArrayList<String> buildList(ArrayList<User> user){
		
		ArrayList<String> result = new ArrayList<String>();
		
		if(user == null){
			return result;
		}
		for(User u : user){
			String s = u.firstname + " "+u.lastname;
			result.add(s);
		}
		
		return result;
	}
	
	private void upgradeScreen(User u){
		
		this.removeComponent();
		
		JPanel upgradePanel = new JPanel();
		upgradePanel.setLayout(new BoxLayout(upgradePanel,BoxLayout.Y_AXIS));
		
		this.vehicle = new JTextField();
		this.vehicle.setMaximumSize(textFieldDimens);
		
		this.zone_name = new JTextField();
		this.zone_name.setMaximumSize(textFieldDimens);
		
		this.zone_code = new JTextField();
		this.zone_code.setMaximumSize(textFieldDimens);
		
		
		upgrade = new JButton("Upgrade");
		upgrade.setBackground(green);
		upgrade.setMaximumSize(buttonDimens);
		upgrade.setAlignmentX(CENTER_ALIGNMENT);
		upgrade.addActionListener(this);
		
		backToMain = new JButton("Back");
		backToMain.addActionListener(this);
		backToMain.setMaximumSize(buttonDimens);
		backToMain.setBackground(red);
		backToMain.setAlignmentX(CENTER_ALIGNMENT);
		
		upgradePanel.add(new JLabel("Vehicle"));
		upgradePanel.add(vehicle);
		
		upgradePanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		upgradePanel.add(new JLabel("Zone Name"));
		upgradePanel.add(zone_name);
		
		upgradePanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		upgradePanel.add(new JLabel("Zone Code"));
		upgradePanel.add(zone_code);
		
		upgradePanel.add(Box.createRigidArea(new Dimension(0,10)));
		upgradePanel.add(upgrade);
		upgradePanel.add(backToMain);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		this.add(upgradePanel);

	}
	//load the profile to manage the license
	public void loadUserProfile(){
		
		controller.getAllRecords();		
		ArrayList<String> userList = this.buildList(this.users);
		String[] listData = new String[userList.size()];
		userList.toArray(listData);
		
		this.removeComponent();
		
		JPanel layout = new JPanel();
		layout.setLayout(new BoxLayout(layout,BoxLayout.Y_AXIS));
		
		userDetails = new JPanel();
		userDetails.setLayout(new BoxLayout(userDetails,BoxLayout.Y_AXIS));

		
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options,BoxLayout.Y_AXIS));
		
		this.createProvisional = new JButton("Register Provisional");
		this.createProvisional.setMaximumSize(buttonDimens);
		this.createProvisional.setBackground(green);
		this.createProvisional.addActionListener(this);
		
		this.upgradeToPrivate = new JButton("Upgrade Selected To Private");
		this.upgradeToPrivate.setMaximumSize(buttonDimens);
		this.upgradeToPrivate.setBackground(blue);
		this.upgradeToPrivate.addActionListener(this);
		
		this.deleteLicense = new JButton("Delete Select Record");
		this.deleteLicense.setMaximumSize(buttonDimens);
		this.deleteLicense.setBackground(red);
		this.deleteLicense.addActionListener(this);
		
		this.logOut = new JButton("Logout");
		this.logOut.setMaximumSize(buttonDimens);
		this.logOut.addActionListener(this);
		
		//records list
		this.records = new JList();
		this.records.setMaximumSize(new Dimension(340,500));
		this.records.setListData(listData);
		this.records.addListSelectionListener(this);
		
		options.add(this.createProvisional);
		options.add(Box.createRigidArea(new Dimension(0,10)));
		options.add(this.upgradeToPrivate);
		options.add(Box.createRigidArea(new Dimension(0,10)));
		options.add(this.deleteLicense);
		options.add(Box.createRigidArea(new Dimension(0,10)));
		options.add(this.logOut);
		
		
		layout.add(userDetails);
		layout.add(Box.createRigidArea(new Dimension(0,30)));
		layout.add(options);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
		this.add(layout);
		this.add(this.records);
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}
	public void recordDeleted(){
		JOptionPane.showMessageDialog(getContentPane(), "Record deleted");
		this.records = new JList();
		this.loadUserProfile();
	}
	public void deleteFail(){
		JOptionPane.showMessageDialog(getContentPane(), "Well that didn't work, try again");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		

		//Delete a record
		if(e.getSource() == this.deleteLicense){
			
			if(this.users == null ){
				return;
			}
			if(this.users.size() < 1){
				return;
			}
			
			int res = JOptionPane.showConfirmDialog(getContentPane(), "Are you sure "
					+ "you want to do"
					+ " that?");
			
			if(res == 0){
				controller.deleteRecord(this.lastSelected.trn);
			}
		}
		
		//Upgrade to provisional
		if(e.getSource() == this.upgrade){
			
			
			if(!match(this.zone_code.getText(),"[A-Za-z0-9]{1,5}")){
				JOptionPane.showMessageDialog(getContentPane(),"Invalide zone code");
				return;
			}
			
			if(!match(this.zone_name.getText(),"[A-Za-z]{1,}")){
				JOptionPane.showMessageDialog(getContentPane(), "Invalid zone name");
				return;
			}
			
			if(!match(this.vehicle.getText(),"[A-Za-z0-9\\s]{1,}")){
				JOptionPane.showMessageDialog(getContentPane(), "Enter a vehicle moron");
				return;
			}
			
			this.lastSelected.vehicle = this.vehicle.getText();
			this.lastSelected.zone_name = this.zone_name.getText();
			this.lastSelected.zone_code = this.zone_code.getText();
			
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
			int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			
			this.lastSelected.expiry_years = currentDay+"/"+currentMonth+"/"+
			(currentYear+5);
			
			controller.upgradeToPrivate(this.lastSelected);
		}
		
		//Go back to the main 
		if(e.getSource() == this.backToMain){
			this.removeComponent();
			this.loadUserProfile();
		}
		
		//Upgrade to a private
		if(e.getSource() == this.upgradeToPrivate){
			try{
				this.upgradeScreen(this.users.get(this.records.getSelectedIndex()));
			}catch(Exception ez){
				JOptionPane.showMessageDialog(getContentPane(), "Please select a user"
						+ " on the left");
			}
			
		}
		
		//Log in
		if(e.getSource() == loginButton){
			if(username.getText().equals("admin") && password.getText().equals("admin")){
				this.loadUserProfile();
			}
			else{
				JOptionPane.showMessageDialog(getContentPane(), "Login Failed");
			}
		}
		
		//Log out
		if(e.getSource() == this.logOut){
			this.removeComponent();
			this.loadLoginView();
		}
		
		//Create a new provisional license
		if(e.getSource() == this.createProvisional){
			this.removeComponent();
			this.loadRegisterView();
		}
		
		//Go back to main
		if(e.getSource() == backToLogin){
			this.removeComponent();
			this.loadUserProfile();
		}
		
		//Register provisional
		if(e.getSource() == submitregister){
			
			if(clientSideValidateProv()){
				System.out.println("Got here");
				String dob = day.getText()+"/"+month.getText()+"/"+year.getText();
				
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
				int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
				
				User u = new User(
						firstname.getText(),
						middlename.getText(),
						lastname.getText(),
						trn.getText(),
						dob, 
						currentAddress.getText(),
						"Provisional License",
						currentDay+"/"+currentMonth+"/"+(currentYear+1)
						);
				
				Request r = new Request("PROVISIONAL");
				r.user = u;
				controller.registerProvisional(r);
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		
		int index = this.records.getSelectedIndex();
		this.userDetails.removeAll();
		this.userDetails.revalidate();
		this.userDetails.repaint();
		User x = this.users.get(index);
		this.lastSelected = x;
		this.userDetails.add(new JLabel("User: "+x.firstname + " " +x.lastname));
		this.userDetails.add(new JLabel("License: "+x.license));
		this.userDetails.add(new JLabel("Expires: "+x.expiry_years));
		this.userDetails.add(new JLabel("trn: "+x.trn));
		this.userDetails.add(new JLabel("Vehicle: "+x.vehicle));
		this.userDetails.add(new JLabel("Zone Name: "+x.zone_name));
		this.userDetails.add(new JLabel("Zone Code: "+x.zone_code));
		
		
	}
}
