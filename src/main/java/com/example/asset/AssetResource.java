package com.example.asset;

import com.example.assignment.AssetAssignmentDTO;
import com.example.exception.IllegalIdException;
import com.example.exception.SerialNumberConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetResource {
    private AssetService assetService;

    @Autowired
    public AssetResource(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssetDTO>> findAll(@RequestParam(required = false) String text) {
        if(text == null) {
            List<AssetDTO> assets = assetService.findAll();
            if (assets.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(assets);
        }

        List<AssetDTO> textAssets = assetService.findAllByNameContainingIgnoreCaseOrSerialNumberContainingIgnoreCase(text, text);

        if(textAssets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(textAssets);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetDTO> save(@RequestBody AssetDTO assetDTO) {
        if(assetDTO.getId() != null) {
            throw new IllegalIdException();
        }

        if(assetService.validSerialNumber(assetDTO)) {
            throw new SerialNumberConflictException();
        }
        assetService.save(assetDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(assetDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(assetDTO);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetDTO> findById(@PathVariable Long id) {
        AssetDTO asset = assetService.findById(id);

        if(asset == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(asset);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetDTO> update(@PathVariable Long id, @RequestBody AssetDTO assetDTO) {
        if(assetDTO.getId() == null || !assetDTO.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt powinien mieć id zgodne z id ścieżki zasobu");
        }

        if(assetService.validSerialNumber(assetDTO)) {
            throw new SerialNumberConflictException();
        }

        assetService.save(assetDTO);
        return ResponseEntity.ok(assetDTO);
    }

    @GetMapping(path = "/{id}/assignments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssetAssignmentDTO>> getAssetAssignments(@PathVariable Long id) {
        List<AssetAssignmentDTO> list = assetService.getAssetAssignment(id);
        if(list == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);

    }
}
