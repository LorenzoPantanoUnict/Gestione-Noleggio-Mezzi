package com.noleggiomezzi.model;

import java.time.LocalDateTime;

public class DateRange {
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;

    public DateRange(LocalDateTime dataInizio, LocalDateTime dataFine) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public LocalDateTime getDataInizio() { return dataInizio; }
    public LocalDateTime getDataFine() { return dataFine; }
}