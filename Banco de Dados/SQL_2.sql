create procedure acesso_sp
@login varchar(30) = null,
@senha varchar(30) = null
as
begin
if(@login is not null and @senha is not null)
begin 
	if exists (select * from L_Usuario where email = @login and senha = @senha)
	begin
		insert into L_Acesso values(GetDate(), (select id from L_Usuario where email = @login))
		return 'T'
	end
end	
	return 'F'
end

create procedure cadastro_sp
@nome varchar(30) = null,
@email varchar(30) = null,
@senha varchar(30) = null
as
begin
if(@nome is not null and @senha is not null and @email is not null)
begin 
	if not exists (select * from L_Usuario where email = @email)
	begin
		insert into L_Usuario values(@nome, @email, @senha)
		return 'T'
	end	
end
	return 'F'
end

alter procedure cadastrarFilho_sp
@email varchar(30) = null,
@nome varchar(30) = null,
@def varchar(50) = null,
@dataNascimento datetime = null,
@texto nText = null,
@imgPerfil nText = null
as
begin
if(@email is not null and @def is not null and @dataNascimento is not null)
begin 
	if exists (select * from L_Usuario where email = @email)
	begin
		if not exists (select * from L_Filho where idpai =(select id from L_Usuario where email = @email) and nome = @nome)
		begin
			insert into L_Filho values((select id from L_Usuario where email = @email), @def, year(GETDATE() - @dataNascimento), @dataNascimento, @texto, @imgPerfil, @nome)
			return 'T'
		end
	end	
end
	return 'F'
end

create procedure insereHabilidade_sp
@email varchar(30) = null,
@nome varchar(30) = null,
@def varchar(50) = null,
@dataNascimento datetime = null,
@texto nText = null,
@imgPerfil nText = null
as
begin
if(@email is not null and @def is not null and @dataNascimento is not null)
begin 
	if exists (select * from L_Usuario where email = @email)
	begin
		if not exists (select * from L_Filho where idpai =(select id from L_Usuario where email = @email) and nome = @nome)
		begin
			insert into L_Filho values((select id from L_Usuario where email = @email), @def, year(GETDATE() - @dataNascimento), @dataNascimento, @texto, @imgPerfil, @nome)
			return 'T'
		end
	end	
end
	return 'F'
end
