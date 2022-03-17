--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

-- Started on 2022-01-31 23:27:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3016 (class 1262 OID 50868)
-- Name: orcamentos; Type: DATABASE; Schema: -; Owner: admin
--

ALTER DATABASE orcamentos OWNER TO admin;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 200 (class 1259 OID 50869)
-- Name: seq-despesa; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."seq-despesa"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."seq-despesa" OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 201 (class 1259 OID 50871)
-- Name: despesa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.despesa (
    iddespesa bigint DEFAULT nextval('public."seq-despesa"'::regclass) NOT NULL,
    idorcamento bigint NOT NULL,
    data character varying(10) NOT NULL,
    descricao character varying(100) NOT NULL,
    situacao character varying(100),
    valor real NOT NULL
);


ALTER TABLE public.despesa OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 50875)
-- Name: seq-orcamento; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."seq-orcamento"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."seq-orcamento" OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 50877)
-- Name: orcamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orcamento (
    idorcamento bigint DEFAULT nextval('public."seq-orcamento"'::regclass) NOT NULL,
    nome character varying(100) NOT NULL
);


ALTER TABLE public.orcamento OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 50881)
-- Name: seq-receita; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."seq-receita"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."seq-receita" OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 50883)
-- Name: receita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.receita (
    idreceita bigint DEFAULT nextval('public."seq-receita"'::regclass) NOT NULL,
    idorcamento bigint NOT NULL,
    mes integer NOT NULL,
    tipo character varying(100) NOT NULL,
    valor real NOT NULL
);


ALTER TABLE public.receita OWNER TO postgres;

--
-- TOC entry 3006 (class 0 OID 50871)
-- Dependencies: 201
-- Data for Name: despesa; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3008 (class 0 OID 50877)
-- Dependencies: 203
-- Data for Name: orcamento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3010 (class 0 OID 50883)
-- Dependencies: 205
-- Data for Name: receita; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3017 (class 0 OID 0)
-- Dependencies: 200
-- Name: seq-despesa; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."seq-despesa"', 1, false);


--
-- TOC entry 3018 (class 0 OID 0)
-- Dependencies: 202
-- Name: seq-orcamento; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."seq-orcamento"', 1, false);


--
-- TOC entry 3019 (class 0 OID 0)
-- Dependencies: 204
-- Name: seq-receita; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."seq-receita"', 1, false);


--
-- TOC entry 2868 (class 2606 OID 50888)
-- Name: orcamento orcamento_nome_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orcamento
    ADD CONSTRAINT orcamento_nome_key UNIQUE (nome);


--
-- TOC entry 2866 (class 2606 OID 50890)
-- Name: despesa pk_despesa; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT pk_despesa PRIMARY KEY (iddespesa);


--
-- TOC entry 2870 (class 2606 OID 50892)
-- Name: orcamento pk_orcamento; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orcamento
    ADD CONSTRAINT pk_orcamento PRIMARY KEY (idorcamento);


--
-- TOC entry 2872 (class 2606 OID 50894)
-- Name: receita pk_receita; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT pk_receita PRIMARY KEY (idreceita);


--
-- TOC entry 2873 (class 2606 OID 50895)
-- Name: despesa fk_orcamento; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT fk_orcamento FOREIGN KEY (idorcamento) REFERENCES public.orcamento(idorcamento) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2874 (class 2606 OID 50900)
-- Name: receita fk_orcamento; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT fk_orcamento FOREIGN KEY (idorcamento) REFERENCES public.orcamento(idorcamento) ON UPDATE CASCADE ON DELETE CASCADE;


-- Completed on 2022-01-31 23:27:09

--
-- PostgreSQL database dump complete
--

