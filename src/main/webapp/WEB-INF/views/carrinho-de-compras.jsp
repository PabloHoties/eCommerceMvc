<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>eCommerce</title>
<!-- folha de estilos CSS do bootstrap -->
<link rel="stylesheet" href="resources/css/bootstrap.min.css" />
</head>
<body>
	<!-- menu do sistema -->
	<jsp:include page="/WEB-INF/views/components/menu.jsp" />
	<!-- área principal da página -->
	<div class="container">
		<div class="mt-3">
			<h4>Carrinho de compras.</h4>
			Listagem de produtos adicionados no seu carrinho.
		</div>
		<c:choose>
			<c:when test="${not empty carrinho}">
				<div class="mb-3 mt-3">
					<h4>
						Total do carrinho:
						<fmt:formatNumber value="${carrinho.valorTotal}" type="currency" />
					</h4>
					Quantidade de itens do carrinho: ${carrinho.quantidadeItens}
				</div>
				<div class="table-responsive">
					<table class="table table-sm table-hover">
						<thead>
							<tr>
								<th>Foto</th>
								<th>Nome do produto</th>
								<th>Preço unitário</th>
								<th>Quantidade</th>
								<th>Total</th>
								<th>Operações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${carrinho.itens}" var="p">
								<tr>
									<td><img src="data:image/jpeg;base64,${p.foto}"
										height="60" /></td>
									<td>${p.nomeProduto}</td>
									<td><fmt:formatNumber value="${p.precoProduto}"
											type="currency" /></td>
									<td>${p.quantidadeProduto}</td>
									<td><fmt:formatNumber value="${p.totalProduto}"
											type="currency" /></td>
									<td><a
										href="/eCommerceMvc/aumentar-quantidade-item?id=${p.idProduto}"
										class="btn btn-outline-primary me-1 btn-sm"> + </a> <a
										href="/eCommerceMvc/diminuir-quantidade-item?id=${p.idProduto}"
										class="btn btn-outline-danger me-1 btn-sm"> - </a> <a
										href="/eCommerceMvc/remover-item?id=${p.idProduto}"
										class="btn btn-outline-danger btn-sm"> Remover </a></td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="6">Quantidade de produtos:
									${carrinho.itens.size()}</td>
							</tr>
						</tfoot>
					</table>
				</div>
				<div class="mt-3 text-end">
					<a href="/eCommerceMvc/limpar-carrinho"
						onclick="return confirm('Deseja realmente apagar todos os itens do seu carrinho?');"
						class="btn btn-outline-danger me-2"> Limpar carrinho </a> <a
						href="#" class="btn btn-success"> Finalizar Pedido </a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-warning mt-3">Não há itens no seu carrinho
					de compras.</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>