package com.example.pets4ever.user.responses;

public record FieldMessageResponse(String field, String value, String message)
{
    public static FieldMessageResponse fromData(String field, String value, String message) {
        return new FieldMessageResponse(field, value, message);
    }
}
