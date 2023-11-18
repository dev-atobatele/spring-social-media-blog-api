package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id){
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        return null;
    }

    public Object deleteMessageById(Integer id){
        if(messageRepository.findById(id).isPresent()){
            messageRepository.deleteById(id);
            return 1;
        }
        return null;
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

    public Object updateMessageById(Integer message_id, String message_text){
        Optional<Message> opMessage = messageRepository.findById(message_id);
        if(opMessage.isPresent()){
            opMessage.get().setMessage_text(message_text);
            messageRepository.save(opMessage.get());
            return 1;
        }
        return null;
    }

    public Object createMessage(Message message){
        messageRepository.save(message);
        return 1;
    }
}
