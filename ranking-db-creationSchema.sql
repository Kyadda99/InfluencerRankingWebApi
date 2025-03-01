-- Database: rankingdb

-- DROP DATABASE IF EXISTS rankingdb;

CREATE DATABASE rankingdb
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'pl-PL'
    LC_CTYPE = 'pl-PL'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;



-- Table: public.CAMPAIGN

-- DROP TABLE IF EXISTS public."CAMPAIGN";

CREATE TABLE IF NOT EXISTS public."CAMPAIGN"
(
    id integer NOT NULL DEFAULT nextval('campaign_id_seq'::regclass),
    influencer_id integer NOT NULL,
    amount_of_opens integer NOT NULL,
    amount_of_interactions integer NOT NULL,
    CONSTRAINT campaign_pkey PRIMARY KEY (id),
    CONSTRAINT fk_influencer FOREIGN KEY (influencer_id)
        REFERENCES public."INFLUENCERS" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."CAMPAIGN"
    OWNER to postgres;


	-- Table: public.CATEGORY

-- DROP TABLE IF EXISTS public."CATEGORY";

CREATE TABLE IF NOT EXISTS public."CATEGORY"
(
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    influencer_id integer NOT NULL,
    CONSTRAINT fk_influencer_category FOREIGN KEY (influencer_id)
        REFERENCES public."INFLUENCERS" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."CATEGORY"
    OWNER to postgres;
-- Index: uq_category_influencer

-- DROP INDEX IF EXISTS public.uq_category_influencer;

CREATE UNIQUE INDEX IF NOT EXISTS uq_category_influencer
    ON public."CATEGORY" USING btree
    (name COLLATE pg_catalog."default" ASC NULLS LAST, influencer_id ASC NULLS LAST)
    TABLESPACE pg_default;





-- Table: public.CONTENT

-- DROP TABLE IF EXISTS public."CONTENT";

CREATE TABLE IF NOT EXISTS public."CONTENT"
(
    id integer NOT NULL DEFAULT nextval('content_id_seq'::regclass),
    likes integer NOT NULL,
    shares integer NOT NULL,
    followers_gained integer NOT NULL,
    publication_date character varying(50) COLLATE pg_catalog."default" NOT NULL,
    influencer_id integer NOT NULL,
    comments integer NOT NULL,
    CONSTRAINT content_pkey PRIMARY KEY (id),
    CONSTRAINT fk_content_influencer FOREIGN KEY (influencer_id)
        REFERENCES public."INFLUENCERS" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."CONTENT"
    OWNER to postgres;




-- Table: public.INFLUENCERS

-- DROP TABLE IF EXISTS public."INFLUENCERS";

CREATE TABLE IF NOT EXISTS public."INFLUENCERS"
(
    id integer NOT NULL DEFAULT nextval('influencers_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    followers integer NOT NULL,
    location character varying(255) COLLATE pg_catalog."default",
    categories text COLLATE pg_catalog."default",
    size character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT influencers_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."INFLUENCERS"
    OWNER to postgres;


	
	