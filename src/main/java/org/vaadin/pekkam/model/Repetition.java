package org.vaadin.pekkam.model;

public enum Repetition {
    REPEAT("repeat"),
    REPEAT_X("repeat-x"),
    REPEAT_Y("repeat-y"),
    NO_REPEAT("no-repeat");

    private final String value;

    Repetition(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
