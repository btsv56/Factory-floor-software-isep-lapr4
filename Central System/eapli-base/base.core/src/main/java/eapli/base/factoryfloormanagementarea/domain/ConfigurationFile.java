/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.domain;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;

import eapli.base.utils.Files;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.domain.model.general.Description;

/**
 *
 * @author bruno
 */
@Entity
public class ConfigurationFile implements ValueObject {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Version
    private Long version;
    
    @Id
    @GeneratedValue()
    private long id;
    
    private final Description description;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private final byte[] file;

    protected ConfigurationFile() {
        description = null;
        file = null;
    }

    public ConfigurationFile(final Description description, final File file) throws IOException {
        this.description = description;
        this.file = Files.toByteArray(file);
    }
    
    

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfigurationFile other = (ConfigurationFile) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Arrays.equals(this.file, other.file)) {
            return false;
        }
        return true;
    }
    
    
    
    public byte[] getFile() {
        return file;
    }    

    public Description getDescription() {
        return description;
    }
    
}
