package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
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
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Bean
    public AccountService accountService(){
        return new AccountService();
    }
    @Bean
    public MessageService messageService(){
        return new MessageService();
    }

    @Bean
    public AccountRepository accountRepository(){
        return new AccountRepository() {
        };
    }

    @Bean public MessageRepository messageRepository(){
        return new MessageRepository() {
        };
    }

    @Autowired
    ApplicationContext applicationContext;

    public AccountService getAccountService(){
        return applicationContext.getBean(AccountService.class);
    }
    public MessageService getMessageService(){
        return applicationContext.getBean(MessageService.class);
    }
}
