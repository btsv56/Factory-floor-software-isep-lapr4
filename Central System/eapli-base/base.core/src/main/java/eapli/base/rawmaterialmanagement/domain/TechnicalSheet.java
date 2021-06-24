/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.rawmaterialmanagement.domain;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.StringPredicates;

/**
 *
 * @author Bruno Veiga
 */
@Embeddable
public class TechnicalSheet implements ValueObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String nameTechnicalSheet;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] file;

    protected TechnicalSheet() {
        //ORM
    }

    /**
     * Technical Sheet constructor
     *
     * @param nameTechnicalSheet
     * @param pathTechnicalSheet
     */
    public TechnicalSheet(final String nameTechnicalSheet, final String pathTechnicalSheet) {
        this.setNameTechnicalSheet(nameTechnicalSheet);
        this.setFile(pathTechnicalSheet);
    }

    /**
     * ub Sets and validates nameTechnicalSheet
     *
     * @param nameTechnicalSheet
     */
    private void setNameTechnicalSheet(final String nameTechnicalSheet) {
        if (nameMeetsMinimumRequirements(nameTechnicalSheet)) {
            this.nameTechnicalSheet = nameTechnicalSheet;
        } else {
            throw new IllegalArgumentException("Invalid name");
        }
    }

    /**
     * Sets and validates pathTechnicalSheet
     *
     * @param pathTechnicalSheet
     */
    private void setFile(final String pathTechnicalSheet) {
        if(!pathMeetsMinimumRequirements(pathTechnicalSheet)) throw new IllegalArgumentException("Path can't be null or empty");
        try {
            File tempFile = new File(pathTechnicalSheet);
            FileInputStream fis = new FileInputStream(tempFile);
            this.file = new byte[(int) tempFile.length()];
            fis.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            file = bos.toByteArray();
            fis.close();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Invalid file path");
        }
    }

    /**
     * Ensure name is not null or empty.
     *
     * @param name
     * @return True if name meets minimum requirements. False if description
     * does not meet minimum requirements.
     */
    private static boolean nameMeetsMinimumRequirements(final String name) {
        return !StringPredicates.isNullOrEmpty(name);
    }

    /**
     * Ensure path is not null or empty.
     *
     * @param path
     * @return True if path meets minimum requirements. False if description
     * does not meet minimum requirements.
     */
    private static boolean pathMeetsMinimumRequirements(final String path) {
        return !StringPredicates.isNullOrEmpty(path);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechnicalSheet)) {
            return false;
        }

        final TechnicalSheet that = (TechnicalSheet) o;
        return this.nameTechnicalSheet.equals(that.nameTechnicalSheet) && this.file.equals(that.file);
    }

    @Override
    public int hashCode() {
        return new HashCoder().with(nameTechnicalSheet).with(file).code();
    }

    @Override
    public String toString() {
        return this.nameTechnicalSheet;
    }

}
