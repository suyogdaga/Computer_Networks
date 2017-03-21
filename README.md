# Computer_Networks
Internet Chat Application

CNT5106 Computer Networks : Internet Chat Application
                            **************************************************************
Created by: Avinash Iyer and Suyog Daga

This is an Internet Chat Application developed in Java language, using Eclipse IDE.

We have used a Client - Server Model, where a client interacts with another client via server. A client can send a file or message to another client.

Overall outline for project:
1) Broadcasting message to all clients.
2) Unicast a message to particular client.
3) Blockcast a message to all clients except for the specified client.
4) Broadcast a file to all clients.
5) Unicast a file to particular client.


File Structure:
We have created 4 classes :
1) Client: Client class implementation
2) Server : Server class implementation
3) MessageType : Assists server for parsing messages and sending Broadcast, Unicast and Blockcast messages from Server respectively.
4) FileHelper : Assists server for parsing file address and then Broadcasting and Unicasting the file from Server respectively.

How to use files:
1) Root directory for project is "com/cn" because this is the main package structure. All files ie Server, Client, FileHelper and MessageType classes belong in "com/cn" directory. For eg:- if current directory on your machine is "C:\Users\Desktop", then file structure for Server.java should be "C:\Users\Desktop\com\cn\Server.java". 

2) Before compiling ensure that you are in a folder above com/cn. For example, if directory structure is "C:\Users\com\cn", then navigate to "C:\Users" and then, Server and Client classes should be compiled and run as follows:
	i) Server - javac com/cn/Server.java
		    java com/cn/Server
	ii)Client - javac com/cn/Client.java
		  - java com/cn/Client clientname
(Here clientname should be like client1, client2 etc , and should be named in the order in which they connect to Server. For example, 'client1' should be first to connect followed by 'client 2' and so on.)

3) Open instances of command prompt and perform the above compilations to run Server and Client classes. Find below examples of how to execute the various operations:
	Messages:-
	 i) Broadcasting message -  broadcast message "message1" . This will send message to all clients except sending client.
	ii) Unicast message - unicast message "message1" client2 .This will send message to only client2 from sending client.
	iii) Blockcast message - blockcast message "message1" client2. This will send message to all clients except client2 and sending client.
	
	Files:-
	iv) Broadcasting file - broadcast file "C:\Users\Desktop\abc.txt". - This will send file 'abc.txt' to all clients except sending client. 'abc.txt' will be copied to receiving client's respective folder, which would be created in 'com/cn' automatically.
	v)Unicast file - unicast file "C:\Users\Desktop\abc.txt" client2 . - This will send file 'abc.txt' to client2 only.

4) For sending files, folders for respective clients will be created automatically if they do not exist.

NOTE:-
1. Root directory (com/cn) is strictly required. (Refer screenshots in README.docx file for any help).
2. Clients should be named in the order in which they connect. Eg:- first client connecting should be named "client1".
3. Client cannot send message or file to itself.
4. Port is fixed at 8000.

 
For any questions, please e-mail us at 
i)	avinashiyer4292@ufl.edu
ii)	suyogdaga@ufl.edu 
