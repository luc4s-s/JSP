<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>
<html lang="en">

<!-- head da pagina -->
<jsp:include page="head.jsp"></jsp:include>



<body> 
	<!-- Pre-loader start theme-loader.jsp-->
	<jsp:include page="theme-loader.jsp"></jsp:include>

	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<!-- navbar -->
			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<!-- navbarmainmenu -->
					<jsp:include page="navbarmainmenu.jsp"></jsp:include>

					<div class="pcoded-content">
						<!-- Page-header start -->

						<!-- page-header -->
						<jsp:include page="page-header.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">

										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">

													<div class="card-block">
														<h4 class="sub-title">Cadastro de Usúario</h4>

														<form class="form-material" enctype="multipart/form-data" action="<%= request.getContextPath() %>/ServletUsuarioController" method="post" id="formUser">
															
															<!-- vai ser enviado junto com o formulario para excluir -->
															<input type="hidden" name="acao" id="acao" value="">
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}"> 
																<span class="form-bar"></span> 
																<label class="float-label">ID:</label>
															</div>

															<div class="form-group form-default input-group mb-4">
																<div class="input-group-prepend">
																<c:if test="${modelLogin.fotouser != '' && modelLogin.fotouser != null}">
																<a href="<%= request.getContextPath()%>/ServletUsuarioController?acao=downloadFoto&id=${modelLogin.id}">
																	<img alt="Imagem User" id="fotoembase64" src="${modelLogin.fotouser}" width="70px;">
																</a>
																</c:if>
																
																<c:if test="${modelLogin.fotouser == '' || modelLogin.fotouser == null }">
																	<img alt="Imagem User" id="fotoembase64" src="assets/images/user.png" width="70px;">
																</c:if>
																
																</div>
																<input type="file" id="fileFoto" name="fileFoto" accept="image/*" onchange="visualizarImg('fotoembase64', 'fileFoto');" class="form-control-file" style="margin-top: 15px; margin-left: 5px;">
															</div>
																								

																<div class="form-group form-default form-static-label">
																	<input type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}"> 
																	<span class="form-bar"></span> 
																	<label class="float-label">Nome:</label>
																</div>
																
																<div class="form-group form-default form-static-label">
																	<input type="text" name="dataNascimento" id="dataNascimento" class="form-control" required="required" value="${modelLogin.dataNascimento}"> 
																	<span class="form-bar"></span> 
																	<label class="float-label">Data Nascimento:</label>
																</div>
																
																<div class="form-group form-default form-static-label">
																	<input type="text" name="rendamensal" id="rendamensal" class="form-control" required="required" value="${modelLogin.rendamensal}"> 
																	<span class="form-bar"></span> 
																	<label class="float-label">Renda Mensal:</label>
																</div>

																<div class="form-group form-default form-static-label">
																	<input type="email" name="email" id="email"
																		class="form-control" required="required"
																		autocomplete="off" value="${modelLogin.email}">
																	<span class="form-bar"></span> <label
																		class="float-label">E-mail:</label>
																</div>
																<div class="form-group form-default form-static-label">
																	<select class="form-control"
																		aria-label="Default select example" name="perfil">
																		<option disabled="disabled">[selecione o
																			Perfil]</option>

																		<option value="ADMIN"
																			<% 
																ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																
																if (modelLogin != null && modelLogin.getPerfil().equals("ADMIN")){
																		out.print(" ");
																		out.print("selected=\"selected\"");
																		out.print(" ");
																}%>>Admin</option>


																		<option value="SECRETARIA"
																			<% 
																modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																
																if (modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")){
																		out.print(" ");
																		out.print("selected=\"selected\"");
																		out.print(" ");
																}%>>Secretária</option>

																		<option value="AUXILIAR"
																			<% 
 																modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																
																if (modelLogin != null && modelLogin.getPerfil().equals("AUXILIAR")){
																		out.print(" ");
																		out.print("selected=\"selected\"");
																		out.print(" ");
																}%>>Auxiliar</option>
																	</select> <span class="form-bar"></span> <label
																		class="float-label">Perfil:</label>
																</div>
																
																<div class="form-group form-default form-static-label">
																	<input onblur="pesquisaCep();" type="text" name="cep" id="cep"class="form-control" required="required" autocomplete="off" value="${modelLogin.cep}">
																	<span class="form-bar"></span> 
																	<label class="float-label">Cep:</label>
																</div>
																
																<div class="form-group form-default form-static-label">
																	<input type="text" name="logradouro" id="logradouro"class="form-control" required="required" autocomplete="off" value="${modelLogin.logradouro}">
																	<span class="form-bar"></span> 
																	<label class="float-label">Logradouro:</label>
																</div>
																
																<div class="form-group form-default form-static-label">
																	<input type="text" name="bairro" id="bairro"class="form-control" required="required" autocomplete="off" value="${modelLogin.bairro}">
																	<span class="form-bar"></span> 
																	<label class="float-label">Bairro:</label>
																</div>
																
																<div class="form-group form-default form-static-label">
																	<input type="text" name="localidade" id="localidade"class="form-control" required="required" autocomplete="off" value="${modelLogin.localidade}">
																	<span class="form-bar"></span> 
																	<label class="float-label">Localidade:</label>
																</div>
																
																<div class="form-group form-default form-static-label">
																	<input type="text" name="uf" id="uf"class="form-control" required="required" autocomplete="off" value="${modelLogin.uf}">
																	<span class="form-bar"></span> 
																	<label class="float-label">Estado:</label>
																</div>
																
																<div class="form-group form-default form-static-label">
																	<input type="text" name="numero" id="numero"class="form-control" required="required" autocomplete="off" value="${modelLogin.numero}">
																	<span class="form-bar"></span> 
																	<label class="float-label">Numero:</label>
																</div>
																

																<div class="form-group form-default form-static-label">
																	<input type="text" name="login" id="login"class="form-control" required="required" autocomplete="off" value="${modelLogin.login}">
																	<span class="form-bar"></span> 
																	<label class="float-label">Login:</label>
																</div>

																<div class="form-group form-default form-static-label">
																	<input type="password" name="senha" id="senha"
																		class="form-control" required="required"
																		autocomplete="off" value="${modelLogin.senha}">
																	<span class="form-bar"></span> <label
																		class="float-label">Senha:</label>
																</div>

																<div class="form-group form-default form-static-label">
																	<input type="radio" name="sexo" checked="checked"
																		value="MASCULINO"
																		<% 
																modelLogin = (ModelLogin) request.getAttribute("modelLogin");
															
																	if (modelLogin != null && modelLogin.getSexo().equals("MASCULINO")){
																			out.print(" ");
																			out.print("checked=\"checked\"");
																			out.print(" ");
																	}%>>Masculino</>


																	<input type="radio" name="sexo" value="FEMENINO"
																		<% 
																modelLogin = (ModelLogin) request.getAttribute("modelLogin");
															
																	if (modelLogin != null && modelLogin.getSexo().equals("FEMENINO")){
																			out.print(" ");
																			out.print("checked=\"checked\"");
																			out.print(" ");
																	}%>>Femenino</>

																</div>


																<button type="button" class="btn waves-effect waves-light btn-grd-success"onclick="limparForm();">Novo</button>
																<button class="btn waves-effect waves-light btn-grd-info ">Salvar</button>
																<button type="button" class="btn waves-effect waves-light btn-grd-danger "onclick="criarDeleteComAjax();">Excluir</button>
																
																<c:if test="${modelLogin.id > 0}"> <!-- verifica se tem usuario em edicao -->
																	<a href="<%= request.getContextPath() %>/ServletTelefone?iduser=${modelLogin.id}" class="btn waves-effect waves-light btn-grd-warning ">Telefone</a>
																</c:if> 
																<button type="button" class="btn waves-effect waves-light btn-grd-inverse "data-toggle="modal" data-target="#exampleModalUsuario">Pesquisar</button>
														</form>
													</div>
												</div>
											</div>
										</div>
											<span id="msg" style="color:green;">${msg}</span>

										<!-- tabela de lista dos Usarios  -->
										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="tabelaresultadosviews">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${ modelLogins}" var="ml">
														<tr>
															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td><a class="btn waves-effect waves-light btn-grd-inverse" href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a></td>
														</tr>
													</c:forEach>

												</tbody>
											</table>
										</div>


										<!-- fazendo a paginação da pagina de cadastro de usuario -->
										<nav aria-label="Navegação de página exemplo">
											<ul class="pagination">
																<% 
										int totalPagina = (int) request.getAttribute("totalPagina");
										
										for(int p = 0; p < totalPagina; p++){
											String url = request.getContextPath() + "/ServletUsuarioController?acao=paginar&pagina=" + (p * 5);
											out.print("<li class=\"page-item\"><a class=\"page-link\" href=\""+ url +"\">"+(p + 1)+"</a></li>");
										}
										
										
										%>
										
											</ul>
										</nav>

									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- pagina JavaScript-->
	<jsp:include page="javascriptfile.jsp"></jsp:include>
	
	
	<!-- Modal de Pesquisa-->
