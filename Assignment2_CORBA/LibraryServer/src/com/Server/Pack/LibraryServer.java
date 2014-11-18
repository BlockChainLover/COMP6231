/**
 * 
 */
package com.Server.Pack;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
//import java.util.Scanner;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

/**
 * @author Kunwar
 *
 */
public class LibraryServer  {
	
	/**
	 * @param args
	 * @throws InvalidName 
	 * @throws WrongPolicy 
	 * @throws ServantAlreadyActive 
	 * @throws ObjectNotActive 
	 * @throws FileNotFoundException 
	 * @throws AdapterInactive 
	 * 
	 */
	public static void main(String[] args) throws InvalidName, ServantAlreadyActive, WrongPolicy, ObjectNotActive, FileNotFoundException, AdapterInactive {
		try{
		LoggerTask log = new LoggerTask();
		ORB orb = ORB.init(args,null);
		POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		
		final ServerFunction concordiaUDP = new ServerFunction("Concordia",1000);
		final ServerFunction mcgillUDP = new ServerFunction("mcgill",1001);
		final ServerFunction vanierUDP = new ServerFunction("vanier",1002);
		
		
		byte[] id1 = rootPOA.activate_object(concordiaUDP);
		byte[] id2 = rootPOA.activate_object(mcgillUDP);
		byte[] id3 = rootPOA.activate_object(vanierUDP);
		
		org.omg.CORBA.Object ref1 = rootPOA.id_to_reference(id1);
		org.omg.CORBA.Object ref2 = rootPOA.id_to_reference(id2);
		org.omg.CORBA.Object ref3 = rootPOA.id_to_reference(id3);
		
		String ior1 = orb.object_to_string(ref1);
		String ior2 = orb.object_to_string(ref2);
		String ior3 = orb.object_to_string(ref3);
		
		System.out.print(" \n Concordia Server is started at " + " " +(new Date().toString()+ " and is up and running \n\n"));
		log.WriteLog(ior1 + " \n Concordia Server is started at " + " " +(new Date().toString()+ " and is up and running \n\n"));;
		
		System.out.print(" \n McGill Server is started at " + " " +(new Date().toString()+ " and is up and running \n\n"));
		log.WriteLog(ior2 + " \n McGill Server is started at " + " " +(new Date().toString()+ " and is up and running \n\n"));
		
		System.out.print(" \n Vanier Server is started at " + " " +(new Date().toString()+ " and is up and running \n\n"));
		log.WriteLog(ior3 + " \n Vanier Server is started at " + " " +(new Date().toString()+ " and is up and running \n\n"));
		//log.closeQuietly();
		
		System.out.print(" \n Concordia Server is started at PORT 1000" + " on " +(new Date().toString()+ " and is up and running \n\n"));
		log.WriteLog(" \n Concordia Server is started at PORT 1000" + " on " +(new Date().toString()+ " and is up and running \n\n"));;
		
		System.out.print(" \n McGill Server is started at PORT 1001 " + " on " +(new Date().toString()+ " and is up and running \n\n"));
		log.WriteLog(" \n McGill Server is started at PORT 1001" + " on " +(new Date().toString()+ " and is up and running \n\n"));
		
		System.out.print(" \n Vanier Server is started at  PORT 1002" + " on " +(new Date().toString()+ " and is up and running \n\n"));
		log.WriteLog(" \n Vanier Server is started at PORT 1002" + " on " +(new Date().toString()+ " and is up and running \n\n"));
		
		PrintWriter file1 = new PrintWriter("src/Concordia.txt");
		file1.println(ior1);
		file1.close();
		PrintWriter file2 = new PrintWriter("src/McGill.txt");
		file2.println(ior2);
		file2.close();
		PrintWriter file3 = new PrintWriter("src/Vanier.txt");
		file3.println(ior3);
		file3.close();
		rootPOA.the_POAManager().activate();
		orb.run();
		}catch(Exception err){
			err.printStackTrace();
		}finally{
			
		}
	}
}
