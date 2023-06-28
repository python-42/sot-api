package com.sotindex.api;

public record Island (String name, String imagePath, String coords) {
    @Override
    public String toString() {
        return name + " at " + coords;
    }
};
