CREATE DATABASE seguridad_db;
GO
USE seguridad_db;
GO

CREATE TABLE usuarios (
    id INT IDENTITY(1,1) PRIMARY KEY,
    usuario NVARCHAR(50) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    nombre NVARCHAR(100) NOT NULL,
    rol VARCHAR(10) NOT NULL CHECK (rol IN ('user', 'admin')),
    activo BIT NOT NULL DEFAULT 1,
    fecha_creacion DATETIME2 DEFAULT GETDATE()
);
GO

CREATE TABLE productos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(150) NOT NULL,
    descripcion NVARCHAR(MAX),
    precio DECIMAL(10,2) NOT NULL,
    categoria NVARCHAR(100),
    stock INT NOT NULL DEFAULT 0,
    activo BIT NOT NULL DEFAULT 1,
    fecha_creacion DATETIME2 DEFAULT GETDATE()
);
GO

INSERT INTO usuarios (usuario, password_hash, nombre, rol) VALUES
('admin', '$2a$12$EPxPIhBtfVM0UUk8JdXRweNXrajQvmUqf0UYGRpCcmWfzjFAlDn9O', 'Administrador', 'admin'),
('usuario', '$2a$12$YKOf8SQ/c4BTGHFqIQCe4eJPW7Gfap8CPe8v0cda.2bIRcQx2mevi', 'Usuario Demo', 'user');
GO

INSERT INTO productos (nombre, descripcion, precio, categoria, stock) VALUES
('Laptop Gamer', 'Laptop con RTX 4070, 32GB RAM', 2500.00, 'Electrónica', 10),
('Teclado Mecánico', 'Teclado RGB Switch Red', 89.99, 'Periféricos', 25),
('Mouse Inalámbrico', 'Mouse ergonómico 8000 DPI', 45.50, 'Periféricos', 30),
('Monitor 27" 4K', 'Monitor IPS 144Hz', 599.99, 'Electrónica', 15),
('Webcam HD', 'Cámara 1080p con micrófono', 79.99, 'Accesorios', 20);
GO
