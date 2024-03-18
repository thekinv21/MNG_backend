package com.mng.Mng.controller;

import com.mng.Mng.dto.OfficeDto;
import com.mng.Mng.dto.request.CreateOfficeRequest;
import com.mng.Mng.dto.response.OfficeResponse;
import com.mng.Mng.dto.response.UserResponse;
import com.mng.Mng.model.Office;
import com.mng.Mng.service.OfficeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/offices")
public class OfficeController {
    private final OfficeService officeService;
    private final ModelMapper modelMapper;

    public OfficeController(OfficeService officeService, ModelMapper modelMapper) {
        this.officeService = officeService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/allOffices")
    public ResponseEntity<OfficeResponse> getAllOffices(@RequestParam(value = "pageNo", defaultValue = "0", required = false ) int pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5" , required = false) int pageSize) {
        OfficeResponse officeResponse = officeService.getAllOffices(pageNo, pageSize);
        return ResponseEntity.ok(officeResponse);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<OfficeDto> getOfficeById(@PathVariable String id){
        Office office = officeService.getOfficeById(id);
        return ResponseEntity.ok(modelMapper.map(office,OfficeDto.class));
    }
    @GetMapping("/officeName/{officeName}")
    public ResponseEntity<OfficeDto> getOfficeByOfficeName(@PathVariable String officeName){
        Office office = officeService.getOfficeByOfficeName(officeName);
        return ResponseEntity.ok(modelMapper.map(office,OfficeDto.class));
    }
    @PostMapping("/create")
    public ResponseEntity<OfficeDto> createOffice(@RequestBody CreateOfficeRequest request){
        Office office = officeService.createOffice(request);
        return ResponseEntity.ok(modelMapper.map(office,OfficeDto.class));
    }
}