<div class="modal fade" id="exampleModalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de usuário</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      
						<div class="input-group mb-3">
							<input type="text" class="form-control" placeholder="Nome" aria-label="Nome" id="nomeBusca" aria-describedby="basic-addon2">
							<div class="input-group-append">
								<button class="btn waves-effect waves-light btn-grd-inverse" type="button" onclick="buscarUsuario();">Pesquisar</button>
							</div>
						</div>
						
						
						<!-- tabela de pesquisa do modal -->
					<div style="height: 300px; overflow: scroll;">
					<table class="table" id="tabelaresultados">
						<thead>
							<tr>
								<th scope="col">ID</th>
								<th scope="col">Nome</th>
								<th scope="col">Ver</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
				
				<!-- paginaçao do modal -->
					<nav aria-label="Navegação de página exemplo">
						<ul class="pagination" id="ulPaginacaoUserAjax">
						</ul>
					</nav>

					<span id="totalResultados" style="color: red;"></span>
				</div>
				
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
      </div>
    </div>
  </div>
</div>
	
	
	<script type="text/javascript">
	
	//evento para renda mensal conversao de moeda
	$("#rendamensal").maskMoney({showSymbol:true, symbol: "R$ ", decimal:",", thousands:"."});
	
	//constante para a formataçao do monetario dinheiro renda mensal
	const formatter = new Intl.NumberFormat('pt-BR', {
		currency : 'BRL',
		minimumFractionDigits : 2
	});
	
	//evento para formata o valor que ja esta na tela codigo que formata de dentro para fora
	$("#rendamensal").val(formatter.format($("#rendamensal").val()));
	
	//evento para da um foco no campo de renda mensal
	$("#rendamensal").focus();
	
	
	//pegando o valor da dataNascimento da tela 
	var dataNascimento = $("#dataNascimento").val();
	
	if(dataNascimento != null && dataNascimento != ''){
	
		var dateFormat = new Date(dataNascimento);//formatando a data em um novo formato
		
		$("#dataNascimento").val(dateFormat.toLocaleDateString('pt-BR',{timeZone: 'UTC'}));//voltando o valor para o canpo de data
	
	}
	
	$("#nome").focus();//focando no campo nome para ediçao
	
	
	// funcao para traduzir o calendario 
	$( function() {
	  
	  $("#dataNascimento").datepicker({
		    dateFormat: 'dd/mm/yy',
		    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
		    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
		    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
		    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
		    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
		    nextText: 'Próximo',
		    prevText: 'Anterior'
		});
} );
	
	
	//evento que executa uma funcao para permitir apenas numeros
	$("#numero").keypress(function (event) {
		return /\d/.test(String.fromCharCode(event.keyCode));
	});
	
	$("#cep").keypress(function (event) {
		return /\d/.test(String.fromCharCode(event.keyCode));
	});
	
	
	//funcao para carregar o cep no campo
	function pesquisaCep() { 
		var cep = $("#cep").val();
		
		$.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) { 
			//Atualiza os campos com os valores da consulta.
             $("#cep").val(dados.cep);
			$("#logradouro").val(dados.logradouro);
            $("#bairro").val(dados.bairro);
            $("#localidade").val(dados.localidade);
            $("#uf").val(dados.uf);
            $("#numero").val(dados.numero);
			
		})
	}
	
	//funcao para vizualizar imagem
	function visualizarImg(fotoembase64, filefoto) {
		
		var preview = document.getElementById(fotoembase64);//campo img html
		var fileUser = document.getElementById(filefoto).files[0];
		var reader = new FileReader();
		
		reader.onloadend = function(){ 
			preview.src = reader.result; //carrega a foto na tela	
		};
		
		if(fileUser){
			reader.readAsDataURL(fileUser); //preview da imagem
		}else{
			preview.src = '';
		}
		
	}
	
	//Funcao para ver ou editar usuario 
	function verEditar(id){
		
		var urlAction = document.getElementById('formUser').action;
		
		window.location.href = urlAction + '?acao=buscarEditar&id='+id;
		
	}
	
	//busca usuario do modal paginacao sem recaregar a pagian
	function buscarUserPageAjax(url){
		
		var urlAction = document.getElementById('formUser').action;
		var nomeBusca = document.getElementById('nomeBusca').value;
		
		$.ajax({

			method : "get",
			url : urlAction,
			data : url,
			success : function(response, textStatus, xhr) {
				
				var json = JSON.parse(response); 
				
				$('#tabelaresultados > tbody > tr').remove();
				$("#ulPaginacaoUserAjax > li").remove();
				
				for(var p = 0; p < json.length; p++){
				 $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].id+'</td> <td>'+json[p].nome+'</td> <td><button onclick="verEditar('+json[p].id+')" type="button" class="btn waves-effect waves-light btn-grd-inverse">Ver</button></td> </tr>');
				}
				
				document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
				var totalPagina = xhr.getResponseHeader("totalPagina");
				
				
				for(var p = 0; p < totalPagina; p++){
					
					var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina=' + (p * 5);
					
					//montando a paginaçao
					$("#ulPaginacaoUserAjax").append('<li class="page-intem"><a class="page-link" href="#" onclick="buscarUserPageAjax(\''+url+'\')">'+ (p + 1) +'</a><li>');
				}
			}

		}).fail(function(xhr, status, errorThrown) {
				alert('Erro ao buscar usuario:'+ xhr.respopnseText);
		});
	}
	
	
	//funcao para buscar usuario e listar no modal 
	function buscarUsuario(){
		var nomeBusca = document.getElementById('nomeBusca').value;
		
		if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') {//validando que tem que ter valor para buscar no banco.
		var urlAction = document.getElementById('formUser').action;
	
		$.ajax({

					method : "get",
					url : urlAction,
					data : "nomeBusca=" + nomeBusca + '&acao=buscarUserajax',
					success : function(response, textStatus, xhr) {
						
						var json = JSON.parse(response); 
						
						$('#tabelaresultados > tbody > tr').remove();
						$("#ulPaginacaoUserAjax > li").remove();
						
						for(var p = 0; p < json.length; p++){
						 $('#tabelaresultados > tbody').append('<tr> <td>'+json[p].id+'</td> <td>'+json[p].nome+'</td> <td><button onclick="verEditar('+json[p].id+')" type="button" class="btn waves-effect waves-light btn-grd-inverse">Ver</button></td> </tr>');
						}
						
						document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
						var totalPagina = xhr.getResponseHeader("totalPagina");
						
						
						for(var p = 0; p < totalPagina; p++){
							var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina=' + (p * 5);
							
							//montando a paginaçao
							$("#ulPaginacaoUserAjax").append('<li class="page-intem"><a class="page-link" href="#" onclick="buscarUserPageAjax(\''+url+'\')">'+ (p + 1) +'</a><li>');
						}
					}

				}).fail(function(xhr, status, errorThrown) {
						alert('Erro ao buscar usuario:'+ xhr.respopnseText);
				});
		

			}
		}

		//metodo de exluir com ajax
		function criarDeleteComAjax() {
			if (confirm('Deseja realmente Excluir os Dados?')) {
				
				var urlAction = document.getElementById('formUser').action;
				var idUser = document.getElementById('id').value;

				$.ajax({

					method : "get",
					url : urlAction,
					data : "id=" + idUser + '&acao=deletarajax',
					success : function(response) {
						limparForm();
						document.getElementById('msg').textContent = response;
					}

				}).fail(
						function(xhr, status, errorThrown) {
							alert('Erro ao deletar usuário por id:'+ xhr.respopnseText);
						});
			}

		}

		//metodo de exluir sem ajax
		function criarDelete() {

			if (confirm("Deseja realmente Excluir os Dados?")) {
				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
		}

		//script para limpar o formulario
		function limparForm() {
			var elementos = document.getElementById("formUser").elements;//retorna os elementos html dentro do form

			for (p = 0; p < elementos.length; p++) {
				elementos[p].value = '';
			}
		}
	</script>
	
</body>

</html>
