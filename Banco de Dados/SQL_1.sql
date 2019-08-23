create table L_Usuario
(
id int identity primary key not null,
nome varchar(30) not null,
senha varchar(30) not null,
imgPerfil ntext 
)

create table L_Acesso
(
id int identity primary key not null,
dataAcesso datetime not null
)

create table L_Filho
(
id int identity primary key not null,
idPai int not null 
constraint FK_USUARIO foreign key (idPai) references L_Usuario(id),
deficiencia varchar(50),
idade int not null,
dataNascimento datetime not null,
texto nText
)

create table L_Habilidade
(
id int identity primary key not null,
idFilho int not null 
constraint FK_FILHO foreign key (idFilho) references L_Filho(id),
nome varchar(30) not null,
pontuacao decimal not null 
constraint CHK_PORCENTAGEM check(pontuacao >= 0 and pontuacao <=1)
)

create table L_Textos
(
id int identity primary key not null,
titulo varchar(30) not null,
texto ntext not null
)

create table L_Imagem
(
id int identity primary key not null,
imag nText not null
)
