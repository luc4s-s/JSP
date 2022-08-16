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
														<h4 class="sub-title">Cadastro de Telefone</h4>
														
															<form class="form-material"  action="<%= request.getContextPath() %>/ServletTelefone" method="post" id="formFone">
															
															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${modelLogin.id}"> 
																<span class="form-bar"></span> 
																<label class="float-label">ID User:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																 <input readonly="readonly" type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}"> 
																 <span class="form-bar"></span> 
																 <label class="float-label">Nome:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																 <input type="text" name="numero" id="numero" class="form-control" required="required" "> 
																 <span class="form-bar"></span> 
																 <label class="float-label">Numero:</label>
															</div>
															
															<button class="btn waves-effect waves-light btn-grd-info ">Salvar</button>
															
															</form>
														
													</div>
												</div>
											</div>
										</div>
										
										<span id="msg" style="color:green;">${msg}</span>

										<!-- tabela da lista dos Telefones  -->
										<div style="height: 300px; overflow: scroll;">
											<table class="table" id="tabelaresultadosviews">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Numero</th>
														<th scope="col">Excluir</th>
													</tr>
												</thead>
												<tbody>
												
													<c:forEach items='${modelTelefones}' var="f">
														<tr>
															<td><c:out value="${f.id}"></c:out></td>
															<td><c:out value="${f.numero}"></c:out></td>
															<td><a class="btn waves-effect waves-light btn-grd-inverse" href="<%= request.getContextPath() %>/ServletTelefone?acao=excluir&id=${f.id}&userpai=${modelLogin.id}">Excluir</a></td>
														</tr>
													</c:forEach>

												</tbody>
											</table>
										</div>

									</div>
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
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
 
 //evento que executa uma funcao para permitir apenas numeros 
 $("#numero").keypress(function (event) {
		return /\d/.test(String.fromCharCode(event.keyCode));
	});
 
 </script>
   
</body>

</html>
    