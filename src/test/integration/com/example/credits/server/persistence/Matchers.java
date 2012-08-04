package com.example.credits.server.persistence;

import org.apache.commons.lang.time.DateUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class Matchers {
    public static Matcher<Date> sameDay(Date date) {
        return new SameDayMatcher(date);
    }

    private static class SameDayMatcher extends BaseMatcher<Date> {
        private Date date;

        public SameDayMatcher(Date date) {
            this.date = date;
        }

        @Override
        public boolean matches(Object item) {
            return DateUtils.isSameDay(date, (Date) item);
        }

        @Override
        public void describeTo(Description description) {
            description.appendValue(date);
        }
    }

    public static Matcher<BigDecimal> closeTo(BigDecimal bigDecimal) {
        return new BigDecimalCloseToMatcher(bigDecimal);
    }

    private static class BigDecimalCloseToMatcher extends BaseMatcher<BigDecimal> {
        private BigDecimal expected;

        public BigDecimalCloseToMatcher(BigDecimal bigDecimal) {

            this.expected = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        }

        @Override
        public boolean matches(Object item) {
            BigDecimal actual = (BigDecimal) item;
            actual = actual.setScale(2, BigDecimal.ROUND_HALF_UP);
            return actual.compareTo(expected) == 0;
        }

        @Override
        public void describeTo(Description description) {
            description.appendValue(expected);
        }
    }
}
