/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.factoryfloormanagementarea.domain.InternalCode;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.messagesmanagement.domain.ConsumptionMessage;
import eapli.base.messagesmanagement.domain.Message;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.domain.model.general.Description;
import eapli.framework.domain.model.general.Designation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Utilizador
 */
public class ConsumptionMessageFactory implements MessageFactory {

    @Override
    public Message createMessage(List<String> attributes) {
        final MachineRepository repositoryMachine = PersistenceContext.repositories().machine();
        final ProductRepository productRepository = PersistenceContext.repositories().products();
        final DepositRepository depositRepository = PersistenceContext.repositories().deposit();
        final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterial();

        final Machine machine = repositoryMachine.ofIdentity(new InternalCode(attributes.get(0).trim())).orElseThrow(() -> new IllegalStateException("Machine doesn't exist"));
        final Description type = Description.valueOf(attributes.get(1).trim());
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        final LocalDateTime dateHour = LocalDateTime.parse(attributes.get(2).trim(), formatter);
        final Integer quantity = Integer.parseInt(attributes.get(4).trim());
        Deposit deposit = null;
        if (attributes.size()== 6) {
            deposit = depositRepository.ofIdentity(Designation.valueOf(attributes.get(5).trim())).orElseThrow(() -> new IllegalStateException("Deposit doesn't exist"));
        }
        if (rawMaterialRepository.ofIdentity(Designation.valueOf(attributes.get(3).trim())).isPresent()) {
            final RawMaterial rawMaterial = rawMaterialRepository.ofIdentity(Designation.valueOf(attributes.get(3).trim())).get();
            return new ConsumptionMessage(machine, type, dateHour, rawMaterial, quantity, deposit);
        } else {
            final Product product = productRepository.ofIdentity(Designation.valueOf(attributes.get(3).trim())).orElseThrow(() -> new IllegalStateException("Product or raw material don't exist"));
            return new ConsumptionMessage(machine, type, dateHour, product, quantity, deposit);
        }
    }

}
