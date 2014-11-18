package com.Student.Client;

import java.io.IOException;
import java.util.Scanner;

public class StudentMenu {
	
	StudentClient student = new StudentClient();
	static Scanner keyboard; 
	/*
	 * Function used to display menu option to the Library Student Client
	 * 
	 * Used while loop to ask for more options 
	 * 
	 * */
	public StudentMenu() throws IOException{
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
//					try {
//						System.out.println("Thread 0 is executing....");
//						//student.createAcc("maxi123", "maxi123", "maxi", "rattan", "4388860786", "kunwarrattan", "mcgill");
//					}catch (IOException e) {
//						e.printStackTrace();
//					}
				
			}
		});
		
		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
					try {
				
						//Thread.sleep(15);
						
		
						Thread.sleep(15);
					//	System.out.println("Thread 1 is executing....");
						System.out.println("Thread 1 is executing....");
						student.createAcc("boromirking", "boromirking", "maxi", "rattan", "4388860786", "kunwarrattan", "concordia");
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				
			}
		});
	
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("Thread 2 is executing....");
					student.createAcc("frodobaggins", "frodobaggins", "maxi", "rattan", "4388860786", "kunwarrattan", "concordia");
					Thread.sleep(15);
						student.createAcc("maxi123", "maxi123", "maxi", "rattan", "4388860786", "kunwarrattan", "vanier");
					student.reserve("maxi123", "maxi123", "c", "kunwar", "concordia");
					System.out.println("Thread 2 is executing....");
					student.reserve("maxi123", "maxi123", "c#", "kunwar", "mcgill");
					System.out.println("Thread 2 is executing....");
					student.reserveInter("boromirking", "boromirking", "bones", "kathy", "concordia");
					Thread.sleep(15);
					
					
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
		
			}	
		});
		
		
		Thread thread3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					//student.createAcc("rattan123", "rattan123", "maxi", "rattan", "4388860786", "kunwarrattan", "vanier");
					Thread.sleep(15);
					//student.reserve(,"vanier");
					student.reserve("maxi123", "maxi123", "c", "rattan", "vanier");
					student.reserveInter("maxi123", "maxi123", "SCM", "rattan", "concordia");
					Thread.sleep(15);
					System.out.println("Thread 3 is executing....");
					student.createAcc("maxi12345", "maxi12345", "maxi", "rattan", "4388860786", "kunwarrattan", "mcgill");
					student.reserveInter("maxi12345", "maxi12345", "Quality", "rattan", "mcgill");
					student.reserveInter("rattan123", "rattan123", "punjabi", "kunwar", "vanier");
					System.out.println("Thread 3 is executing....");
					student.reserveInter("frodobaggins", "frodobaggins", "bones", "kathy", "concordia");
					
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
//				try {
//					//student.exit();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
			}
		});
		thread.start();thread1.start();thread2.start();thread3.start();
		
	}
	
	public void display() throws IOException{
		
		int choice = 0 ;
			
				System.out.print("\nFollowing Options are available for you");
				System.out.print("\n1 Press 1 to Create an Account");
				System.out.print("\n2 Press 2 to Reserve a book");
				System.out.print("\n3 Press 3 to Reserve Inter Library");
				System.out.print("\n4 Press 4 to Exit Application");
				System.out.print("\n Please Select your choice : = ");
				while(true) {
					Boolean valid = false;
					while (!valid) {
						try {
							choice = keyboard.nextInt();
							valid = true;

						} catch (Exception e) {
							System.out.println("Sorry ! Please enter again:");
							valid = false;
							keyboard.nextLine();
						}
					}
					switch(choice){
						case 1:
							student.createAccount();
							display();
							break;
						case 2:
							student.reserveBook();
							display();
							break;
						case 3:
							student.reserveInterLibrary();
							display();
							break;
						case 4:											// Case to exit out of the while Loop
							System.out.println("Have a Nice Day");	
							keyboard.close();
							student.exit();
							System.exit(0);
						default:
							System.out.println("Invalid Input");
							display();
							break;
						}
				   }
				}
			


	public static void main(String[] args) throws IOException {
		StudentMenu stuCli = new StudentMenu();
		try {
			keyboard = new Scanner(System.in);
			stuCli.display();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
