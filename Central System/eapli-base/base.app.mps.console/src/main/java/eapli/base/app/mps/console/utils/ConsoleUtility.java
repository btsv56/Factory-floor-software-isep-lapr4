/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.mps.console.utils;

import static eapli.framework.util.Console.readLine;

import eapli.base.utils.Time;
import eapli.framework.util.Utility;

/**
 *
 * @author bruno
 */
@Utility
public class ConsoleUtility {

    private ConsoleUtility() {
    }

    public static Time readHourOfDay(String output) {
        while (true) {
            final String strDate = readLine(output);
            if (strDate.isEmpty()) {
                return null;
            }
            try {
                int hour = Integer.valueOf(strDate.split(":")[0]);
                int minutes = Integer.valueOf(strDate.split(":")[1]);
                return new Time(hour, minutes);
            } catch (NumberFormatException numberFormatException) {
            }
        }
    }

    public static int readMinutes(String output) {
        while (true) {

            final String strDate = readLine(output);
            if (strDate.isEmpty()) {
                return 0;
            }
            int minutes = 0;
            try {
                minutes = Integer.valueOf(strDate);
                return minutes;
            } catch (NumberFormatException numberFormatException) {
            }
        }
    }

}
