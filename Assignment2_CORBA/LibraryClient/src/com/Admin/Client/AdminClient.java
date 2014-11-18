package com.Admin.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
//import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
import java.net.MalformedURLException;
//import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Scanner;

import org.omg.CORBA.ORB;

import ServerImplementaiton.ServerIMplHelper;
import ServerImplementaiton.ServerIMplOperations;


@SuppressWarnings("serial")
public class AdminClient implements Serializable  {
	
	String ReturnString = new String();
	
	private Writer Logger = null;
	String EducationalInstitute;
	String username;
	ServerIMplOperations associatedServer;
	public AdminClient()  {
		System.out.println("Welcome Library admin");
		System.out.println(" !! Please enter the following Information !!");	
		try{
		Logger = new BufferedWriter(
				 new OutputStreamWriter(
				 new FileOutputStream("../LibraryServer/src/Logging/admin/"+EducationalInstitute+".txt")));
		}catch(FileNotFoundException err){
			System.out.println(err);
		}
	}
		
	

	public void display(){
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		
		String user = " ";
		int nofDays;
		
		String username = "";
		boolean flag = true;
		do{
		
			do{
				System.out.print("\n Enter the Admin Username = ");
				username = keyboard.next();
				if(!username.equalsIgnoreCase("admin")){System.out.println("invalid username");}
			}while(!username.equalsIgnoreCase("admin"));
		
		String password;
			do{
				System.out.print("\n Enter the Admin Password = ");
				password = keyboard.next();
				if(!password.equalsIgnoreCase("admin")){System.out.println("invalid Password");}
			}while(!password.equalsIgnoreCase("admin"));
		
		boolean value = false;
		String EducationalInstitute;
			do{
				System.out.print("\nEnter the Educational Institute (\"McGill\",\"Concordia\",\"Vanier\") = ");
				EducationalInstitute = keyboard.next();
				value = true;
					if(EducationalInstitute.equalsIgnoreCase("Concordia") || EducationalInstitute.equalsIgnoreCase("McGill") || EducationalInstitute.equalsIgnoreCase("Vanier") ){
						value = false;
					}else{
						System.out.println("Please Enter these Correct Institute");
					}
			}while(value == true);
		
		String inst = EducationalInstitute;
		System.out.print("\n Enter the Number of Days = ");
		String NofD = keyboard.next();
		String response = null;
		
		try{
		if(inst.equalsIgnoreCase("Concordia")){
			response = connect("Concordia", username, "../LibraryServer/src/Concordia.txt");
		}else if(inst.equalsIgnoreCase("McGill")){
			response = connect("McGill", username, "../LibraryServer/src/McGill.txt");
		}else if(inst.equalsIgnoreCase("Vanier")){
			response = connect("Vanier", username, "../LibraryServer/src/Vanier.txt");
		}
		
		System.out.println(response);
		String result = associatedServer.getNonreturners(username, password, inst, NofD);
		System.out.println("\n-------------------------------------------------------\n");
		System.out.println(result+ " responeded at " +(new Date().toString()+ "\n"));
		System.out.println("\n-------------------------------------------------------\n");
		
		
		Logger.write(result+ " responeded at " +(new Date().toString()+ "\n\n"));
		
		
		
			if(inst.equalsIgnoreCase("Concordia")){
				response = connect("Concordia", username, "../LibraryServer/src/Concordia.txt");
			}else if(inst.equalsIgnoreCase("McGill")){
				response = connect("McGill", username, "../LibraryServer/src/McGill.txt");
			}else if(inst.equalsIgnoreCase("Vanier")){
				response = connect("Vanier", username, "../LibraryServer/src/Vanier.txt");
			}
		 Scanner in = new Scanner(System.in);
		 String x= "1";
		 do{
			 System.out.println("\nWould you like to test setDuration Function (Press 1 for confirmation) ");
			 x = in.next();
			 	if(x.equalsIgnoreCase("1")){
				     System.out.print("\nPlease enter the username : ");
					 username = in.next();
					 System.out.print("\nPlease enter the bookname : ");
					 String bookname = in.next();
					 System.out.print("\nPlease enter the duration : ");
					 String number= in.next();
					 String result2 = associatedServer.setDuration(username, bookname, number);;
					 System.out.println("\n-------------------------------------------------------\n");
						System.out.println(result2+ " responeded at " +(new Date().toString()+ "\n"));
						System.out.println("\n-------------------------------------------------------\n");
						Logger.write(result2+ " responeded at " +(new Date().toString()+ "\n\n"));
					
				 }else{
					 x = "0";
					 break;
				 }
		}while(x.equalsIgnoreCase("1"));	
		
		
		
		}catch(Exception err){
					err.printStackTrace();
			}
		
		}while(flag == true);
}

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
			System.out.println(iorFile);
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
	
//	public void bindServer(String username,int PORT ,String password,String inst, int NofD){
//		DatagramSocket Socket = null;
//		@SuppressWarnings("unused")
//		String servername = " ";
//		if(PORT == 2005){servername = "Concordia";}
//		else if(PORT == 2006){servername = "McGill";}
//		else if(PORT == 2007){servername = "Vanier";}
//		try{
//			//System.out.println(PORT+"\n");
//			Socket = new DatagramSocket();
//			String str = username+"."+PORT+"."+password+"."+inst+"."+NofD;
//			//System.out.println(str);
//			byte[] m = str.getBytes();
//			InetAddress aHost = InetAddress.getByName("localhost");
//			int serverPort = PORT;
//			DatagramPacket request = new DatagramPacket(m, str.length(),aHost,serverPort);
//			System.out.println("\nUDP call has been send to" + servername +  " server at PORT " + PORT + " by " + servername + "  " +username);
//			Socket.send(request);
//			byte[] buffer = new byte[1000];
//			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
//			Socket.receive(reply);
//			
//			byte[] dataCopy = new byte[reply.getLength()];
//			System.arraycopy(reply.getData(), reply.getOffset(),dataCopy, 0, reply.getLength());
//			String actualData = new String(dataCopy);
//			actualData = actualData.trim();
//			String datArry[] = actualData.split("\\/");
//			
//			for(int i =0 ;i<datArry.length;i++){
//			System.out.println(datArry[i]);
//			System.out.println("");
//			}
//			
//			//System.out.println("reply"+reply.getData());
//			
//	
//		}catch(SocketException e){System.out.println("Socekt "+e.getMessage());
//		}catch(Exception e){System.out.println(e.getMessage());
//		}finally {if(Socket != null){Socket.close();}}
//		
//	}
	

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
			AdminClient admin = new AdminClient();
			admin.display();
			
	}
	

}
