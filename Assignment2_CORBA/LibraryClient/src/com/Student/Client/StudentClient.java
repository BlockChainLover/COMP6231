package com.Student.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Scanner;






import org.omg.CORBA.ORB;

import ServerImplementaiton.ServerIMplHelper;
import ServerImplementaiton.ServerIMplOperations;

public class StudentClient extends Thread{
	
	Scanner keyboard = new Scanner(System.in);
	
	String firstname;
	String lastname;
	String email;
	String phoneNumber;
	
	//username that is using this Student Library
	String username;
	String Password;
	String EducationalInstitute;
	
	//the associated server on which the username should be connected to
	ServerIMplOperations associatedServer;
	
	//data that is going to log on the server
	
	private Writer Logger = null;
	
	/**
	 * 
	 * StudentClient constructor which sets up the associated server
	 * @param instname
	 * @param iorFile
	 * @throws IOException
	 */
	public StudentClient(String instname,String username, String iorFile)throws IOException{
		this.username = username;
		this.EducationalInstitute = instname;
		try{
			Logger = new BufferedWriter(
					 new OutputStreamWriter(
					 new FileOutputStream("../LibraryServer/src/Logging/"+EducationalInstitute+".txt")));
			}catch(FileNotFoundException err){
				System.out.println(err);
			}
	}
	public StudentClient()  {
		try{
		Logger = new BufferedWriter(
				 new OutputStreamWriter(
				 new FileOutputStream("../LibraryServer/src/Logging/"+EducationalInstitute+".txt")));
		}catch(FileNotFoundException err){
			System.out.println(err);
		}
	}
	/*
	 * 
	 * 
	 * Connect to CORBA Server related to particular institute
	 * 
	 * */
	public String connect(String instname,String username, String iorFile){
		try{
			this.username = username;
			this.EducationalInstitute = instname;
			/*
			 * random array string for the init
			 * 
			 * */
			String args[] = null;
			ORB orb = ORB.init(args,null);
			//System.out.println(iorFile);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(iorFile));
			String ior = br.readLine();
			
			
			org.omg.CORBA.Object object = orb.string_to_object(ior);
			associatedServer = ServerIMplHelper.narrow(object);
			
			//set up logging information for particular student
			
			br.close();
		}catch(Exception ex){
			System.out.println(ex.toString() + ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}
		
		return "Succesfully connected";
	}
	
	
	
	
	
	/*
	 *  @Create Account Function that is being called from studentMenu
	 * 
	 
	 * 
	 * */
	public void createAccount() throws IOException {
		
		System.out.print("\n Please Enter the First name of the Student : = ");
		firstname = keyboard.next();
		System.out.print("\nPlease Enter the last name of the student : = ");
		lastname = keyboard.next();
		System.out.print("\nEnter the email address of the system : = ");
		email = keyboard.next();
		boolean value = false;
		/*
		 * Validating the Phone Number
		*/
			do{ 
				System.out.print("\nEnter the phone number of the student: = ");
				phoneNumber = keyboard.next();
				value = true;
				if(phoneNumber.length() != 10 ){
					value = false;
				}else{
					System.out.print("\nWrong Cell phone numeber");
				}
			}while(value == true);
			
			do{
			System.out.print("\nEnter the username of the student : = ");
			 username = keyboard.next();
			value = true;
			if(username.length() > 6 && username.length()  < 15 ){
					value = false;
				}else{
					System.out.print("\n!!! Username Should be Between 6 TO 15 Characters Long !!! ");
				}
			}while(value == true);
			/*
			 * Validating the password
			*/
			do{
		System.out.print("\nEnter the password of the student : = ");
		Password = keyboard.next();
		if(Password.length() > 6){
			value = false;
			}else{
				System.out.println("!!! Password Should be Between 6 Characters Long !!! ");
			}
		}while(value == true);
		/*
		 * Validating the University Name
		*/
		do{
				System.out.print("\nEnter the Educational Institute of the student (\"McGill\",\"Concordia\",\"Vanier\") = ");
				EducationalInstitute = keyboard.next();
				value = true;
					if(EducationalInstitute.equalsIgnoreCase("Concordia") || EducationalInstitute.equalsIgnoreCase("McGill") || EducationalInstitute.equalsIgnoreCase("Vanier") ){
						value = false;
					}else{
						System.out.println("Please Enter these Correct University");
					}
			}while(value == true);
		
		createAcc(username, Password, firstname, lastname, phoneNumber, email, EducationalInstitute);
		
	}
	
	public void createAcc(String username, String Password, String firstname, String lastname, String phoneNumber, String email, String EducationalInstitute) throws IOException{
		String inst = EducationalInstitute;
		String response = null;
		
		if(inst.equalsIgnoreCase("Concordia")){
			response = connect("Concordia", username, "../LibraryServer/src/Concordia.txt");
		}else if(inst.equalsIgnoreCase("McGill")){
			response = connect("McGill", username, "../LibraryServer/src/McGill.txt");
		}else if(inst.equalsIgnoreCase("Vanier")){
			response = connect("Vanier", username, "../LibraryServer/src/Vanier.txt");
		}
		
		//System.out.println(response);
		String result = associatedServer.createAccount(username, Password, firstname, lastname, phoneNumber, email, EducationalInstitute);
		System.out.println(result+ " responeded at " +(new Date().toString()+ "\n\n"));
		Logger.write(result+ " responeded at " +(new Date().toString()+ "\n\n"));
	}

	public void reserveBook() throws IOException {
		System.out.print("\n Please Enter the username of the Student : = ");
		String username = keyboard.next();
		System.out.print("\n Please Enter the password of the student : = ");
		String password= keyboard.next();
		System.out.print("\nPlease Enter the book name : = ");
		String bookname = keyboard.next();
		System.out.print("\n Please Enter the Authorname of the Book : = ");
		String Authorname = keyboard.next();
		System.out.print("\n Please Enter the Institute name of the Book : = ");
		String inst = keyboard.next();
		reserve(username, password, bookname, Authorname,inst);
		
	}
	
	public void reserve(String username,String password,String bookname,String Authorname,String inst) throws IOException{
		String response = null;
		if(inst.equalsIgnoreCase("Concordia")){
			response = connect("Concordia", username, "../LibraryServer/src/Concordia.txt");
		}else if(inst.equalsIgnoreCase("McGill")){
		response = connect("McGill", username, "../LibraryServer/src/McGill.txt");
		}else if(inst.equalsIgnoreCase("Vanier")){
			response = connect("Vanier", username, "../LibraryServer/src/Vanier.txt");
		}
		
	//	System.out.println(response);
		String result = associatedServer.reserveBook(username, password, bookname, Authorname);
		System.out.println("\n-------------------------------------------------------\n");
		System.out.println(result+ " responeded at " +(new Date().toString()+ "\n"));
		System.out.println("\n-------------------------------------------------------\n");
		
		
		Logger.write(result+ " responeded at " +(new Date().toString()+ "\n\n"));
			
	}
	
	public void reserveInterLibrary() throws IOException {
		System.out.print("\n Please Enter the username of the Student : = ");
		String username = keyboard.next();
		System.out.print("\n Please Enter the password of the student : = ");
		String password= keyboard.next();
		System.out.print("\nPlease Enter the book name : = ");
		String bookname = keyboard.next();
		System.out.print("\n Please Enter the Authorname of the Book : = ");
		String Authorname = keyboard.next();
		System.out.print("\n Please Enter the Institute name of the Book : = ");
		String inst = keyboard.next();
			
			reserveInter(username, password, bookname, Authorname,inst);
			
		}
	
	public void reserveInter(String username,String password,String bookname,String Authorname, String inst) throws IOException{
		String response = null;
		if(inst.equalsIgnoreCase("Concordia")){
			response = connect("Concordia", username, "../LibraryServer/src/Concordia.txt");
		}else if(inst.equalsIgnoreCase("McGill")){
			response = connect("McGill", username, "../LibraryServer/src/McGill.txt");
		}else if(inst.equalsIgnoreCase("Vanier")){
			response = connect("Vanier", username, "../LibraryServer/src/Vanier.txt");
		}
		
		//System.out.println(response);
		String result = associatedServer.reserveInterLibrary(username, password, bookname, Authorname);
		System.out.println("\n-------------------------------------------------------\n");
		System.out.println(result+ " responeded at " +(new Date().toString()+ "\n"));
		System.out.println("\n-------------------------------------------------------\n");
		
		
		Logger.write(result+ " responeded at " +(new Date().toString()+ "\n\n"));
			
	}
		
		
	

	public void exit() throws IOException {
		String server[] = new String[]{"concordia","mcgill","vanier"};
		for(String i : server){
			String response = null;
			response = connect(i, username, "../LibraryServer/src/"+i+".txt");
			System.out.println(response);
			String result = associatedServer.exit();
			System.out.println("\n-------------------------------------------------------\n");
			System.out.println(result+ " responeded at " +(new Date().toString()+ "\n"));
			System.out.println("\n-------------------------------------------------------\n");
			
			
			Logger.write(result+ " responeded at " +(new Date().toString()+ "\n\n"));
			
		}
		keyboard.close();
	}

	
}
