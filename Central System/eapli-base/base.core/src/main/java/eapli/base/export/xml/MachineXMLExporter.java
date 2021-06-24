package eapli.base.export.xml;

import eapli.base.factoryfloormanagementarea.domain.ConfigurationFile;
import eapli.base.factoryfloormanagementarea.domain.Machine;

import java.io.PrintWriter;

public class MachineXMLExporter {
    public void export(final Iterable<Machine> machines, final PrintWriter stream) {
        stream.println("<Machines>");
        for(Machine m: machines) {
            stream.printf("<Machine InternalCode=\"%s\" State=\"%s\">%n", m.identity(), m.state().machineState());
            stream.printf("<SerialNumber>%s</SerialNumber>%n",m.serialNumber().toString());
            stream.printf("<Description>%s</Description>%n",m.description());
            stream.printf("<InstallationDate>%s</InstallationDate>%n",m.installationDate().dateOfInstallation());
            stream.printf("<Brand>%s</Brand>%n",m.brand());
            stream.printf("<Model>%s</Model>%n",m.model().model());
            stream.printf("<Protocol ID=\"%s\"/>%n",m.protocol().getInt());
            if(m.followedByMachine() != null) {
                stream.printf("<Machine InternalCode=\"%s\"/>%n", m.followedByMachine().identity());
            }
            stream.printf("<ConfigurationFiles>%n");
            for(ConfigurationFile cf : m.configList()){
                stream.printf("<ConfigurationFile File=\"%s\">%s</ConfigurationFile>%n", cf.toString(),cf.getDescription());
            }
            stream.printf("</ConfigurationFiles>%n");
            stream.printf("</Machine>%n");
        }
        stream.println("</Machines>");
    }
}
