-- Cria��o do banco de dados
CREATE DATABASE PokemonDB2;
GO

USE PokemonDB2;
GO

CREATE TABLE Pokemon (
    nome NVARCHAR(50) PRIMARY KEY,  
    tipo1 NVARCHAR(20) NOT NULL,    
    tipo2 NVARCHAR(20) NOT NULL,    
    numero INT NOT NULL             
);


CREATE TABLE Habilidade (
    nomeHabilidade NVARCHAR(50) PRIMARY KEY,  
    tipo NVARCHAR(20) NOT NULL,               
    numeroUsos INT NOT NULL,                
    poder INT NOT NULL                       
);

CREATE TABLE Pokemon_Habilidade (
    id INT IDENTITY(1,1) PRIMARY KEY,           
    pokemonName NVARCHAR(50) NOT NULL,          
    habilidadeName NVARCHAR(50) NOT NULL,      
    FOREIGN KEY (pokemonName) REFERENCES Pokemon(nome),        
    FOREIGN KEY (habilidadeName) REFERENCES Habilidade(nomeHabilidade)  
);

CREATE TABLE Ginasio(
    id int IDENTITY(1,1) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    lider VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    numeroInsigneas VARCHAR (2) NOT NULL,
    asp VARCHAR(50) NOT NULL,
    quantidadePokemons VARCHAR (2) NOT NULL,
    MediaLevel VARCHAR (3) NOT NULL,
    PRIMARY key (id)
);

CREATE TABLE item (
    nome VARCHAR(100), 
    id INT not null,
    tipo VARCHAR(50),
    quantidade int ,
    PRIMARY key (id)
);
CREATE TABLE Ginasio_Pokemon (
    id_gp INT IDENTITY(1,1) not null,           
    pokemonName NVARCHAR(50) NOT NULL,          
    Ginasiolider VARCHAR(100) NOT NULL,      
	primary key (id_gp),
    FOREIGN KEY (pokemonName) REFERENCES Pokemon(nome),        
    FOREIGN KEY (Ginasiolider) REFERENCES Ginasio(lider)  
);
INSERT INTO Pokemon (nome, tipo1, tipo2, numero)
VALUES ('Pikachu', 'Eletrico', NULL, 25), ('Charmander','Fogo','Nenhum', 7),
('Bulbassaur','Planta','Venenoso', 7),('Squirtle','Agua','Nenhum', 4)

INSERT INTO Habilidade (nomeHabilidade, tipo, numeroUsos, poder)
VALUES ('Choque do Trovao', 'Eletrico', 15, 40)

INSERT INTO Pokemon_Habilidade (pokemonName, habilidadeName)
VALUES ('Pikachu', 'Choque do Trovo')

INSERT INTO Ginasio (tipo, lider, cidade, numeroInsigneas, quantidadePokemons, mediaNivel)
VALUES ('Eletrico', 'Lt. Surge', 'Vermilion City', 3, 3, 25.5)

INSERT INTO Item (nome_Item, tipo_Item, quantidade)
VALUES ('Pocao', 'Cura', 10)

