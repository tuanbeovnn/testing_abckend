package com.myblogbackend.blog.enums;

public enum FeelingDescription {
    LIKE("like"),
    UNLIKE("unlike");

    private final String value;

    FeelingDescription(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}