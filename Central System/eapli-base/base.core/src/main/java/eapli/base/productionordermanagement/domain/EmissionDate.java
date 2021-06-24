package eapli.base.productionordermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.Calendars;

import javax.persistence.Embeddable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Embeddable
public class EmissionDate  implements ValueObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Calendar emDate;

    protected EmissionDate() {
        this.emDate = Calendars.now();
    }

    public EmissionDate(Calendar date) {
        if (date.compareTo(Calendars.now()) > 0) {
            throw new IllegalArgumentException("Date cannot be from future.");
        } else {
            this.emDate = date;
        }
    }

    public Calendar getDate() {
        return this.emDate;
    }

    @Override
    public String toString() {
        Date date =emDate.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }
}
