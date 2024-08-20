package org.plscraper.enums;

public enum Location {
    HOME('H'),
    AWAY('A');

    private char abbr;

    Location(char abbr) {
        this.abbr = abbr;
    }
}
