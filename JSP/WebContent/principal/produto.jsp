<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
																	<input type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}"> 
																	<span class="form-bar"></span> 
																	<label class="float-label">Preço:</label>
																</div>
																
																<div class="form-group form-default form-static-label">
																	<input type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}"> 
																	<span class="form-bar"></span> 
																	<label class="float-label">Quantidade:</label>
																</div>
																
																<div class="form-group form-default form-static-label">
																	<input type="text" name="nome" id="nome" class="form-control" required="required" value="${modelLogin.nome}"> 
																	<span class="form-bar"></span> 
																	<label class="float-label">Descriçao:</label>
																</div>
																
																


																<button type="button" class="btn waves-effect waves-light btn-grd-success"onclick="limparForm();">Novo</button>
																<button class="btn waves-effect waves-light btn-grd-info ">Salvar</button>
																<button type="button" class="btn waves-effect waves-light btn-grd-danger "onclick="criarDeleteComAjax();">Excluir</button>
																<button type="button" class="btn waves-effect waves-light btn-grd-inverse "data-toggle="modal" data-target="#exampleModalUsuario">Pesquisar</button>
														</form>
													</div>
												</div>
											</div>
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
   
</body>

</html>
    