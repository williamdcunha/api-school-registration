package com.alunoonline.api.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchGradesRequestDto {
    private Double nota1;
    private Double nota2;
}
