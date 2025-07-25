-- Created by Vertabelo (http://vertabelo.com)
-- Last amodification date: 2023-05-17 12:13:00.273

-- tables
-- Table: genre
CREATE TABLE genre (
                       id serial  NOT NULL,
                       name varchar(255)  NOT NULL,
                       CONSTRAINT genre_pk PRIMARY KEY (id)
);

-- Table: movie
CREATE TABLE movie (
                       id serial  NOT NULL,
                       genre_id int,
                       runtime int  NOT NULL,
                       title varchar(255)  NOT NULL,
                       director varchar(255)  NOT NULL,
                       youtube_link varchar(255)  NOT NULL,
                       description text  NOT NULL,
                       poster_image bytea,
                       status varchar(1)  NOT NULL,
                       CONSTRAINT movie_pk PRIMARY KEY (id)
);

-- Table: role
CREATE TABLE role (
                      id serial  NOT NULL,
                      name varchar(255)  NOT NULL,
                      CONSTRAINT role_pk PRIMARY KEY (id)
);

-- Table: room
CREATE TABLE room (
                      id serial  NOT NULL,
                      name varchar(255)  NOT NULL,
                      rows int  NOT NULL,
                      cols int  NOT NULL,
                      CONSTRAINT room_pk PRIMARY KEY (id)
);

-- Table: seance
CREATE TABLE seance (
                        id serial  NOT NULL,
                        room_id int  NOT NULL,
                        movie_id int  NOT NULL,
                        start_time timestamp with time zone NOT NULL,
                        language varchar(255)  NOT NULL,
                        subtitles varchar(255)  NOT NULL,
                        status varchar(1)  NOT NULL,
                        CONSTRAINT seance_pk PRIMARY KEY (id)
);

-- Table: seat
CREATE TABLE seat (
                      id serial  NOT NULL,
                      room_id int  NOT NULL,
                      col int NOT NULL,
                      row int NOT NULL,
                      CONSTRAINT seat_pk PRIMARY KEY (id)
);

-- Table: ticket
CREATE TABLE ticket (
                        id serial  NOT NULL,
                        user_id int  NOT NULL,
                        seat_id int  NOT NULL,
                        seance_id int  NOT NULL,
                        ticket_type_id int  NOT NULL,
                        CONSTRAINT ticket_pk PRIMARY KEY (id)
);

-- Table: ticket_type
CREATE TABLE ticket_type (
                             id serial  NOT NULL,
                             name varchar(255)  NOT NULL,
                             price decimal(10,2)  NOT NULL,
                             CONSTRAINT ticket_type_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE "user" (
                        id serial  NOT NULL,
                        username varchar(255)  NOT NULL,
                        password varchar(255)  NOT NULL,
                        role_id int  NOT NULL,
                        email varchar(255)  NOT NULL,
                        status varchar(1)  NOT NULL,
                        CONSTRAINT user_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: movie_genre (table: movie)
ALTER TABLE movie ADD CONSTRAINT movie_genre
    FOREIGN KEY (genre_id)
        REFERENCES genre (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: seance_room (table: seance)
ALTER TABLE seance ADD CONSTRAINT seance_room
    FOREIGN KEY (room_id)
        REFERENCES room (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: seat_room (table: seat)
ALTER TABLE seat ADD CONSTRAINT seat_room
    FOREIGN KEY (room_id)
        REFERENCES room (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: session_movie (table: seance)
ALTER TABLE seance ADD CONSTRAINT session_movie
    FOREIGN KEY (movie_id)
        REFERENCES movie (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ticket_seance (table: ticket)
ALTER TABLE ticket ADD CONSTRAINT ticket_seance
    FOREIGN KEY (seance_id)
        REFERENCES seance (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ticket_seat (table: ticket)
ALTER TABLE ticket ADD CONSTRAINT ticket_seat
    FOREIGN KEY (seat_id)
        REFERENCES seat (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ticket_ticket_type (table: ticket)
ALTER TABLE ticket ADD CONSTRAINT ticket_ticket_type
    FOREIGN KEY (ticket_type_id)
        REFERENCES ticket_type (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: ticket_user (table: ticket)
ALTER TABLE ticket ADD CONSTRAINT ticket_user
    FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: user_role (table: user)
ALTER TABLE "user" ADD CONSTRAINT user_role
    FOREIGN KEY (role_id)
        REFERENCES role (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;
