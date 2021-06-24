/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;
import eapli.base.messagesmanagement.domain.Message;
import java.util.*;

/**
 *
 * @author bruno
 */
public class MessageComparator implements Comparator<Message> {

    @Override
    public int compare(Message o1, Message o2) {
        return o1.dateHour().compareTo(o2.dateHour());
    }
    
}
