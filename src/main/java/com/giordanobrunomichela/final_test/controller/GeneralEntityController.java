package com.giordanobrunomichela.final_test.controller;

import com.giordanobrunomichela.final_test.dto.GeneralEntityDTO;
import com.giordanobrunomichela.final_test.service.GeneralEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generalentity")
@CrossOrigin(origins = "http://localhost:3000")
public class GeneralEntityController {

    private final GeneralEntityService generalEntityService;

    public GeneralEntityController(GeneralEntityService generalEntityService) {
        this.generalEntityService = generalEntityService;
    }

    @GetMapping("/all")
    public List<GeneralEntityDTO> getAllGeneralEntities() {
        return generalEntityService.getAllGeneralEntities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralEntityDTO> getGeneralEntityById(@PathVariable Long id) {
        GeneralEntityDTO dto = generalEntityService.getGeneralEntityById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralEntityDTO> createGeneralEntity(@RequestBody GeneralEntityDTO generalEntityDTO) {
        GeneralEntityDTO created = generalEntityService.createGeneralEntity(generalEntityDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralEntityDTO> updateGeneralEntity(@PathVariable Long id, @RequestBody GeneralEntityDTO dto) {
        GeneralEntityDTO updated = generalEntityService.updateGeneralEntity(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGeneralEntity(@PathVariable Long id) {
        generalEntityService.deleteGeneralEntity(id);
        return ResponseEntity.noContent().build();
    }
}