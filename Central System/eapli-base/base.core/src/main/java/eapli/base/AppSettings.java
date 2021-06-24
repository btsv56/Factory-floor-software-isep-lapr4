package eapli.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the application settings.
 *
 * @author Paulo Gandra Sousa
 */
public class AppSettings {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppSettings.class);

    private static final String PROPERTIES_RESOURCE = "application.properties";
    private static final String REPOSITORY_FACTORY_KEY = "persistence.repositoryFactory";
    private static final String UI_MENU_LAYOUT_KEY = "ui.menu.layout";
    private static final String PERSISTENCE_UNIT_KEY = "persistence.persistenceUnit";
    private static final String SCHEMA_GENERATION_KEY = "javax.persistence.schema-generation.database.action";
    private static final String HIGH_CALORIES_DISH_LIMIT = "HighCaloriesDishLimit";
    private static final String MESSAGE_DIRECTORY_ENTRY="messages.directory.entry";
    private static final String MESSAGE_DIRECTORY_OUT="messages.directory.out";
    //Importer for messages
    private static final String CSVIMPORTER="csv";
    //Message Factory
    private static final String START_OF_ACTIVITY_MESSAGE="S0";
    private static final String END_OF_ACTIVITY_MESSAGE="S9";
    private static final String RESUMPTION_OF_ACTIVITY_MESSAGE="S1";
    private static final String FORCED_STOP_MESSAGE="S8";
    private static final String CONSUMPTION_MESSAGE="C0";
    private static final String OUTPUT_DELIVERY_MESSAGE="C9";
    private static final String OUTPUT_MESSAGE="P1";
    private static final String REVERSAL_MESSAGE="P2";
    
    private static final String XML_EXPORTER="xml";
    

    private final Properties applicationProperties = new Properties();

    public AppSettings() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream propertiesStream = this.getClass().getClassLoader()
                .getResourceAsStream(PROPERTIES_RESOURCE)) {
            if (propertiesStream != null) {
                this.applicationProperties.load(propertiesStream);
            } else {
                throw new FileNotFoundException(
                        "property file '" + PROPERTIES_RESOURCE + "' not found in the classpath");
            }
        } catch (final IOException exio) {
            setDefaultProperties();

            LOGGER.warn("Loading default properties", exio);
        }
    }

    private void setDefaultProperties() {
        this.applicationProperties.setProperty(REPOSITORY_FACTORY_KEY,
                "eapli.base.persistence.jpa.JpaRepositoryFactory");
        this.applicationProperties.setProperty(UI_MENU_LAYOUT_KEY, "horizontal");
        this.applicationProperties.setProperty(PERSISTENCE_UNIT_KEY, "eapli"
                + ".base");
        this.applicationProperties.setProperty(HIGH_CALORIES_DISH_LIMIT, "300");
        this.applicationProperties.setProperty(MESSAGE_DIRECTORY_ENTRY, ".\\MessageEntry\\");
        this.applicationProperties.setProperty(MESSAGE_DIRECTORY_OUT, ".\\MessageOut\\");
        this.applicationProperties.setProperty(CSVIMPORTER, "eapli.base.messagesmanagement.application.CSVImporter");
        //Message Factories
        this.applicationProperties.setProperty(START_OF_ACTIVITY_MESSAGE, "eapli.base.messagesmanagement.application.StartOfActivityMessageFactory");
        this.applicationProperties.setProperty(END_OF_ACTIVITY_MESSAGE, "eapli.base.messagesmanagement.application.EndOfActivityMessageFactory");
        this.applicationProperties.setProperty(RESUMPTION_OF_ACTIVITY_MESSAGE, "eapli.base.messagesmanagement.application.ResumptionOfActivityMessageFactory");
        this.applicationProperties.setProperty(FORCED_STOP_MESSAGE, "eapli.base.messagesmanagement.application.ForcedStopMessageFactory");
        this.applicationProperties.setProperty(CONSUMPTION_MESSAGE, "eapli.base.messagesmanagement.application.ConsumptionMessageFactory");
        this.applicationProperties.setProperty(OUTPUT_DELIVERY_MESSAGE, "eapli.base.messagesmanagement.application.OutputDeliveryMessageFactory");
        this.applicationProperties.setProperty(OUTPUT_MESSAGE, "eapli.base.messagesmanagement.application.OutputMessageFactory");
        this.applicationProperties.setProperty(REVERSAL_MESSAGE, "eapli.base.messagesmanagement.application.ReversalMessageFactory");
        this.applicationProperties.setProperty(XML_EXPORTER, "eapli.base.export.xml.InformationExporterXML");
    }

    public Boolean isMenuLayoutHorizontal() {
        return "horizontal"
                .equalsIgnoreCase(this.applicationProperties.getProperty(UI_MENU_LAYOUT_KEY));
    }

    public String getPersistenceUnitName() {
        return this.applicationProperties.getProperty(PERSISTENCE_UNIT_KEY);
    }

    public String getRepositoryFactory() {
        return this.applicationProperties.getProperty(REPOSITORY_FACTORY_KEY);
    }

    public Integer getHighCaloriesDishLimit() {
        return Integer.valueOf(this.applicationProperties.getProperty(HIGH_CALORIES_DISH_LIMIT));
    }
    
    public String getMessageEntryFolder() {
        return this.applicationProperties.getProperty(MESSAGE_DIRECTORY_ENTRY);
    }
    
    public String getMessageOutFolder() {
        return this.applicationProperties.getProperty(MESSAGE_DIRECTORY_OUT);
    }
    
    public String getCSVFormat() {
        return this.applicationProperties.getProperty(CSVIMPORTER);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map getExtendedPersistenceProperties() {
        final Map ret = new HashMap();
        ret.put(SCHEMA_GENERATION_KEY,
                this.applicationProperties.getProperty(SCHEMA_GENERATION_KEY));
        return ret;
    }

    public String getProperty(String prop) {
        return this.applicationProperties.getProperty(prop);
    }
}
