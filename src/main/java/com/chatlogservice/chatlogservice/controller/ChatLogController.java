package com.chatlogservice.chatlogservice.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.chatlogservice.chatlogservice.exception.MessageNotFound;
import com.chatlogservice.chatlogservice.exception.UserNotFoundException;
import com.chatlogservice.chatlogservice.model.UserMessage;
import com.chatlogservice.chatlogservice.service.UserMessageService;

@RestController
public class ChatLogController {

	
	@Autowired
	private UserMessageService usrMsgService;
	
	@PostMapping(value = "chatlogs/{userid}")
	public ResponseEntity<Object> createMsg(@RequestBody UserMessage msg ,  @PathVariable String userid) {
		msg.setUserID(userid);
		UserMessage msgNew = usrMsgService.Save(msg);
		
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{msgID}")
		.buildAndExpand(msgNew.getMessageID()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	@GetMapping(value = "chatlogs/{userid}")
	public List<UserMessage> getMessagebyUserId(@PathVariable String userid ,
			@RequestParam(value = "limit" , defaultValue = "10") int limit ,
			@RequestParam(value = "strtmsgKey" , defaultValue = "") String strtmsgKey){
		
		List<UserMessage> msgs = usrMsgService.getChatLogsbyUserID(userid , limit , strtmsgKey);
		
		if(msgs.isEmpty()) {
			throw new UserNotFoundException("userid-" + userid +" not found");
		}
		
		return msgs;
	}
	
	
	@DeleteMapping(value = "chatlogs/{userid}")
	public void deleteByUerId(@PathVariable String userid) {
		String id = usrMsgService.deletebyUserID(userid);
		
		if(id=="") {
			throw new UserNotFoundException("userid-" + userid +" not found");
		}
	}
	
	@DeleteMapping(value = "chatlogs/{userid}/{msgid}")
	public ResponseEntity<Object> deleteByMsgID(@PathVariable String userid , @PathVariable String msgid) {
		
		UserMessage removedMsg = usrMsgService.deletebyMsgID(userid , msgid);
		
		if(removedMsg == null) {
			throw new MessageNotFound("msgid - "+ msgid + " not found");
		}
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/")
				.buildAndExpand(removedMsg.getMessageID()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	

	@GetMapping(value = "chatlogs")
	public List<UserMessage> getAllMessages(){
		List<UserMessage> msgs = usrMsgService.findAll();
		return msgs;
	}
		
		
	
	
	
}
