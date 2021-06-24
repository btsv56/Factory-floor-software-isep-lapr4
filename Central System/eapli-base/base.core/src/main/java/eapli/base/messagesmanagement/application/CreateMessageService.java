/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.messagesmanagement.domain.Message;
import eapli.base.messagesmanagement.repositories.MessageRepository;
import java.util.List;

/**
 *
 * @author Utilizador
 */
public class CreateMessageService {

    public void createMessage(String format, List<String> attributes) {
        final MessageRepository repository = PersistenceContext.repositories().messages();
        final MessageFactory messageFactory = MessageContext.messageFactory(format);
        Message message = messageFactory.createMessage(attributes);
        repository.save(message);
    }
}
