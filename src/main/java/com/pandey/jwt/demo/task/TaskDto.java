package com.pandey.jwt.demo.task;

import com.pandey.jwt.demo.dto.UserDto;

public record TaskDto(Long id , String title, String description , UserDto userDto) {
}
