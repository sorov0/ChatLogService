package com.chatlogservice.chatlogservice.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.chatlogservice.chatlogservice.model.UserMessage;

@Service
public class UserMessageService {

	static Map<String , UserMessage> mpUserMessage = new TreeMap<>();
	
	private static int cnt = 4;
	
	static {
		mpUserMessage.put("msg1" , new UserMessage("msg1" , (long) 10 , "This is firstMessage" , true , "usr1"));
		mpUserMessage.put("msg2" , new UserMessage("msg2" , (long) 11 , "This is secondMessage" , true , "usr2"));
		mpUserMessage.put("msg3" , new UserMessage("msg3" , (long) 12 , "This is thirdMessage" , true , "usr3"));
		mpUserMessage.put("msg4" , new UserMessage("msg4" , (long) 13 , "This is fifthMessage" , true , "usr1"));
	}
	
	
	
	public UserMessage Save(UserMessage msg) {
		
		if(msg.getMessageID()==null) {
			msg.setMessageID("msg"+ ++cnt);
		}
		mpUserMessage.put(msg.getMessageID(), msg);
		
		return mpUserMessage.get(msg.getMessageID());
		
	}
	
	public List<UserMessage> getChatLogsbyUserID(String userID , int limit , String msgKey){
		
		List<UserMessage> userMsgs;
		List<UserMessage> userMsgsnew = new ArrayList<>();
		
		userMsgs = new ArrayList<>(mpUserMessage.values());
		
		/*
		 
		if(msgKey=="") {
			
		}else {
			userMsgs.stream().filter(a -> a.getUserID().equals(userID) ).forEach(a->userMsgsnew.add(a));
		}
		 */
		
		userMsgs.stream().filter(a -> a.getUserID().equals(userID)).forEach(a->userMsgsnew.add(a));
		
		userMsgsnew.sort((a , b)-> b.getTimeStamp().compareTo(a.getTimeStamp()));
		
		
		userMsgs.clear();
		for(int i = 0 ; i < Math.min(userMsgsnew.size(), limit) ; i++) {
			userMsgs.add(userMsgsnew.get(i));
		}
		
		return userMsgs;
		
	}
	
	public String deletebyUserID(String userid) {
		String id = "";
		Set<String> set = new HashSet<>();
		for(Map.Entry<String, UserMessage> entry : mpUserMessage.entrySet()) {
			if(entry.getValue().getUserID().equals(userid)) {
				set.add(entry.getKey());
			}
		}
		if(set.size()>0) {
			mpUserMessage.keySet().removeAll(set);
			id = userid;
		}
		return id;
		
	}
	
	
	public UserMessage deletebyMsgID(String userid , String msgid) {
		
		UserMessage removedMsg = mpUserMessage.remove(msgid);
		
		return removedMsg;
	}
	
    public List<UserMessage>  findAll() {
		
		List<UserMessage> userMsgs = new ArrayList<>(mpUserMessage.values());
		
		return userMsgs;
	}
	
	
}
