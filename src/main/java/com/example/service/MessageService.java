package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    AccountRepository accountRepository;
    

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id){
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    public Object deleteMessageById(Integer id){
        if(messageRepository.findById(id).isPresent()){
            messageRepository.deleteById(id);
            return 1;
        } else {
            return null;
        }
    }

    public List<Message> getAllMessagesFromUser(Integer account_id){
        List<Message> postedByUser = new ArrayList<>();
        for (Message message : getAllMessages()) {
            if(message.getPosted_by().equals(account_id)){
                postedByUser.add(message);
            }
        }
        return postedByUser;
    }

    public Object updateMessageById(Integer message_id, Message message) {
        if(message.getMessage_text().length()>254
        || message.getMessage_text().isBlank()
        || messageRepository.existsById(message_id)==false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            message.setMessage_id(message_id);
            messageRepository.save(message);
            return 1;
        }
    }

    public Object createMessage(Message message){
        if(message.getMessage_text().length()>254
        || message.getMessage_text().isBlank()
        || accountRepository.existsById(message.getPosted_by()) == false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            messageRepository.save(message);
            return messageRepository.getById(message.getMessage_id());
        }
    }
}
