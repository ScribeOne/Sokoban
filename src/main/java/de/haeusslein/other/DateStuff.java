package de.haeusslein.other;

import java.time.LocalDate;

public class DateStuff {
    public void dateOutput() {
        LocalDate now = LocalDate.now();
        LocalDate fourWeeksEarlier = LocalDate.now().minusWeeks(4);
        LocalDate oneMonthEarlier = LocalDate.now().minusMonths(1);

        System.out.printf("Now: %s Weekday: %s %n", now, now.getDayOfWeek());
        System.out.printf("4 Weeks earlier: %s Weekday: %s %n", fourWeeksEarlier, fourWeeksEarlier.getDayOfWeek());
        System.out.printf("1 Month earlier: %s Weekday: %s %n", oneMonthEarlier, oneMonthEarlier.getDayOfWeek());
    }
}
