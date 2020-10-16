package de.uni_osnabrueck.ikw.eegdroid.utilities;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.LocalTime;
import java.time.ZonedDateTime;

public class MyXAxisValueFormatterTime extends ValueFormatter {

    private final ZonedDateTime time;

    public MyXAxisValueFormatterTime(ZonedDateTime creationTime) {
        time = creationTime;
    }

    @Override
    public String getFormattedValue(float value) {
        // Value represents the position of the label on the axis (x or y)
        LocalTime localTime = time.toLocalTime().plusSeconds(Math.round(value / 1000));
        return localTime.toString();
    }

}