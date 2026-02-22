package com.noleggiomezzi.utility;

import java.time.LocalDateTime;

public class DateRange {
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;

    public DateRange(LocalDateTime dataInizio, LocalDateTime dataFine) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public boolean sovrappone(DateRange altro) {
        // (Inizio1 < Fine2) AND (Inizio2 < Fine1)
        return this.dataInizio.isBefore(altro.getDataFine()) && 
            altro.getDataInizio().isBefore(this.dataFine);
    }

    public LocalDateTime getDataInizio() { return dataInizio; }
    public LocalDateTime getDataFine() { return dataFine; }
}