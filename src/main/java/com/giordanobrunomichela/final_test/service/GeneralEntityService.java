package com.giordanobrunomichela.final_test.service;

import com.giordanobrunomichela.final_test.dto.GeneralEntityDTO;
import com.giordanobrunomichela.final_test.model.GeneralEntity;
import com.giordanobrunomichela.final_test.model.GeneralEntityType;
import com.giordanobrunomichela.final_test.repository.GeneralEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeneralEntityService {
    private final GeneralEntityRepository generalEntityRepository;

    public GeneralEntityService(GeneralEntityRepository generalEntityRepository) {
        this.generalEntityRepository = generalEntityRepository;
    }

    public List<GeneralEntityDTO> getAllGeneralEntities() {
        return generalEntityRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public GeneralEntityDTO getGeneralEntityById(Long id) {
        return generalEntityRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public GeneralEntityDTO createGeneralEntity(GeneralEntityDTO generalEntityDTO) {
        GeneralEntity entity = convertToEntity(generalEntityDTO);
        return convertToDTO(generalEntityRepository.save(entity));
    }

    /*
     * public GeneralEntityDTO updateGeneralEntity(Long id, GeneralEntityDTO dto) {
     * Optional<GeneralEntity> existingEntity =
     * generalEntityRepository.findById(id);
     * if (existingEntity.isPresent()) {
     * GeneralEntity entity = convertToEntity(dto);
     * entity.setId(id);
     * return convertToDTO(generalEntityRepository.save(entity));
     * }
     * return null;
     * }
     */

    public GeneralEntityDTO updateGeneralEntity(Long id, GeneralEntityDTO dto) {
        Optional<GeneralEntity> existingEntityOpt = generalEntityRepository.findById(id);
        if (existingEntityOpt.isPresent()) {
            GeneralEntity entity = existingEntityOpt.get();

            if (dto.getName() != null) {
                entity.setName(dto.getName());
            }
            if (dto.getDescription() != null) {
                entity.setDescription(dto.getDescription());
            }
            if (dto.getGeneralEntityTypeIds() != null) {
                List<GeneralEntityType> types = dto.getGeneralEntityTypeIds().stream()
                        .map(typeId -> {
                            GeneralEntityType type = new GeneralEntityType();
                            type.setId(typeId);
                            return type;
                        })
                        .collect(Collectors.toList());
                entity.setGeneralEntityType(types);
            }

            return convertToDTO(generalEntityRepository.save(entity));
        }
        return null;
    }

    public void deleteGeneralEntity(Long id) {
        generalEntityRepository.deleteById(id);
    }

    public GeneralEntityDTO convertToDTO(GeneralEntity entity) {
        GeneralEntityDTO dto = new GeneralEntityDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setGeneralEntityTypeIds(entity.getGeneralEntityType().stream()
                .map(GeneralEntityType::getId)
                .collect(Collectors.toList())); // <-- changed to toList()
        return dto;
    }

    public GeneralEntity convertToEntity(GeneralEntityDTO dto) {
        GeneralEntity entity = new GeneralEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        List<GeneralEntityType> types = dto.getGeneralEntityTypeIds().stream()
                .distinct()
                .map(id -> {
                    GeneralEntityType type = new GeneralEntityType();
                    type.setId(id);
                    return type;
                })
                .collect(Collectors.toList());
        entity.setGeneralEntityType(types);
        return entity;
    }
}