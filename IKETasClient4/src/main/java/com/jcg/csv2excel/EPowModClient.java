package com.jcg.csv2excel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import com.jcg.csv2excel.EPowModInterface;

public class EPowModClient {
	static int portnumber;
	private static Logger logger = Logger.getLogger(EPowModClient.class);

	public static void main(String[] args) throws IOException {
		
		String clientpath, clientpath2;
		String serverpath, serverpath2;
		

		System.out.println("Enter Port Number :");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String input = bufferedReader.readLine();
        portnumber = Integer.parseInt(input);
		
		try {
			Registry myReg = LocateRegistry.getRegistry("127.0.0.1",portnumber);
			//Registry myReg = LocateRegistry.getRegistry("141.52.44.103",1078);
			EPowModInterface c = (EPowModInterface) myReg.lookup("remoteObject");
			
			
			/*1.1. Term
			 * Stream the CSV-File from Client to Server for the first File
			 */
			
			clientpath= "config/bus.csv";//Clientpath is the path of the Source of CSV-File
			serverpath = "config/bus.csv";//Serverpath is the path of the Target of CSV-File
			
			File clientpathfile = new File(clientpath);
			byte [] mydata=new byte[(int) clientpathfile.length()];
			FileInputStream in=new FileInputStream(clientpathfile);	
				System.out.println("uploading to server...");		
			 in.read(mydata, 0, mydata.length);					 
			 c.uploadFileToServer(mydata, serverpath, (int) clientpathfile.length());
			 
			 in.close();
			 
			 /*1.2. Term
				 * Stream the CSV-File from Client to Server for the first File
				 */
			 
			 	clientpath2= "config/line.csv";//Clientpath is the path of the Source of CSV-File
				serverpath2 = "config/line.csv";//Serverpath is the path of the Target of CSV-File
				
				File clientpathfile2 = new File(clientpath2);
				byte [] mydata2=new byte[(int) clientpathfile2.length()];
				FileInputStream in2=new FileInputStream(clientpathfile2);	
					System.out.println("uploading to server...");		
				 in2.read(mydata2, 0, mydata2.length);					 
				 c.uploadFileToServer(mydata2, serverpath2, (int) clientpathfile2.length());
				 
				 in.close();
			 
			 /*2. Term
			  * Make a Workbook out of the available CSV-File in Server for the second File
			  */
			 
			String xlsLoc = "config/", csvLoc = "config/bus.csv",csvLoc1 = "config/line.csv" ,fileLoc = "";
			
			fileLoc = c.convertCsvToXls(xlsLoc, csvLoc, csvLoc1, "BUS", "BRANCH");
			logger.info("File Location Is?= " + fileLoc);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		
	}
		
	}
