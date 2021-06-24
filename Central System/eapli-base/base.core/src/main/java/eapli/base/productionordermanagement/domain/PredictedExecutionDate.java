package eapli.base.productionordermanagement.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.Calendars;

@Embeddable
public class PredictedExecutionDate implements ValueObject {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Calendar execDate;

    protected PredictedExecutionDate() {
        this.execDate = null;
    }

    public PredictedExecutionDate(Calendar date) {
        if (date.compareTo(Calendars.now()) < 0) {
            throw new IllegalArgumentException("Date cannot be from the past.");
        } else {
            this.execDate = date;
        }
    }

    public Calendar getDate() {
        return this.execDate;
    }

    @Override
    public String toString() {
        Date date =execDate.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }
}
