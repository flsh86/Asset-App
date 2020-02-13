package com.example.rest;

import com.example.asset.AssetAssignmentDTO;
import com.example.asset.AssetDTO;
import com.example.services.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        if(text != null) {
            List<AssetDTO> textAssets = assetService.findAllByNameContainingIgnoreCaseOrSerialNumberContainingIgnoreCase(text, text);
            return ResponseEntity.ok(textAssets);
        } else {
           List<AssetDTO> assets = assetService.findAll();
           return ResponseEntity.ok(assets);
        }

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetDTO> save(@RequestBody AssetDTO assetDTO) {
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
        return ResponseEntity.ok(asset);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetDTO> update(@PathVariable Long id, @RequestBody AssetDTO assetDTO) {
        assetService.update(id, assetDTO);
        return ResponseEntity.ok(assetDTO);
    }

    @GetMapping(path = "/{id}/assignments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssetAssignmentDTO>> getAssetAssignments(@PathVariable Long id) {
        List<AssetAssignmentDTO> list = assetService.getAssetAssignment(id);
        return ResponseEntity.ok(list);
    }
}
