-- Insert default data for roles
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN')
ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Insert default data for general_entity_type
INSERT INTO general_entity_type (id, name, description) VALUES (1, 'Type One', 'Description for Type One')
ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description);
INSERT INTO general_entity_type (id, name, description) VALUES (2, 'Type Two', 'Description for Type Two')
ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description);

-- Insert default data for general_entity and associate with general_entity_type
INSERT INTO general_entity (id, name, description) VALUES (1, 'Entity One', 'Description for Entity One')
ON DUPLICATE KEY UPDATE name = VALUES(name), description = VALUES(description);
INSERT INTO general_entity_entity_type (general_entity_id, general_entity_type_id) VALUES (1, 1), (1, 2)
ON DUPLICATE KEY UPDATE general_entity_id = VALUES(general_entity_id), general_entity_type_id = VALUES(general_entity_type_id);