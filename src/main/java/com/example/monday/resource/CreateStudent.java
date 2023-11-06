package com.example.monday.resource;

import com.example.monday.data.StudentUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateStudent(@NotBlank String name, @NotNull StudentUnit unit) {
}
