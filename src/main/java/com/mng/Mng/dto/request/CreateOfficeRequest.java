package com.mng.Mng.dto.request;

import com.mng.Mng.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOfficeRequest {

    private String officeName;
    private String officePhone;
    private String officePhoto;
    private String city;
    private String district;
    private String street;
    private String number;
    private LocationDto location;
}
