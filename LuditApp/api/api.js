const express = require('express');
const fs = require('fs');
const app = express();         
const bodyParser = require('body-parser');
const porta = 3000; //porta padrão
const sql = require('mssql');
const conexaoStr = "Server=regulus;Database=PR118189;User Id=PR118189;Password=gaguisa2019;";

//conexao com BD
sql.connect(conexaoStr)
   .then(conexao => global.conexao = conexao)
   .catch(erro => console.log(erro));

// configurando o body parser para pegar POSTS mais tarde   
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json({strict: false, extended: false}));
app.use(express.json());
//acrescentando informacoes de cabecalho para suportar o CORS
app.use(function(req, res, next) {
  res.header('Content-Type', 'application/json');
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  res.header("Access-Control-Allow-Methods", "GET, POST, HEAD, OPTIONS, PATCH, DELETE");
  next();
});
//definindo as rotas
const rota = express.Router();
rota.get('/', (requisicao, resposta) => resposta.json({ mensagem: 'Funcionando!'}));
app.use('/', rota);

//inicia servidor
app.listen(porta);
console.log('API Funcionando!');

function execSQL(sql, resposta) {
	global.conexao.request()
				  .query(sql)
				  .then(resultado => resposta.json(resultado.recordset))
				  .catch(erro => resposta.json(erro));
}

rota.post('/cadastrarUsuario', (requisicao, resposta) =>{
const nome = requisicao.body.nome;
const email = requisicao.body.email;
const senha = requisicao.body.senha;
execSQL(`cadastro_sp '${nome}','${email}','${senha}'`, resposta);
})

rota.post('/cadastrarFilho/:email', (requisicao, resposta) =>{
    const def = requisicao.body.def;
    const data = requisicao.body.data;
    const texto = requisicao.body.texto;
    const imgPerfil = requisicao.body.imgPerfil;
	const nome = requisicao.body.nome;
    execSQL(`cadastrarFilho_sp '${requisicao.params.email}','${nome}','${def}', '${data}', '${texto}', '${imgPerfil}'`, resposta);
    })


rota.post("/loginUsuario", (requisicao, resposta) => {
    const login = requisicao.body.login;
    const senha = requisicao.body.senha;    
    execSQL(`acesso_sp '${login}','${senha}'`, resposta);
})

rota.get("/habilidades/:email/:nome", (requisicao, resposta) =>{
    execSQL(`select nome, porcentagem from L_Habilidade where idFilho in (select id from L_Filho where nome = '${requisicao.params.nome}' and 
            idPai in (select id from L_Usuario where email = '${requisicao.params.email}'))`, resposta);	
}) 


rota.post("/getUser/:email", (requisicao, resposta) =>{
	const senha = requisicao.body.senha;
    execSQL(`select * from L_Usuario where email = '${requisicao.params.email}' and senha = '${senha}'`, resposta);	
}) 

rota.post("/habilidades/:email/:nome/:habilidade/:pontos", (requisicao, resposta)=>{
	execSQL("insereHabilidade_sp '"+ requisicao.params.email +"', '" + requisicao.params.nome +"', '"+ requisicao.params.habilidade 
			+"', "+ requisicao.params.pontos, resposta);
})

rota.patch("/alteraNome/:email", (requisicao, resposta) =>{
	const email = requisicao.params.email;
	const nome = requisicao.body;
	execSQL(`update L_Usuario set nome='${nome}' where email='${email}'`, resposta);
})

rota.patch("/alteraEmail/:email", (requisicao, resposta) =>{
	const email = requisicao.params.email;
	const novoEmail = requisicao.body;
    execSQL(`update L_Usuario set email='${novoEmail}' where email='${email}'`, resposta);
})

rota.patch("/alteraSenha/:email", (requisicao, resposta) =>{
	const email = requisicao.params.email;
	const novaSenha = requisicao.body;
    execSQL(`update L_Usuario set senha='${novaSenha}' where email='${email}'`, resposta);
})
/*
rota.post('/alterarPerfil/:email/:novaImagem', (requisicao, resposta) =>{
	const email = requisicao.params.email;
	const data = requisicao.body.dataImagem;
	let caminho = 'estilo/uploadsPerfil/' + requisicao.params.novaImagem;
	fs.writeFile(caminho, data, 'base64', (err)=>{
		if(err)
			console.log(err);
		caminho = 'nodeSql/' + caminho; 
		execSQL(`update Usuario set imgPerfil='${caminho}' where email='${email}'`, resposta);
	});
})*/

rota.delete("/excluiConta/:email", (requisicao, resposta)=>{
	const email = requisicao.params.email;
	execSQL(`delete from L_Usuario where email='${email}'`, resposta);
})


rota.get("/buscarFilhos/:email", (requisicao, resposta)=>{
	const email = requisicao.params.email;
	execSQL(`select * from L_Filho where idPai in (select id from L_Usuario where email = '${email}')`, resposta);
})
