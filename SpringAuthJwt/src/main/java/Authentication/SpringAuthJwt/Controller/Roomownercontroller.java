package Authentication.SpringAuthJwt.Controller;

import Authentication.SpringAuthJwt.Entities.Property;
import Authentication.SpringAuthJwt.Entities.RoomOwner;

import Authentication.SpringAuthJwt.Service.RoomOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/owners")
@CrossOrigin(origins = "*")
public class Roomownercontroller {

    @Autowired
   private RoomOwnerService roomOwnerService;

    @PostMapping
    public ResponseEntity<?> createOwner(@RequestBody RoomOwner roomowners) {
        try {
            RoomOwner createdOwner = roomOwnerService.createRoomOwner(roomowners);
            return ResponseEntity.ok(createdOwner);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to create owner: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/{ownerId}/properties")
    public ResponseEntity<?> addPropertyToOwner(@PathVariable Long ownerId, @RequestBody Property property) {
        try {
            Property addedProperty = roomOwnerService.addPropertyToOwner(ownerId, property);
            return ResponseEntity.ok(addedProperty);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Owner not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to add property: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping(
            path = "/{ownerId}/properties/with-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addPropertyWithImage(
            @PathVariable Long ownerId,
            @RequestPart(value = "file", required = true) MultipartFile file,
            @RequestPart(value = "propertyDetails", required = true) Property property) {

        try {
            // First add property to owner
            Property savedProperty = roomOwnerService.addPropertyToOwner(ownerId, property);

            // Then save the image and update the property
            Property updatedProperty = roomOwnerService.savePropertyWithImage(file, savedProperty);

            return ResponseEntity.ok(updatedProperty);

        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Owner not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to create property: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<RoomOwner>> getAllOwners() {
        try {
            List<RoomOwner> owners = roomOwnerService.getAllOwners();
            return ResponseEntity.ok(owners);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<?> getOwnerById(@PathVariable Long ownerId) {
        try {
            RoomOwner owner = roomOwnerService.getOwnerById(ownerId);
            return ResponseEntity.ok(owner);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Owner not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error retrieving owner: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{ownerId}/properties")
    public ResponseEntity<?> getAllPropertiesByOwnerId(@PathVariable Long ownerId) {
        try {
            List<Property> properties = roomOwnerService.getAllPropertiesByOwnerId(ownerId);
            return ResponseEntity.ok(properties);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Owner not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error retrieving properties: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}