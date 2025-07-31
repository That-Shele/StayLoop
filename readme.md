INSERT INTO roles (nombre) VALUES ('admin');
INSERT INTO roles (nombre) VALUES ('hotel');
INSERT INTO roles (nombre) VALUES ('usuario');
-- clave 12345
INSERT INTO usuarios (nombre, id_rol, email, password) VALUES ('sysadmin', 1, 'admin@gmail.com', '$2a$12$r74HSGhuNB5zqfLG3fiao.OlRzKsPv/6R5EuhLNeFqjkKM7BZJ20m');