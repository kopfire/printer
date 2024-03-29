CREATE TABLE public.types_cartridges
(
    id             INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name           VARCHAR
);

CREATE TABLE public.printers
(
    id             INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name           VARCHAR,
    types_cartridges VARCHAR ARRAY
);

CREATE TABLE public.housings
(
    id             INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name           VARCHAR
);

CREATE TABLE public.offices
(
    id             INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name           VARCHAR,
    housing        INT,
    CONSTRAINT  offices_housings_fk FOREIGN KEY (housing) REFERENCES housings(id)
);

CREATE TABLE public.cartridges
(
    id             INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    type_cartridge INT,
    text_qr        VARCHAR,
    status         VARCHAR,
    office         INT,
    text_status    TEXT,
    CONSTRAINT  cartridges_type_cartridge_fk FOREIGN KEY (type_cartridge) REFERENCES types_cartridges(id),
    CONSTRAINT  cartridges_offices_fk FOREIGN KEY (office) REFERENCES offices(id)
);

CREATE TABLE public.person
(
    id             INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR NOT NULL,
    role VARCHAR(100) NOT NULL
);

INSERT INTO public.person (username, password, role) VALUES ('admin', '$2a$10$ylP/CPQiNcMuaMaI3wz47ulUV2GvAV2y0ATxmrYaDvzGFkHFgtpze', 'ROLE_ADMIN');

INSERT INTO public.housings (name) VALUES ('Главный корпус');
INSERT INTO public.housings (name) VALUES ('Пристрой');
INSERT INTO public.housings (name) VALUES ('Общежитие №1');
INSERT INTO public.housings (name) VALUES ('Общежитие №3');
INSERT INTO public.housings (name) VALUES ('Учебный корпус №2');
INSERT INTO public.housings (name) VALUES ('Учебный корпус №3');
INSERT INTO public.housings (name) VALUES ('Учебный корпус №7');
INSERT INTO public.housings (name) VALUES ('Учебный корпус №8');
INSERT INTO public.housings (name) VALUES ('Учебный корпус №9');
INSERT INTO public.housings (name) VALUES ('АЦКК');
INSERT INTO public.housings (name) VALUES ('Знаменск УК №1');
INSERT INTO public.housings (name) VALUES ('Знаменск УК №2');
INSERT INTO public.housings (name) VALUES ('Общежитие №2');
INSERT INTO public.housings (name) VALUES ('Общежитие №4');
INSERT INTO public.housings (name) VALUES ('Общежитие №5');
INSERT INTO public.housings (name) VALUES ('Общежитие №6');
INSERT INTO public.housings (name) VALUES ('Общежитие №7');

INSERT INTO public.offices (name, housing) VALUES ('112a', 1);
INSERT INTO public.offices (name, housing) VALUES ('412', 1);
INSERT INTO public.offices (name, housing) VALUES ('408', 1);
INSERT INTO public.offices (name, housing) VALUES ('608', 2);
INSERT INTO public.offices (name, housing) VALUES ('508', 2);
INSERT INTO public.offices (name, housing) VALUES ('709', 2);
INSERT INTO public.offices (name, housing) VALUES ('1', 3);


INSERT INTO public.types_cartridges (name) VALUES ('12a');
INSERT INTO public.types_cartridges (name) VALUES ('15a');

INSERT INTO public.printers (name, types_cartridges) VALUES ('HP LaserJet 1020', '{"12a", "15a"}');

INSERT INTO public.cartridges (type_cartridge, text_qr, status, office) VALUES (1, 'АГУ_Картридж_1', 'У пользователей', 1);