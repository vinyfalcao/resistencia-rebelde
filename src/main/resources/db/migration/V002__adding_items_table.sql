CREATE TABLE public.items (
	id int8 NOT NULL,
	nome varchar(255) NULL,
	pontos int8 NULL,
	CONSTRAINT items_pkey PRIMARY KEY (id)
);

CREATE TABLE public.item_inventario_rebelde (
	id int8 NOT NULL,
	item_id int8 NOT NULL,
	rebelde_id int8 NOT NULL,
	CONSTRAINT item_inventario_rebelde_pk PRIMARY KEY (id),
	CONSTRAINT item_id_rebelde_id_uk UNIQUE (item_id, rebelde_id),
	CONSTRAINT item_id_items_fk FOREIGN KEY (item_id) REFERENCES items(id),
	CONSTRAINT rebelde_id_rebelde_fk FOREIGN KEY (rebelde_id) REFERENCES rebelde_entity(id)
);