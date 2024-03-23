package com.mng.Mng.service;

import com.mng.Mng.dto.OfficeDto;
import com.mng.Mng.dto.UserDto;
import com.mng.Mng.dto.request.CreateOfficeRequest;
import com.mng.Mng.dto.response.OfficeResponse;
import com.mng.Mng.exception.OfficeNotFoundException;
import com.mng.Mng.model.Office;
import com.mng.Mng.repository.OfficeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeService {
    private final OfficeRepository officeRepository;
    private final ModelMapper modelMapper;

    public OfficeService(OfficeRepository officeRepository, ModelMapper modelMapper) {
        this.officeRepository = officeRepository;
        this.modelMapper = modelMapper;
    }

    public OfficeResponse getAllOffices(int pageNo, int pageSize){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Office> offices = officeRepository.findAll(pageable);
        List<Office> officesList = offices.getContent();
        List<OfficeDto> content = officesList.stream().map(office -> modelMapper.map(office, OfficeDto.class)).toList();
        OfficeResponse officeResponse = new OfficeResponse();
        officeResponse.setContent(content);
        officeResponse.setPageNo(offices.getNumber());
        officeResponse.setPageSize(offices.getSize());
        officeResponse.setTotalElements(offices.getTotalElements());
        officeResponse.setTotalPages(offices.getTotalPages());
        officeResponse.setLast(offices.isLast());
        return officeResponse;
    }
    public Office getOfficeById(String id){
        return officeRepository.findOfficeById(id).orElseThrow(() -> new OfficeNotFoundException("Office not found with this id: " + id));
    }
    public Office getOfficeByOfficeName(String officeName){
        return officeRepository.findByOfficeName(officeName).orElseThrow(() -> new OfficeNotFoundException("Office not found with this name: " + officeName));
    }
    public Office createOffice(CreateOfficeRequest request){

        Office office = new Office(request.getOfficeName(),request.getOfficePhone(),request.getOfficePhoto(),request.getCity()
                ,request.getDistrict(),request.getStreet(),request.getNumber());
        return officeRepository.save(office);

    }
}
