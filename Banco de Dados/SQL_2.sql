alter procedure acesso_sp
@login varchar(30) = null,
@senha varchar(30) = null
as
begin
if(@login is not null and @senha is not null)
begin 
	if exists (select * from L_Usuario where email = @login and senha = @senha)
	begin
		insert into L_Acesso values(GetDate(), (select id from L_Usuario where email = @login))
		return 0
	end
end	
	return 1
end

alter procedure cadastro_sp
@nome varchar(30) = null,
@email varchar(30) = null,
@senha varchar(30) = null
as
begin
if(@nome is not null and @senha is not null and @email is not null)
begin 
	if not exists (select * from L_Usuario where email = @email)
		insert into L_Usuario values(@nome, @email, @senha)

	select * from L_Usuario where nome = @nome and email = @email and senha = @senha
end
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
declare @idPai int;
if(@email is not null and @def is not null and @dataNascimento is not null)
begin 
	if exists (select * from L_Usuario where email = @email)
	begin
	select @idPai = id from L_Usuario where email = @email;
		if not exists (select * from L_Filho where idpai = @idPai and nome = @nome)
		begin
			insert into L_Filho values(@idPai, @def, DATEDIFF(year, @dataNascimento,  GETDATE()), @dataNascimento, @texto, @imgPerfil, @nome)
			select * from L_Filho where nome = @nome and idPai = @idPai
		end
	end	
end
end


alter procedure insereHabilidade_sp
@email varchar(30) = null,
@nome varchar(30) = null,
@hab varchar(30) = null,
@pontos int = null
as
begin
declare @id int;
declare @antPont decimal;
select @id = id from L_Filho where nome = @nome;
if(@email is not null and @hab is not null and @pontos is not null and @nome is not null)
begin 
	if exists (select * from L_Filho where nome = @nome and idPai in (select id from L_Usuario where email = @email))
	begin
		if not exists(select * from L_Habilidade where nome = @hab and idFilho = @id)
			insert into L_Habilidade values(@id,@hab,@pontos)
		else begin
			select @antPont = porcentagem from L_Habilidade where nome = @hab and idFilho = @id
			update L_Habilidade set porcentagem = (@antPont+porcentagem) where nome = @hab and idFilho = @id
		end
	end	
end
	return 1
end


alter trigger tg_deletarFilhos on L_Usuario instead of delete 
as begin  
DECLARE
    @id  int;
 
    SELECT @id = id FROM DELETED

	delete from L_Filho where idPai = @id
	delete from L_Usuario where id = @id
end


select * from L_Usuario