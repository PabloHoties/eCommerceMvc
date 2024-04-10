<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>eCommerce</title>
<!-- folha de estilos CSS do bootstrap -->
<link rel="stylesheet" href="resources/css/bootstrap.min.css"/>
</head>
<body>
	<!-- menu do sistema -->
	<jsp:include page="/WEB-INF/views/components/menu.jsp"/>
	<!-- área principal da página -->
	<div class="container">
		
		<div class="mt-3">
			<h4>Detalhes do produto selecionado.</h4>
			Adicione o produto em seu carrinho de compras.
		</div>
		
		<div class="row mt-3 mb-5">
			<div class="col-md-6 text-center">
				<img src="data:image/jpeg;base64,${produto.foto}" class="m-3 img-fluid"/>
			</div>
			<div class="col-md-6">
				
				<h3>${produto.nome}</h3>
				<h5>Preço: <fmt:formatNumber value="${produto.preco}" type="currency"/> </h5>
				
				<hr/>
				
				<form method="post" action="/eCommerceMvc/adicionar-produto">
				
					<!-- campo oculto -->
					<input type="hidden" name="idProduto" value="${produto.id}"/>
				
					<label>Selecione a quantidade do produto:</label>
					<input name="quantidade" type="number" min="1" max="100" value="1" class="form-control mt-2 mb-2" required/>
					
					<div class="d-grid mb-3">
						<input type="submit" class="btn btn-primary btn-lg" value="Comprar"/>
					</div>
					
					<div class="d-grid mb-3">
						<a href="/eCommerceMvc/" class="btn btn-light btn-lg">Voltar para a loja</a>
					</div>
				
				</form>
				
				
			</div>
			<div class="col-md-4">
			
			</div>
		</div>
		
	</div>
</body>
</html>
