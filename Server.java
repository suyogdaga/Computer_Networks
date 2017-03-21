package com.cn;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import com.cn.*;


public class Server {

	private static final int sPort = 8000;   //The server will be listening on this port number
	private static final ArrayList<Integer> clientNumbers=new ArrayList<Integer>();
	private static final ArrayList<ObjectOutputStream> clientStreams = new ArrayList<ObjectOutputStream>();
	
	public static void main(String[] args) throws Exception {
		System.out.println("The server is running."); 			
        	ServerSocket listener = new ServerSocket(sPort);
        	
		int clientNum = 1;
        	try {
            		while(true) {
            			clientNumbers.add(clientNum);
                		new Handler(listener.accept(),clientNum).start();
				System.out.println("Client "  + clientNum + " is connected!");
				clientNum++;
            			}
        	} finally {
            		listener.close();
        	} 
 
    	}

	/**
     	* A handler thread class.  Handlers are spawned from the listening
     	* loop and are responsible for dealing with a single client's requests.
     	*/
    	private static class Handler extends Thread {
        	private String message;    //message received from the client
        	private Socket connection;
        	private ObjectInputStream in;	//stream read from the socket
        	private ObjectOutputStream out;    //stream write to the socket
        	private int no;		//The index number of the client
        	private MessageType messageType;
        	public Handler(Socket connection, int no) {
            		this.connection = connection;
            		this.no = no;
            		
        	}

        public void run() {
 		try{
			//initialize Input and Output streams
			out = new ObjectOutputStream(connection.getOutputStream());
			clientStreams.add(out);
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			TimeUnit.SECONDS.sleep(2);
			while(true){
				try{
					String message = (String)in.readObject();
					messageType = new MessageType(message);
					messageSender(this.no,clientStreams,messageType.parseFirstMessage());
					
				}catch(Exception e){
					
				}
			}
			
		}
		catch(IOException | InterruptedException ioException){
			System.out.println("Disconnect with Client " + no);
		}
		finally{
			//Close connections
			try{
				in.close();
				out.close();
				connection.close();
			}
			catch(IOException ioException){
				System.out.println("Closing connection Disconnect with Client " + no);
			}
		}
	}

	
	
		private void messageSender(int no,ArrayList<ObjectOutputStream> clientstreams, String[] messageType) {
			
			if(messageType.length==1){
				//broadcasting all messages
				System.out.println("client"+no+" broadcasted message");
				int index = no-1;
				messageType[0]= messageType[0]+no;
				for(ObjectOutputStream o: clientStreams){
					
					if(o!=clientStreams.get(index))
					sendMessage(o,messageType[0]);
				}
			}
			else if(messageType.length==2 && messageType[1].equals("file")){
				//System.out.println("file sender");
				//System.out.println(messageType[0]);
				System.out.println("client"+no+" broadcasted file");
				int index = no-1;
				try {
					Path path = Paths.get(messageType[0]);
					byte[] content = Files.readAllBytes(path);
					
					for(ObjectOutputStream o: clientStreams){
						if(o!=clientStreams.get(index)){
							int receiverNum = clientStreams.indexOf(o)+1;
							sendFile(o, new FileHelper(content,messageType[0],receiverNum,no));
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else if(messageType.length==3){
				
				if(messageType[2].equals("unicast")){
					//unicasting message
					
					String clientName = messageType[1];
					
					char lastChar = clientName.charAt(clientName.length()-1);
					int clientNo = Character.getNumericValue(lastChar);
					System.out.println("client"+no+" unicasted message to client"+clientNo);
					int index = clientNo-1;
					messageType[0]+=no;
					sendMessage(clientStreams.get(index),messageType[0]);
					
				}
			
				else{
					//blockcasting message
					String clientName = messageType[1];
					char lastChar = clientName.charAt(clientName.length()-1);
					int clientNo = Character.getNumericValue(lastChar);
					int index = clientNo-1;
					System.out.println("client"+no+" blockcasted message to client"+clientNo);
					int index2 = no-1;
					messageType[0]+=no;
					for(ObjectOutputStream o :clientStreams){
						if(o!=clientStreams.get(index) && o!=clientStreams.get(index2)){
							sendMessage(o, messageType[0]);
						}
					}
				}
			}
			
			else if(messageType.length==4){
				//unicasting file
				try {
					Path path = Paths.get(messageType[0]);
					byte[] content = Files.readAllBytes(path);
					String clientName = messageType[1];
					//System.out.println("name: "+clientName);
					char lastChar = clientName.charAt(clientName.length()-1);
					int clientNo = Character.getNumericValue(lastChar);
					//System.out.println("no. :"+clientNo);
					int index = clientNo-1;
					System.out.println("client"+no+" unicasted file to client"+clientNo);
					sendFile(clientStreams.get(index), new FileHelper(content,messageType[0],index+1,no));
						
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

		private void sendMessage(ObjectOutputStream out, String message2){
			try{
				//System.out.println("message: "+message2);
				out.writeObject(message2);
				out.flush();
			}catch(Exception e){
				
			}
		}
		
		private void sendFile(ObjectOutputStream out, FileHelper helper){
			//System.out.println("Length of file: "+helper.getContent().length);
			try{
				out.writeObject(helper);
				out.flush();
			}catch(IOException e){
				
			}
		}

		
		
		
    }
    	
    	

}