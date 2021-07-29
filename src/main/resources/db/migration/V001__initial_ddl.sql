-- DROP SEQUENCE public.hibernate_sequence;

CREATE SEQUENCE public.hibernate_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- public.localizacao_rebelde_entity definition

-- Drop table

-- DROP TABLE public.localizacao_rebelde_entity;

CREATE TABLE public.localizacao_rebelde_entity (
	id int8 NOT NULL,
	latitude float8 NULL,
	longitude float8 NULL,
	nome_galaxia varchar(255) NULL,
	CONSTRAINT localizacao_rebelde_entity_pkey PRIMARY KEY (id),
	CONSTRAINT uk3cgl7rnmyn0yvjok2daj5p0e6 UNIQUE (nome_galaxia, latitude, longitude)
);


-- public.rebelde_entity definition

-- Drop table

-- DROP TABLE public.rebelde_entity;

CREATE TABLE public.rebelde_entity (
	id int8 NOT NULL,
	genero varchar(255) NULL,
	idade int8 NULL,
	nome varchar(255) NULL,
	traidor bool NOT NULL,
	localizacao_id int8 NULL,
	CONSTRAINT rebelde_entity_pkey PRIMARY KEY (id),
	CONSTRAINT uk_l669lrgk7sp177wqdbkoilhax UNIQUE (nome),
	CONSTRAINT fkejh5lg3sry5d6y1lcatri91hj FOREIGN KEY (localizacao_id) REFERENCES localizacao_rebelde_entity(id)
);


-- public.tabela_traicao definition

-- Drop table

-- DROP TABLE public.tabela_traicao;

CREATE TABLE public.tabela_traicao (
	id int8 NOT NULL,
	relator_id int8 NULL,
	reportado_id int8 NULL,
	CONSTRAINT tabela_traicao_pkey PRIMARY KEY (id),
	CONSTRAINT ukpe2tiy4u81evf5mxcwa33lqro UNIQUE (relator_id, reportado_id),
	CONSTRAINT fk57xy8wtxi0r8jbs3q8d9oni0h FOREIGN KEY (reportado_id) REFERENCES rebelde_entity(id),
	CONSTRAINT fk5ei81bqwaa30dy3afusegmks7 FOREIGN KEY (relator_id) REFERENCES rebelde_entity(id)
);


-- public.localizacao_rebelde_entity_rebeldes definition

-- Drop table

-- DROP TABLE public.localizacao_rebelde_entity_rebeldes;

CREATE TABLE public.localizacao_rebelde_entity_rebeldes (
	localizacao_rebelde_entity_id int8 NOT NULL,
	rebeldes_id int8 NOT NULL,
	CONSTRAINT uk_bywpdxser3d63jm0gtcgb64mt UNIQUE (rebeldes_id),
	CONSTRAINT fkelmoxujd6p343rkuy5fb44bw3 FOREIGN KEY (rebeldes_id) REFERENCES rebelde_entity(id),
	CONSTRAINT fkn9fwls0ufd6yd02t1p1rp1lu8 FOREIGN KEY (localizacao_rebelde_entity_id) REFERENCES localizacao_rebelde_entity(id)
);
