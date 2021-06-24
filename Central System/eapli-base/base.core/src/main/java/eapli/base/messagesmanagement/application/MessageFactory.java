/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;

import eapli.base.messagesmanagement.domain.Message;
import java.util.List;

/**
 *
 * @author Utilizador
 */
public interface MessageFactory {
    Message createMessage(List<String> attributes);
}
