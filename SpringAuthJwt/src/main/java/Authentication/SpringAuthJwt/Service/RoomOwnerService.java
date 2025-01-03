package Authentication.SpringAuthJwt.Service;

import Authentication.SpringAuthJwt.Entities.Property;
import Authentication.SpringAuthJwt.Entities.RoomOwner;
import Authentication.SpringAuthJwt.FileStorageHelper.FileStorageHelper;
import Authentication.SpringAuthJwt.Repositories.PropertyRepository;
import Authentication.SpringAuthJwt.Repositories.RoomownerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RoomOwnerService {

    @Autowired
    private RoomownerRepository roomownerRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private FileStorageHelper fileStorageHelper;

    public RoomOwner createRoomOwner(RoomOwner roomOwner) {
        return roomownerRepository.save(roomOwner);
    }

    public Property addPropertyToOwner(Long ownerId, Property property) {
        RoomOwner owner = roomownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        property.setOwner(owner);
        return propertyRepository.save(property);
    }

    public Property savePropertyWithImage(MultipartFile file, Property property) throws IOException {
        // Use the injected fileStorageHelper instance
        String imageUrl = fileStorageHelper.storeFile(file);
        property.setImageUrl(imageUrl);

        // Save property with updated image URL
        return propertyRepository.save(property);
    }

    public List<RoomOwner> getAllOwners() {
        return roomownerRepository.findAll();
    }

    public RoomOwner getOwnerById(Long ownerId) {
        return roomownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
    }

    public List<Property> getAllPropertiesByOwnerId(Long ownerId) {
        RoomOwner owner = roomownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        return propertyRepository.findByOwner(owner);
    }
}
