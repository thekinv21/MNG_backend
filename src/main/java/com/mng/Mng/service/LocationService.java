package com.mng.Mng.service;

import com.mng.Mng.dto.LocationDto;
import com.mng.Mng.exception.LocationNotFoundException;
import com.mng.Mng.model.Location;
import com.mng.Mng.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserService userService;
    private final OfficeService officeService;

    public LocationService(LocationRepository locationRepository, UserService userService, OfficeService officeService) {
        this.locationRepository = locationRepository;
        this.userService = userService;
        this.officeService = officeService;
    }

    public Location getLocationById(Integer id){
        return locationRepository.findLocationById(id).orElseThrow(() -> new LocationNotFoundException("Location not found with this id: " + id));
    }


    public Location addLocationToUser(String email, LocationDto dto){
        var user = userService.getUserByEmail(email);
        Location location = new Location(dto.getLatitude(),dto.getLongitude());
        user.setLocation(location);
        location.setUser(user);
        return locationRepository.save(location);
    }
    public Location addLocationToOffice(String id, LocationDto dto){
        var office = officeService.getOfficeById(id);
        Location location = new Location(dto.getLatitude(),dto.getLongitude());
        if (office.getLocation() != null) {
            locationRepository.delete(office.getLocation());
        }
        office.setLocation(location);
        location.setOffice(office);
        return locationRepository.save(location);
    }
    public Location updateLocationToUser(Integer id, String email,LocationDto dto){
        var user = userService.getUserByEmail(email);
        var location = getLocationById(id);
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        user.setLocation(location);
        location.setUser(user);
        return locationRepository.save(location);

    }
    public Location updateLocationToOffice(Integer id, String officeId,LocationDto dto){
        var office = officeService.getOfficeById(officeId);
        var location = getLocationById(id);
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        office.setLocation(location);
        location.setOffice(office);
        return locationRepository.save(location);
    }
    // İki nokta arasındaki mesafeyi hesaplayan metot
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c * 1000;
    }



    public boolean areLocationsIntersecting(String email, String officeId) {
        var user = userService.getUserByEmail(email);
        var office = officeService.getOfficeById(officeId);

        double distance = calculateDistance(user.getLocation().getLatitude(), user.getLocation().getLongitude(),
                office.getLocation().getLatitude(), office.getLocation().getLongitude());

        return distance <= 15.0;
    }


}
