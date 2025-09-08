package com.example.connectionlogger;

/**
 * Simple model representing a log message with a timestamp.
 */
public class LogItem {
    public final long time;
    public final String message;

    public LogItem(long time, String message) {
        this.time = time;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogItem)) return false;
        LogItem other = (LogItem) o;
        return time == other.time && message.equals(other.message);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(time);
        result = 31 * result + message.hashCode();
        return result;
    }
}
