
create procedure acesso_sp
@id int = null
as
if(@id is not null)
	insert into L_Acesso values(GETDATE(), @id)