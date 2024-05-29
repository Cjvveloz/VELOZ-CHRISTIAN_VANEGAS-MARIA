DROP TABLE IF EXISTS ODONTOLOGOS;
CREATE TABLE ODONTOLOGOS(ID LONG AUTO_INCREMENT PRIMARY KEY, NUMERO_MATRICULA INT NOT NULL, NOMBRE VARCHAR(50) NOT NULL, APELLIDO VARCHAR(50) NOT NULL);
DROP TABLE IF EXISTS DOMICILIOS;
CREATE TABLE DOMICILIOS (ID LONG AUTO_INCREMENT PRIMARY KEY, CALLE VARCHAR(50) NOT NULL, NUMERO INT NOT NULL, LOCALIDAD VARCHAR(50) NOT NULL, PROVINCIA VARCHAR(50) NOT NULL);
DROP TABLE IF EXISTS PACIENTES;
CREATE TABLE PACIENTES (ID LONG AUTO_INCREMENT PRIMARY KEY, NOMBRE VARCHAR(100) NOT NULL, APELLIDO VARCHAR(100) NOT NULL, DNI INT NOT NULL, FECHA DATE NOT NULL, DOMICILIO_ID LONG);
DROP TABLE IF EXISTS TURNOS;
CREATE TABLE TURNOS (ID LONG AUTO_INCREMENT PRIMARY KEY, PACIENTES_ID LONG NOT NULL, ODONTOLOGOS_ID LONG NOT NULL, FECHAYHORA DATE NOT NULL);

INSERT INTO ODONTOLOGOS(NUMERO_MATRICULA, NOMBRE, APELLIDO)VALUES ('123', 'Christian', 'Veloz'), ('345', 'Paola', 'Valverde'), ('678', 'Pedro', 'Rodriguez');
-- para test --
INSERT INTO DOMICILIOS(CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES ('Av Siempre Viva', 742, 'Springfield', 'Massachusetts'), ('Calle Wallaby', 42, 'Sydney', 'Nueva Gales del Sur');

INSERT INTO PACIENTES (NOMBRE, APELLIDO, DNI, FECHA, DOMICILIO_ID) VALUES ('Emiliano', 'Celli', 35464856, '2023-05-24', 1), ('Pablo', 'Clemente', 3548546, '2023-05-24', 2);

