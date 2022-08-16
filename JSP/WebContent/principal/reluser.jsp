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
														<h4 class="sub-title">Relatório Usuário</h4>

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
																	<button type="button" onclick="imprimirHtml();" class="btn waves-effect waves-light btn-primary btn-outline-primary">Imprir Relatório</button>
																	<button type="button" onclick="imprimirPDF();" class="btn waves-effect waves-light btn-inverse btn-outline-inverse" >Imprimir PDF</button>
																	<button type="button" onclick="imprimirExcel();" class="btn waves-effect waves-light btn-success btn-outline-success" >Imprimir Excel</button>
																	
																</div>
															</div>

														</form>


														<!-- tabela de lista dos Usarios  -->
														<div style="height: 300px; overflow: scroll;">
															<table class="table" id="tabelaresultadosviews">
																<thead>
																	<tr>
																		<th scope="col">ID</th>
																		<th scope="col">Nome</th>
																	</tr>
																</thead> 
																<tbody>
																	<c:forEach items="${ listarUser}" var="ml">
																		<tr>
																			<td><c:out value="${ml.id}"></c:out></td>
																			<td><c:out value="${ml.nome}"></c:out></td>
																		</tr>

																		<!-- para lista o telefone -->
																		<c:forEach items="${ml.telefones}" var="fone">

																			<tr>
																				<td />
																				<td style="font-size: 9px;"><c:out value="${fone.numero}"></c:out></td>
																			</tr>	
																		</c:forEach>

																	</c:forEach>

																</tbody>
															</table>
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
	
	<script type="text/javascript">
	
			//funcao para imprimir o relatorio na tela 
			function imprimirHtml() {
				document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioUser';
				$("#formUser").submit();
			}
			
			//funcao para imprimir o relatorio em pdf
			function imprimirPDF() {
				document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioPDF';
				$("#formUser").submit();
				return false;
			}
			
			//funcao para imprimir o relatorio em pdf
			function imprimirExcel() {
				document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioExcel';
				$("#formUser").submit();
				return false;
			}
	
			// funcao para traduzir o calendario 
			$( function() {
			  
			  $("#dataInicial").datepicker({
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
			
			// funcao para traduzir o calendario 
			$( function() {
			  
			  $("#dataFinal").datepicker({
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
	
	</script>

</body>

</html>
