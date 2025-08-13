INSERT INTO roles (nombre) VALUES ('admin');
INSERT INTO roles (nombre) VALUES ('hotel');
INSERT INTO roles (nombre) VALUES ('usuario');
-- clave 12345
INSERT INTO usuarios (nombre, id_rol, email, password, status) VALUES ('sysadmin', 1, 'admin@gmail.com', '$2a$12$XTyv3vgxJOVPjcC9wfryFOF7fZHb0Lpv6l2Kjtt5UEi9AHin6Z2fC', 1);