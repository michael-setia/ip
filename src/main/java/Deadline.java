package main.java;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDate date;

    // Constructor for Deadline
    public Deadline(String description, String date) {
        super(description);
        this.date = LocalDate.parse(date);
    }

    // Return string representation of Deadline
    @Override
    public String toString() {
        String dateString = date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return " [D]" + super.toString() + " (by: " + dateString + ")";
    }
}
