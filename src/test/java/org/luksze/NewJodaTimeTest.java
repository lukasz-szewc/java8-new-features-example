package org.luksze;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JUNE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class NewJodaTimeTest {

    @Test
    public void canCreateLocalDate() throws Exception {
        LocalDate date = constructDate();
        assertThat(date.isLeapYear(), is(TRUE));
        assertThat(date.getMonth(), is(JUNE));
        assertThat(date.isAfter(of(2016, JUNE, 22)), is(TRUE));
    }

    @Test
    public void equalityOfLocalDate() throws Exception {
        LocalDate firstDate = constructDate();
        LocalDate secondDate = constructDate();
        assertThat(firstDate, equalTo(secondDate));
        assertThat(firstDate.compareTo(secondDate), is(0));
    }

    @Test
    public void canCreateLocalTime() throws Exception {
        LocalTime time = LocalTime.of(21, 33, 44, 2);
        assertThat(time.getHour(), is(21));
        assertThat(time.getMinute(), is(33));
        assertThat(time.getSecond(), is(44));
        assertThat(time.getNano(), is(2));
    }

    @Test
    public void canCreateLocalDateTime() throws Exception {
        LocalDateTime localDateTime = constructDate().atTime(21, 33);
        assertThat(localDateTime.getMonth(), is(JUNE));
        assertThat(localDateTime.getHour(), is(21));
    }

    @Test
    public void canCreateNewLocalDateByAddingYears() throws Exception {
        LocalDate localDate = of(2004, FEBRUARY, 29);
        LocalDate nextYear = localDate.plus(1, ChronoUnit.YEARS);
        assertThat(nextYear, equalTo(of(2005, FEBRUARY, 28)));
    }

    @Test
    public void canCreatePeriod() throws Exception {
        Period period = Period.between(of(2001, 1, 1), of(2002, 5, 20));
        assertThat(period.getYears(), is(1));
        assertThat(period.getMonths(), is(4));
        assertThat(period.getDays(), is(19));
        assertThat(period.toTotalMonths(), is(16L));
    }

    @Test
    public void canCreateDuration() throws Exception {
        long seconds = 90;
        LocalDateTime referenceDateTime = now().atStartOfDay();
        Duration duration = Duration.between(referenceDateTime, referenceDateTime.plusSeconds(seconds));
        assertThat(duration.getSeconds(), is(seconds));
    }

    @Test
    public void canUseTimeZones() throws Exception {
        ZonedDateTime londonTime = ZonedDateTime.now(ZoneId.of("Europe/London"));
        ZonedDateTime warsawTime = londonTime.withZoneSameInstant(ZoneId.of("Europe/Warsaw"));

        assertThat(londonTime.isBefore(warsawTime), is(FALSE));
        assertThat(londonTime.isAfter(warsawTime), is(FALSE));
        assertThat(warsawTime.minusHours(1).toLocalDateTime(), equalTo(londonTime.toLocalDateTime()));
        assertThat(warsawTime.minusHours(1), not(equalTo(londonTime)));
    }

    @Test
    public void zoneIdDealsWithDaylightSavingTime() throws Exception {
        ZonedDateTime beforeTimeChange = ZonedDateTime.of(2017, 3, 26, 1, 30, 0, 0, ZoneId.of("Europe/Warsaw"));
        ZonedDateTime afterTimeChange =  ZonedDateTime.of(2017, 3, 26, 2, 30, 0, 0, ZoneId.of("Europe/Warsaw"));

        assertThat(beforeTimeChange.plusHours(1), equalTo(afterTimeChange));
        assertThat(beforeTimeChange, equalTo(afterTimeChange.minusHours(1)));
        assertThat(beforeTimeChange.toLocalDateTime(), equalTo(afterTimeChange.toLocalDateTime().minusHours(2)));
        assertThat(beforeTimeChange.getOffset(), equalTo(ZoneOffset.ofHours(1)));
        assertThat(afterTimeChange.getOffset(), equalTo(ZoneOffset.ofHours(2)));
    }

    private LocalDate constructDate() {
        return of(2016, JUNE, 23);
    }
}
