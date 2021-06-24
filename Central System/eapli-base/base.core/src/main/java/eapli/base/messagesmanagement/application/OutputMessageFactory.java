/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;

import eapli.base.factoryfloormanagementarea.domain.InternalCode;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.messagesmanagement.domain.Message;
import eapli.base.messagesmanagement.domain.OutputMessage;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.domain.model.general.Description;
import eapli.framework.domain.model.general.Designation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Utilizador
 */
public class OutputMessageFactory implements MessageFactory {

    @Override
    public Message createMessage(List<String> attributes) {
        final MachineRepository repositoryMachine = PersistenceContext.repositories().machine();
        final ProductRepository productRepository = PersistenceContext.repositories().products();
        final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrder();

        final Machine machine = repositoryMachine.ofIdentity(new InternalCode(attributes.get(0).trim())).orElseThrow(() -> new IllegalStateException("Machine doesn't exist"));
        final Description type = Description.valueOf(attributes.get(1).trim());
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        final LocalDateTime dateHour = LocalDateTime.parse(attributes.get(2).trim(), formatter);
        final Product product = productRepository.ofIdentity(Designation.valueOf(attributes.get(3).trim())).orElseThrow(() -> new IllegalStateException("Product doesn't exist"));
        final Integer quantity = Integer.parseInt(attributes.get(4).trim());
        ProductionOrder productionOrder = null;
        if (attributes.size() ==6) {
            productionOrder = productionOrderRepository.getProductionOrderByLot(Designation.valueOf(attributes.get(5).trim()));
        }
        return new OutputMessage(machine, type, dateHour, product, quantity, productionOrder);
    }
}
