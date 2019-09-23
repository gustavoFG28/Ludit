create table L_Usuario
(
id int identity primary key not null,
nome varchar(30) not null,
email varchar(30) not null,
senha varchar(30) not null
)

create table L_Acesso
(
id int identity primary key not null,
dataAcesso datetime not null,
idUsuario int not null
constraint FK_ACESSADO foreign key(idUsuario) references L_Usuario(id)
)

create table L_Filho
(
id int identity primary key not null,
idPai int not null 
constraint FK_USUARIO foreign key (idPai) references L_Usuario(id),
deficiencia varchar(50),
idade int not null,
dataNascimento datetime not null,
texto nText,
imgPerfil ntext, 
nome varchar(30) not null
)

create table L_Habilidade
(
id int identity primary key not null,
idFilho int not null 
constraint FK_FILHO foreign key (idFilho) references L_Filho(id),
nome varchar(30) not null,
porcentagem decimal not null 
constraint CHK_PORCENTAGEM check(porcentagem >= 0 and porcentagem <=1)
)

create table L_Texto
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



insert into L_Texto values('1','LUDIT',
'Aplicativo que auxilia de forma educativa, divertida e interativa crianças com algum tipo de deficiência intelectual, motora, 
entre outras. Por meio da plataforma a criança consegue aprender de um forma que a deixe entretida e interessada.')

insert into L_Texto values('2','APAE',
'A Apae é uma escola, espalhada por todo o país, possuindo a missão de realizar, promover e articular ações de defesa de direitos e prevenção, 
orientações, prestação de serviços, apoio à família, direcionadas à melhoria da qualidade de vida da pessoa com deficiência intelectual
e à construção de uma sociedade justa e solidária.')

insert into L_Texto values('3', 'SOBRE NÓS',
'Com o objetivo de ajudar e ensinar crianças com algum tipo de deficiência ou dificuldade, nós Isabela Paulino e Gustavo Gitzel, 
através do nosso conhecimento de programação nos tornamos capazes de tornar a ideia de um aplicativo, que pode auxiliar 
milhares de brasileiros e brasileiras, realidade...')

insert into L_Imagem values('1','')
insert into L_Imagem values('2','')
insert into L_Imagem values('3','')
insert into L_Imagem values('4','')




