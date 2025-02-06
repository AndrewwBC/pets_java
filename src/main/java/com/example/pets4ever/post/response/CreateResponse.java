package com.example.pets4ever.post.response;

import java.time.LocalDateTime;

public record CreateResponse(LocalDateTime createAt, String fileName) {

}
