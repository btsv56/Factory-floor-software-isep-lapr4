package eapli.base.errornotificationmanagement.application;

import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.base.errornotificationmanagement.repository.ErrorNotificationRepository;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class ConsultArchivedProcessedErrorNotificationsController {

    private ErrorNotificationRepository repository = PersistenceContext.repositories().errornotification();
    private MachineRepository machineRepository = PersistenceContext.repositories().machine();

    public Iterable<Machine> findMachines(){
        return machineRepository.findAll();
    }

    public Iterable<ErrorNotification> listErrorNotificationsByMachine(Machine machine){
        return repository.findByMachine(machine);
    }

    public Iterable<ErrorNotification> listErrorNotificationsByTime(){
        return repository.findByTime();
    }

}
