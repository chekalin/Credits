package com.example.credits.server.persistence;

import org.apache.commons.lang.time.DateUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

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
}
