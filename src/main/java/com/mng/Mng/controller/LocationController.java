package com.mng.Mng.controller;

import com.mng.Mng.dto.LocationDto;
import com.mng.Mng.model.Location;
import com.mng.Mng.service.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/locations")
public class LocationController {
    private final LocationService locationService;
    private final ModelMapper modelMapper;

    public LocationController(LocationService locationService, ModelMapper modelMapper) {
        this.locationService = locationService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Integer id){
        Location location = locationService.getLocationById(id);
        return ResponseEntity.ok(modelMapper.map(location,LocationDto.class));
    }

    @PostMapping("/create/user/location/{email}")
    public ResponseEntity<LocationDto> addLocationToUser(@PathVariable String email, @RequestBody LocationDto dto){
        var location = locationService.addLocationToUser(email,dto);
        return ResponseEntity.ok(modelMapper.map(location,LocationDto.class));
    }/*
    @PostMapping("/create/office/{id}")
    public ResponseEntity<LocationDto> addLocationToOffice(@PathVariable String id, @RequestBody LocationDto dto){
        var location = locationService.addLocationToOffice(id,dto);

        return ResponseEntity.ok(modelMapper.map(location,LocationDto.class));
    }*/
    @PutMapping("/update/{id}/user/{email}")
    public ResponseEntity<LocationDto> updateLocationToUser(@PathVariable Integer id,@PathVariable String email, @RequestBody LocationDto dto) {
        var location = locationService.updateLocationToUser(id, email, dto);
        return ResponseEntity.ok(modelMapper.map(location,LocationDto.class));
    }
    @PutMapping("/update/{id}/office/{officeId}")
    public ResponseEntity<LocationDto> updateLocationToOffice(@PathVariable Integer id,@PathVariable String officeId, @RequestBody LocationDto dto) {
        var location = locationService.updateLocationToOffice(id, officeId ,dto);
        return ResponseEntity.ok(modelMapper.map(location,LocationDto.class));
    }

    @GetMapping("/calculate-distance/{email}/{officeId}")
    public boolean areLocationsIntersecting(@PathVariable String email, @PathVariable String officeId) {
        return locationService.areLocationsIntersecting(email, officeId);

    }
    }
