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
														<h4 class="sub-title">Gráfico</h4>

														<form class="form-material"  action="<%=request.getContextPath()%>/ServletUsuarioController" method="get" id="formUser">
												
															<input type="hidden" id="acaoRelatorioImprimirTipo" name="acao" value="imprimirRelatorioUser">
																
															<div class="form-row align-items-center">
															
																<div class="col-sm-3 my-1">
																	<label class="sr-only" for="dataInicial">Data Inicial</label>
																	<input value="${dataInicial}" type="text" class="form-control" id="dataInicial"  name="dataInicial">
																</div>
																
																<div class="col-sm-3 my-1">
																	<label class="sr-only" for="dataFinal">Data Final</label>
																	 <input value="${dataFinal}" type="text" class="form-control" id="dataFinal" name="dataFinal">
																</div>
																
																<div class="col-auto my-1 ">
																	<button type="button" onclick="gerarGrafico();" class="btn waves-effect waves-light btn-warning btn-outline-warning">Gerar Gráfico</button>
																</div>
															</div>

														</form>


														<!-- GRÁFICO -->
														<div style="height: 500px; overflow: scroll;" >
															
															<!-- myChart vai montar o grafico -->
															<div>
																<canvas id="myChart"></canvas>
															</div>



														</div>

													</div>
												</div>
											</div>
										</div>

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
	
	<!-- declaraçao script para invocar o chartJS e gerar o grafico-->
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	
	
	<script type="text/javascript">
	
	
		//so esta criando o grafico
		var myChart = new Chart(document.getElementById('myChart'));

		
		//funcao para gerar o grafico
		function gerarGrafico() {

			//ajax para buscar os dados para o grafico
			var urlAction = document.getElementById('formUser').action;
			var dataInicial = document.getElementById('dataInicial').value;
			var dataFinal = document.getElementById('dataFinal').value;

			$
					.ajax(
							{

								method : "get",
								url : urlAction,//url da servlet
								data : "dataInicial=" + dataInicial
										+ '&dataFinal=' + dataFinal
										+ '&acao=graficoSalario',
								success : function(response) {

									var json = JSON.parse(response);//convertendo o JSON
									
									myChart.destroy();//vai montar o grafico

									//responsalvel por chamar o myChart
									myChart = new Chart(
											document.getElementById('myChart'),
											{
												type : 'line',
												data : {
													labels : json.perfils,//traz do banco os perfils 
													datasets : [ {
														label : 'Gráfico de média salarial por tipo',
														backgroundColor : 'rgb(255, 99, 132)', //muda a cor geral
														borderColor : 'rgb(255, 99, 132)', //muda a cor da linha do grafico
														data : json.salarios,//traz do banco a media dos salario
													} ]
												},
												options : {}
											}

									);

								}

							}).fail(
							function(xhr, status, errorThrown) {
								alert('Erro ao buscar dados para o Gráfico:'
										+ xhr.respopnseText);
							});
		}

		// funcao para traduzir o calendario 
		$(function() {

			$("#dataInicial")
					.datepicker(
							{
								dateFormat : 'dd/mm/yy',
								dayNames : [ 'Domingo', 'Segunda', 'Terça',
										'Quarta', 'Quinta', 'Sexta', 'Sábado' ],
								dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
										'S', 'D' ],
								dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
										'Qui', 'Sex', 'Sáb', 'Dom' ],
								monthNames : [ 'Janeiro', 'Fevereiro', 'Março',
										'Abril', 'Maio', 'Junho', 'Julho',
										'Agosto', 'Setembro', 'Outubro',
										'Novembro', 'Dezembro' ],
								monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
										'Mai', 'Jun', 'Jul', 'Ago', 'Set',
										'Out', 'Nov', 'Dez' ],
								nextText : 'Próximo',
								prevText : 'Anterior'
							});
		});

		// funcao para traduzir o calendario 
		$(function() {

			$("#dataFinal")
					.datepicker(
							{
								dateFormat : 'dd/mm/yy',
								dayNames : [ 'Domingo', 'Segunda', 'Terça',
										'Quarta', 'Quinta', 'Sexta', 'Sábado' ],
								dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
										'S', 'D' ],
								dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
										'Qui', 'Sex', 'Sáb', 'Dom' ],
								monthNames : [ 'Janeiro', 'Fevereiro', 'Março',
										'Abril', 'Maio', 'Junho', 'Julho',
										'Agosto', 'Setembro', 'Outubro',
										'Novembro', 'Dezembro' ],
								monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
										'Mai', 'Jun', 'Jul', 'Ago', 'Set',
										'Out', 'Nov', 'Dez' ],
								nextText : 'Próximo',
								prevText : 'Anterior'
							});
		});
	</script>

</body>

</html>
