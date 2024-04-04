<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
    
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
			<h4>Seja bem vindo a loja de produtos</h4>
			Selecione o produto para incluí-lo no seu carrinho de compras.
		</div>
		
		<div class="row">
			<c:forEach items="${produtos}" var="p">
				<div class="col-md-4">
					<div class="card m-2">
						<div class="card-body text-center">
							
							<h3>${p.nome}</h3>
							<h5>Preço: ${p.preco}</h5>
							
							<img src="data:image/jpeg;base64, ${p.foto}" width="240" class="m-3"/>
							
							<div class="mt-3 d-grid">
								<a href="/eCommerceMvc/detalhes-produto" class="btn btn-primary">
									Adicionar ao carrinho
								</a>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		

	
	</div>

</body>
</html>