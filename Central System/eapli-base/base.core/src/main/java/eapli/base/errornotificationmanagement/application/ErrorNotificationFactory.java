/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.errornotificationmanagement.application;

import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.base.errornotificationmanagement.domain.ErrorNotificationType;
import eapli.base.factoryfloormanagementarea.domain.InternalCode;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.model.general.Description;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 *
 * @author Utilizador
 */
public class ErrorNotificationFactory {

    private MachineRepository repository = PersistenceContext.repositories().machine();

    public ErrorNotification createErrorNotification(Description description, ErrorNotificationType errorNotificationType, Description messageType) {
        Machine machine = repository.ofIdentity(new InternalCode(Arrays.asList(description.toString().split(";")).get(0))).orElseGet(null);
        return new ErrorNotification(machine,description, errorNotificationType, messageType, LocalDateTime.now());
    }
}
