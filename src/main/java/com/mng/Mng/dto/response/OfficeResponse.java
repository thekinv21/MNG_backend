package com.mng.Mng.dto.response;

import com.mng.Mng.dto.OfficeDto;
import com.mng.Mng.dto.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class OfficeResponse {
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private List<OfficeDto> content;
}
