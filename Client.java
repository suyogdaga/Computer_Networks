package com.cn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


public class Client {
	Socket requestSocket;           //socket connect to the server
	ObjectOutputStream out;         //stream write to the socket
 	ObjectInputStream in;          //stream read from the socket
	String message;                //message send to the server
	BufferedReader br;
	String clientName;
	int portNo;
	
	
	public Client(String clientName) {
		this.clientName = clientName;
		
	}

	
	void run()
	{
		try{
			//create a socket to connect to the server
			requestSocket = new Socket("localhost", 8000);
			System.out.println("Connected to localhost in port 8000");
			//initialize inputStream and outputStream
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			//get Input from standard input
			br = new BufferedReader(new InputStreamReader(System.in));
			
			final Thread sendThread = new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
						while(true)
						{
							sendThreadMethod();
						}	
					
				}
				
			});
			sendThread.start();
			final Thread receiveThread = new Thread(new Runnable(){

				@Override
				public void run() {
				
						while(true){
							receiveThreadMethod();
						}
					
					// TODO Auto-generated method stub
					
				}
				
			});
			receiveThread.start();
			
			TimeUnit.MINUTES.sleep(20);
			
		}
		catch (ConnectException e) {
    			System.err.println("Connection refused. You need to initiate a server first.");
		} 
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		
		catch(IOException ioException){
			ioException.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			//Close connections
			try{
				
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	//send a message to the output stream
	
	private void sendThreadMethod(){
		try{
			
			message = br.readLine();
			//Send the sentence to the server
			
			out.writeObject(message);
			
			out.flush();

			
		}
		catch(Exception e){
			
		}
	}
	
	private void receiveThreadMethod(){
		try{
			Object o = in.readObject();
			if(o instanceof String)
			{
				String msg = (String)o;
				int clientNo = Character.getNumericValue(msg.charAt(msg.length()-1));
				msg = msg.substring(0, msg.length()-1);
				
				 System.out.println("Message received from client "+clientNo+": "+msg);
			 }
			else {
				
				System.out.println("Receiving file....");
				FileHelper fileHelper = (FileHelper)o;
				
				Path p = Paths.get(fileHelper.getFileName());
				
				String name = p.getFileName().toString();
				
				String filePath = "com\\cn\\client"+fileHelper.getReceiverNo()+"\\"+name;
				
				File file = new File(filePath);
				if(!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				
				if ( ! file.exists() )
		            		file.createNewFile();
		            
				Files.write(file.toPath(), fileHelper.getContent());
				System.out.println("File received from client"+fileHelper.getSenderNo()+" - "+name);
				
			}
				
		}
		catch(Exception e){
			
		}
	}
	
	void sendMessage(String msg)
	{
		try{
			//stream write the message
			out.writeObject(msg);
			out.flush();
			
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	//main method
	public static void main(String args[])
	{
		Client client = new Client(args[0]);
		client.run();
	}

}