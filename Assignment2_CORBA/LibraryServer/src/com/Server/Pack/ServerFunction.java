package com.Server.Pack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import ServerImplementaiton.ServerIMplPOA;

/**
 * @author sdesk152
 *
 */
/**
 * @author sdesk152
 *
 */
@SuppressWarnings("serial")
public class ServerFunction extends ServerIMplPOA implements Serializable,
		Runnable {

	public HashMap<Character, HashMap<String, Student>> StudentAccount = new HashMap<Character, HashMap<String, Student>>();
	public HashMap<String, Book> BookDetails = new HashMap<String, Book>();
	public HashMap<String, BooKHistory> bookHistory = new HashMap<String, BooKHistory>();

	String servername;

	

	int portNumber;
	
	/*
	 * Constructor method called from the Student Menu class 
	 * Initializing servername and port number
	 * Apart from this it is also deserializing the hash maps 
	 * 
	 * */
	public ServerFunction(String string, int portNumber) {
		this.portNumber = portNumber;
		this.servername = string;		
		new Thread(this).start();
		//mappingDatabase();
		setup();
	}
	/**
	 * set up the database
	 * 
	 * */
	public void setup(){
	@SuppressWarnings({ "resource", "unused" })
	Scanner in = new Scanner(System.in);
	//System.out.println("Press y if you want to setup the database :");
	//String k = in.next().trim();
	String k="y";
	if(k.equalsIgnoreCase("y")){
			if(this.servername.equalsIgnoreCase("concordia")){
				BookSetupFunction("c", "kunwar", 10);
				BookSetupFunction("c++", "kunwar", 10);
				BookSetupFunction("java", "kunwar", 10);
				BookSetupFunction("hindi", "kunwar", 10);
				BookSetupFunction("punjabi", "kunwar", 10);
			}
			else if(this.servername.equalsIgnoreCase("mcgill")){
				BookSetupFunction("algorithm", "kunwar", 10);
				BookSetupFunction("c#", "kunwar", 10);
				BookSetupFunction("SPM", "kunwar", 10);
				BookSetupFunction("SCM", "kunwar", 10);
			}
			else if(this.servername.equalsIgnoreCase("vanier")){
				BookSetupFunction("Quality", "rattan", 10);
				BookSetupFunction("c", "rattan", 10);
				BookSetupFunction("c++", "rattan", 10);
				BookSetupFunction("SCM", "rattan", 10);
				BookSetupFunction("Quality", "rattan", 10);
				BookSetupFunction("maya", "rattan", 10);
				BookSetupFunction("distributed", "rattan", 10);
				BookSetupFunction("bones", "kathy", 1);
			}
		}
		return;
	}
	public void BookSetupFunction(String bname, String authorname,int numofcopies) {
		Book b1 = new Book();
		b1.BookName = bname;
		b1.AuhorName = authorname;
		b1.numberOfCopies = numofcopies;
		String bookString = bname + authorname;
		BookDetails.put(bookString, b1);
	}
	

	/*
	 * create Account Function synchronized while writing on to the student Hash
	 * Map
	 */
	@Override
	public String createAccount(String username, String password,String firstname, String lastname, 
			String phonenumber,String email, String educationalinstitute) {
		try {

			Student student = new Student();

			LoggerTask logtask = null;
			logtask = new LoggerTask();

			student.firstname = firstname;
			student.lastname = lastname;
			student.email = email;
			student.username = username;
			student.Password = password;
			student.phoneNumber = phonenumber;
			student.EducationalInstitute = educationalinstitute;

			String path = "../LibraryServer/src/com/Server/Pack/userCreated/"+ student.username + ".txt";

			char element = student.username.toLowerCase().charAt(0);

			// .....if condition returns if username and key doesn't exists
			
			if (StudentAccount.get(element) == null) {
					HashMap<String, Student> studRecord = new HashMap<String, Student>();
					studRecord.put(username, student);
	
					synchronized (StudentAccount) {
						StudentAccount.put(element, studRecord);
					}
					DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String string = student.username + " / " + student.Password + " / " + student.firstname + " / " + student.lastname
							+ " / " + student.phoneNumber + " / " + student.EducationalInstitute + " / " + dateFormat.format(cal.getTime());
					
					writeToFile(string, path);
	
					System.out.println(student.username + " Created");
					logtask.WriteLog("User Account Successfully Created");
					return  " User Account for  "+ username +"  Successfully Created on server " + this.servername;
			}
			
			// .....else condition whether there exists key for first character of the username

			
			else {
				HashMap<String, Student> stud = StudentAccount.get(element);

				if (stud.get(username) == null) {
					
					stud.put(username, student);

					synchronized (StudentAccount) {
						StudentAccount.put(element, stud);
					}
					DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String string = student.username + " / " + student.Password + " / " + student.firstname + " / " + student.lastname + " / " + student.phoneNumber
							+ " / " + student.EducationalInstitute + " / "
							+ dateFormat.format(cal.getTime());
					writeToFile(string, path);
					System.out.println(student.username + " Created at "
							+ dateFormat.format(cal.getTime()));
					logtask.WriteLog(student.username + " Created at "
							+ dateFormat.format(cal.getTime()));
					logtask.WriteLog("User Account Successfully Created");
					return  " User Account for  "+ username +"  Successfully Created on server " + this.servername;
				}

				// return if there exists same username related with particular
				// key
				else {
					logtask.WriteLog("user Already exsists");
					System.out.println("User Account Already exsists");
					return "User Account Already exsists";
				}
			}
		} catch (IOException err) {
			err.printStackTrace();
		}
		return "Invalid : Error has been occured";
	}

	/*
	 * To reserve the book in the same library
	 * 
	 * */
	
	
	@Override
	public String reserveBook(String username, String password,String bookname, String authorname) {
		LoggerTask logtask = null;
		
		Iterator<Book> bko = BookDetails.values().iterator();

		while (bko.hasNext()) {
			Book book = bko.next();
			System.out.println(book.BookName + " " + book.AuhorName + "  " + book.numberOfCopies);
		}

		@SuppressWarnings("unused")
		Book book = new Book();

		String str = bookname + authorname;

		Character element = username.toLowerCase().charAt(0);
		try {
			logtask = new LoggerTask();
		
		if (StudentAccount.get(element) != null) {
			HashMap<String, Student> st = StudentAccount.get(element);

			if (st.get(username) != null) {
				Student student = st.get(username);

				if (student.Password.equalsIgnoreCase(password)) {

					if (BookDetails.get(str) != null) {
						Book bookdet = BookDetails.get(str);
						
						if (bookdet.numberOfCopies > 0) {

							
							
							BooKHistory bk = new BooKHistory();
							String str1 = bookname + authorname + username;
							if (bookHistory.get(str1) == null) {
								
								bookdet.BookName = bookname;
								bookdet.AuhorName = authorname;
								bookdet.numberOfCopies = bookdet.numberOfCopies - 1;
								
								BookDetails.remove(str);
								BookDetails.put(str, bookdet);
								
								
								bk.Username = username;
								bk.AuthorName = authorname;
								//System.out.println(bk.AuthorName + " = " + str1);
								bk.BookName = bookname;
								bk.institute = student.EducationalInstitute;
								@SuppressWarnings("unused")
								DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
								Calendar cal = Calendar.getInstance();
								bk.issueDate = cal.getTime();
								cal.add(Calendar.DATE, 14);
								bk.dueDate = cal.getTime();
								synchronized (bookHistory) {
									bookHistory.put(str1, bk);
								}
							}else{
								return "Already issued with the same username";
							}

						} else {
							return "This book is out of stock";
						}
					} else {
						return "Can not find book with information provided";
					}
				} else {
					return "Student name and password doesn't match";
				}

			} else {
				return "username doesn't exists";
			}
		} else {
			return "username doesn't exists";
		}
		Iterator<BooKHistory> bko1 =
				bookHistory.values().iterator();
				while(bko1.hasNext()){
					BooKHistory book1 = bko1.next();
					System.out.println("Bookname = "+book1.BookName + " | Authorname = " +
					book1.AuthorName + " | Username = " + book1.Username + " | Issuedate = "
					+book1.issueDate + " | Due Date " + book1.dueDate);
				}
				logtask.WriteLog("\nIssued to username : " + username  + "  with key id = " + str + " bookname : " + bookname + " authorname : " + authorname);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "\nissued to username : " + username  + "  with key id = " + str + " bookname : " + bookname + " authorname : " + authorname;
		
	}

	/*
	 * admin function
	 * returns the list of students who did not return their books 
	 * based on number of days provided as function argument 
	 * 
	 * */

	@Override
	public String getNonreturners(String AdminUsername, String AdminPassword,String instname, String numOfDays) {
		LoggerTask logtask = null;
		String str = " ";
					try {
						logtask = new LoggerTask();
				
	 			 Iterator<BooKHistory> bko1 = bookHistory.values().iterator();
					
				 while(bko1.hasNext()){
		
				 BooKHistory book1 = bko1.next();
				 Date idate = book1.issueDate;
				 Date ddate = book1.dueDate;
				// if(idate.compareTo(ddate) > 0){
						long lt = idate.getTime()-ddate.getTime();
						long diffDays = lt / (1000 * 60 * 60 * 24);
						if(diffDays >= Integer.parseInt(numOfDays)){
						System.out.println(diffDays);
						System.out.println("Bookname = "+book1.BookName + " | Authorname = " +
								 book1.AuthorName + " | Username = " + book1.Username + " | Issuedate = "
								 +book1.issueDate + " | Due Date " + book1.dueDate + " Above days :" + diffDays + " | Fine = " + diffDays*2);
								 str+= "\n"+"Bookname = "+book1.BookName + " | Authorname = " +
										 book1.AuthorName + " | Username = " + book1.Username + " | Issuedate = "
										 +book1.issueDate + " | Due Date " + book1.dueDate + " Above days :" + diffDays + " | Fine = " + diffDays*2;
					}
				 }
				 logtask.WriteLog(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
}
	
	
	/* 
	 * Called by admin to change the current date of the books issued in order to get fine
	 * 
	 * 
	 */
	public String setDuration(String username, String bookname, String NumofDays){
		LoggerTask logtask = null;
		String str = " ";
		try {
			logtask = new LoggerTask();
		
			@SuppressWarnings("unused")
			Iterator<BooKHistory> bko1 = bookHistory.values().iterator();
			
			for(Map.Entry<String, BooKHistory> entry : bookHistory.entrySet()){
				BooKHistory history =  entry.getValue();
				if(history.Username.equalsIgnoreCase(username) && history.BookName.equalsIgnoreCase(bookname)){
						Date x = history.issueDate;
						Calendar cal = Calendar.getInstance();
						cal.setTime(x);
						cal.add(Calendar.DATE, Integer.parseInt(NumofDays));
						history.issueDate = cal.getTime();
						System.out.println("Bookname = "+history.BookName + " | Authorname = " +
								history.AuthorName + " | Username = " + history.Username + " | Return Date = "
								 +history.issueDate + " | Due Date " + history.dueDate + " | Old Date = " +x);
						str+="Bookname = "+history.BookName + " | Authorname = " +
								history.AuthorName + " | Username = " + history.Username + " | Return Date = "
								 +history.issueDate + " | Due Date " + history.dueDate + " | Old Date = " +x;
					}
				}	
			logtask.WriteLog(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Deserialized();
		//mappingDatabase();
		return "Updated data";
	}
	
	/*
	 * 
	 * 
	 * */
	private void updateBookHistory(String userName, String bookName, String authorName){
		String str = bookName + authorName + userName;
		@SuppressWarnings("unused")
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		BooKHistory bk = new BooKHistory();
			bk.Username = userName;
			bk.AuthorName = authorName;
			//System.out.println(bk.AuthorName + " = " + str);
			bk.BookName = bookName;
			bk.institute = servername;
			bk.issueDate = cal.getTime();
			cal.add(Calendar.DATE, 14);
			bk.dueDate = cal.getTime();
	
		synchronized (bookHistory) {
			bookHistory.put(str, bk);
		}
	}
	/*
	 * called from the client to reserve in other libraries 
	 * calling reserveBook to check in its own database from which it is calling 
	 * 
	 * */
	@Override
	public String reserveInterLibrary(String username, String password,String bookname, String authorname) {
		@SuppressWarnings("unused")
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		LoggerTask logtask = null;
		try {
					logtask = new LoggerTask();
					
					String response = reserveBook(username, password, bookname, authorname);
					String str = bookname + authorname + username;
					String expResponse = "registered with key id = " + str + " username = "+ username;
					if (!expResponse.equals(response)) {
						String dataToSendToLibraries = servername+":"+bookname+":"+authorname;
							if(portNumber != 1001 && initiateConnectionWithOtherServers(1001, dataToSendToLibraries)){
								updateBookHistory(username,bookname,authorname);
								logtask.WriteLog("Book " + bookname +"  was registered in mcgill  library  from " + servername + " username : "+username + "at " + cal.getTime());
								return "Book " + bookname +" was registered in mcgill library from " + servername + " username : "+username + "at " + cal.getTime();
							}
							else if(portNumber != 1002 && initiateConnectionWithOtherServers(1002, dataToSendToLibraries)){
								updateBookHistory(username,bookname,authorname);
								logtask.WriteLog("Book " + bookname +"  was registered in vanier   from " + servername + " username : "+username + "at " + cal.getTime());
								return "Book  " + bookname +" was registered in vanier library   from " + servername + " username : "+username + "at " + cal.getTime();
							}
							else if(portNumber != 1000 && initiateConnectionWithOtherServers(1000, dataToSendToLibraries)){
								updateBookHistory(username,bookname,authorname);
								logtask.WriteLog("Book " + bookname +"  was registered in concordia library   from " + servername + " username : "+username + "at " + cal.getTime());
								return "Book " + bookname +"  was registered in concordia library   from " + servername + " username : "+username + "at " + cal.getTime();
							}
					}
					else{
						logtask.WriteLog("registered with key id = " + str + " username = " + username);
						return "registered with key id = " + str + " username = " + username;
					}
		logtask.WriteLog("Book "+ bookname  +" with authorname : " + authorname+ " was not found in any of the libraries called from " + this.servername + " library");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Book .."+ bookname  +" with authorname : " + authorname+ " was not found in any of the libraries called from " + this.servername + " library";
	}

	private boolean reserveBookForInterLibrary(String instName,String bookName,String authorName) {
		String str = bookName + authorName;
		if (BookDetails.get(str) != null) {
			Book bookdet = BookDetails.get(str);
			//System.out.println(bookdet.numberOfCopies + " " + bookdet.BookName);
			if (bookdet.numberOfCopies > 0) {
				bookdet.numberOfCopies = bookdet.numberOfCopies - 1;
				synchronized (BookDetails) {
					BookDetails.put(str, bookdet);
				}
				return true;
			}
		}
		return false;
	}
	
	public void writeToFile(String str, String path) throws IOException {
		FileWriter fileWriter = new FileWriter(path);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(str);
		bufferedWriter.newLine();
		bufferedWriter.write("===================================================================================");
		bufferedWriter.close();
	}

	
	/* -------------------------------------- UDP Client -------------------------------------------------------------------
	 * 
	 * UDP client initilaizing and calling the UDP Server with port and data
	 * 
	 * ---------------------------------------------------------------------------------------------------------
	 * */
	public boolean initiateConnectionWithOtherServers(int port, String data) {
		DatagramSocket aSocket = null;
		byte[] dataBytes = data.getBytes();
		boolean retVal = false;
		String response = "";
		try {
			aSocket = new DatagramSocket();
			InetAddress aHost = InetAddress.getByName("localhost");
			DatagramPacket request = new DatagramPacket(dataBytes,dataBytes.length, aHost, port);
			aSocket.send(request);
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			byte[] dataFromServer = new byte[reply.getLength()];
			System.arraycopy(reply.getData(), reply.getOffset(),
					dataFromServer, 0, reply.getLength());
			response = new String(dataFromServer);
			response = response.trim();
			if(response.equals("1")){
				retVal = true;
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
		return retVal;
	}
	/* ------------------------------------------------- UDP Server --------------------------------------------------------
	 * run method for the UDP server on three different threads
	 * has three copies running for 3 different servers
	 * waiting for UDP calls from the server
	 * 
	 * ---------------------------------------------------------------------------------------------------------
	 * */
	@Override
	public void run() {
		DatagramSocket host = null;
		try {
			host = new DatagramSocket(this.portNumber);
			System.out.println("udp connection running..");
			while (true) {
				System.out.println("UDP " + this.servername
						+ " is running at Port " + this.portNumber);
				byte[] receiveData = new byte[1024];
				DatagramPacket request = new DatagramPacket(receiveData,receiveData.length);
				host.receive(request);
				byte[] dataCopy = new byte[request.getLength()];
				System.arraycopy(request.getData(), request.getOffset(),
						dataCopy, 0, request.getLength());
				String actualData = new String(dataCopy);
				actualData = actualData.trim();
				String datArry[] = actualData.split(":");
				String response = "0";
				if (datArry.length == 3) {
					try {
						if(reserveBookForInterLibrary(datArry[0], datArry[1],datArry[2])){
							response = "1";
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				byte[] responseByte = response.getBytes();
				DatagramPacket reply = new DatagramPacket(responseByte,responseByte.length, request.getAddress(),request.getPort());
				host.send(reply);
			}
		} catch (SocketException e) {
			System.out.println("Socekt " + e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (host != null) {
				host.close();
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	private void mappingDatabase() {
		String path = "../LibraryServer/src/com/Server/Pack/SerializedObjects/"
				+ servername + "_STUDENT.txt";
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("");
		} else {
			try {
				FileInputStream fis = new FileInputStream(path);
				ObjectInputStream ois = new ObjectInputStream(fis);
				synchronized (ois) {
					StudentAccount = (HashMap<Character, HashMap<String, Student>>) ois.readObject();
				}
				ois.close();
				fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				return;
			} catch (ClassNotFoundException c) {
				System.out.println("Class not found");
				c.printStackTrace();
				return;
			}
			System.out.println("Deserialized Book History Database..");

			try {
				String path2 = "../LibraryServer/src/com/Server/Pack/SerializedObjects/"
						+ servername + "_BOOK.txt";
				FileInputStream fis = new FileInputStream(path2);
				ObjectInputStream ois = new ObjectInputStream(fis);
				synchronized (ois) {
					BookDetails = (HashMap<String, Book>) ois.readObject();
				}
				ois.close();
				fis.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				return;
			} catch (ClassNotFoundException c) {
				System.out.println("Class not found");
				c.printStackTrace();
				return;
			}
			System.out.println("Deserialized Student Data..");
		}

		try {
			String path3 = "../LibraryServer/src/com/Server/Pack/SerializedObjects/"
					+ servername + "_BOOKHISTORY.txt";
			FileInputStream fis = new FileInputStream(path3);
			ObjectInputStream ois = new ObjectInputStream(fis);
			synchronized (ois) {
				bookHistory = (HashMap<String, BooKHistory>) ois.readObject();
			}
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
 }

	/*------------------------------------------------------------------------------*/
	/*
	 * Exit method that will run at the time user will close the application it
	 * will serailize all the hashmap and arraylist objects that are being used
	 */
	public void Deserialized(){
		try {
			LoggerTask logtask = null;
			logtask = new LoggerTask();

			String path = "../LibraryServer/src/com/Server/Pack/SerializedObjects/"
					+ servername + "_STUDENT.txt";
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			synchronized (oos) {
				oos.writeObject(StudentAccount);
			}

			oos.close();
			fos.close();
			System.out
					.print("\nSerialized HashMap data is saved in Student Database History");
			logtask.WriteLog("\nSerialized HashMap data is saved in Book Database History");

			String path2 = "../LibraryServer/src/com/Server/Pack/SerializedObjects/"+ servername + "_BOOK.txt";

			FileOutputStream fos1 = new FileOutputStream(path2);
			ObjectOutputStream oos1 = new ObjectOutputStream(fos1);

			synchronized (oos1) {
				oos1.writeObject(BookDetails);
			}
			oos1.close();
			fos1.close();
			System.out
					.print("\nSerialized Student Account database is saved in Database");
			logtask.WriteLog("\nSerialized Student Account database is saved in Database");

			String path3 = "../LibraryServer/src/com/Server/Pack/SerializedObjects/"
					+ servername + "_BOOKHISTORY.txt";
			FileOutputStream fos2 = new FileOutputStream(path3);
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);

			synchronized (oos2) {
				oos2.writeObject(bookHistory);
			}

			oos2.close();
			fos2.close();
			System.out
					.printf("\nSerialized Book Database is saved in Database");
			logtask.WriteLog("\nSerialized Book Database is saved in Database");

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public String exit() {
		Deserialized();
		return "Deserialized";
	}
	
	
	
}
