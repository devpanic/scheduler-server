package com.devpanic.scheduler.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CustomLocalDateTimeSerializer() {
        this(null);
    }

    public CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            LocalDateTime roundedDateTime = roundToNearestHalfHour(value);
            gen.writeString(roundedDateTime.format(formatter));
        }
    }

    private LocalDateTime roundToNearestHalfHour(LocalDateTime dateTime) {
        int minutes = dateTime.getMinute();
        int roundedMinutes = (minutes / 30) * 30;
        return dateTime.withMinute(roundedMinutes).withSecond(0).withNano(0);
    }
}