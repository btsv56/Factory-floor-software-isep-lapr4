/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.Calendars;

/**
 *
 * @author bruno
 */
@Embeddable
public class InstallationDate implements ValueObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Calendar dateOfInstallation;

    protected InstallationDate() {
        this.dateOfInstallation = null;
    }

    public InstallationDate(Calendar dateOfInstallation) {
        if (dateOfInstallation.compareTo(Calendars.now()) > 0) {
            throw new IllegalArgumentException("Date cannot be from future.");
        } else {
            this.dateOfInstallation = dateOfInstallation;
        }
    }

    public String dateOfInstallation(){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(dateOfInstallation.getTime());
    }
}
