package com.mng.Mng.dto;

import com.mng.Mng.model.Adress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDto {
    private String id;
    private String officeName;
    private LocationDto location;
    private Adress adress;
}
