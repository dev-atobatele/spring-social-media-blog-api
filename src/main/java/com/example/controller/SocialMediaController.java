package com.example.controller;

 import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;
    
    public SocialMediaController(MessageService messageService){
        this.messageService = messageService;
        this.accountService = accountService;
    }    
    @GetMapping("/messages/{message_id}")
    public Message getMessageById(@PathVariable Integer message_id){
        return messageService.getMessageById(message_id);
    }
    @GetMapping("/messages")
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }
    @DeleteMapping("/messages/{message_id}")
    public Object deleteMessageById(@PathVariable Integer message_id){
        return messageService.deleteMessageById(message_id);
    }
    @GetMapping("/accounts/{account_id}/messages")
    public List<Message> getAllMessagesFromUser(@PathVariable Integer account_id){
        return messageService.getAllMessagesFromUser(account_id);
    }
    @PatchMapping("/messages/{message_id}")
    public Object updateMessage(@PathVariable Integer message_id){
        return messageService.updateMessageById(message_id);
    }
    @PostMapping("/messages")
    public Object createMessage(){
        return messageService.createMessage();
    }
    @PostMapping("/register")
    public Object registerAccount(){
        return accountService.registerAccount();
    }
    @PostMapping("/login")
    public Object login(){
        return accountService.login();
    }
}
