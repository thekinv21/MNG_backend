package com.mng.Mng.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionDto {
    private String id;
    private String photographName;
    private String imageUrl;
    private LocalDateTime createdDate;
    private boolean isActive;
    private List<QuestionDto> questions;
}
