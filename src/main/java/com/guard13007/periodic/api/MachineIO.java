package com.guard13007.periodic.api;

public enum MachineIO {
    ACCEPT_ALL(true, true), INPUT(true, false), OUTPUT(false, true), NULL(false, false);

    public static MachineIO[] VALID_IO_SETTINGS = { ACCEPT_ALL, INPUT, OUTPUT, NULL };

    private final boolean in;
    private final boolean out;

    MachineIO(boolean in, boolean out) {
        this.in = in;
        this.out = out;
    }

    public boolean acceptsInput() {
        return this.in;
    }

    public boolean outputs() {
        return this.out;
    }
}
