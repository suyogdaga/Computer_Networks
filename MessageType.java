package com.cn;
public class MessageType {
	private String message;
	public MessageType(String message){
		this.message = message;
	}
	
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String[] parseFirstMessage(){
		String[] messageParts = getMessage().split(" "), value = null;
		switch(messageParts[0]){
		case "broadcast":{value = parseBroadcastMessage(getMessage());}break;
		case "unicast":{value = parseUnicastMessage(getMessage());}break;
		case "blockcast":{value = parseBlockcastMessage(getMessage());}break;
		
		}
		return value;
	}
	
	private String[] parseBroadcastMessage(String message){
		String[] strings = message.split(" ");
		String mType = strings[1];
		strings = message.split("\"");
		String actualMessage = strings[1];
		if(mType.equals("message"))
			return new String[]{actualMessage};
		else
			return new String[]{actualMessage,"file"};
	}
	private String[] parseUnicastMessage(String message){
		String[] strings = message.split(" ");
		String mType = strings[1];
		strings = message.split("\"");
		String actualMessage = strings[1];
		String clientName = strings[2];
		if(mType.equals("message"))
			return new String[]{actualMessage,clientName,"unicast"};
		else
			return new String[]{actualMessage,clientName,"unicast","file"};
	}
	private String[] parseBlockcastMessage(String message){
		String[] strings = message.split(" ");
		strings = message.split("\"");
		String actualMessage = strings[1];
		String clientName = strings[2];
		
		return new String[]{actualMessage,clientName,"blockcast"};
	}
}

