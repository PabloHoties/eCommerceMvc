package br.com.cotiinformatica.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.cotiinformatica.models.CarrinhoDeComprasModel;
import br.com.cotiinformatica.models.ItemCarrinhoModel;
import br.com.cotiinformatica.models.LojaProdutosModel;

@Controller
public class CarrinhoDeComprasController {

	private String endpoint = "http://localhost:8083/api/produtos/obter";
	private String session = "carrinho";

	@RequestMapping(value = "/carrinho-de-compras")
	public ModelAndView carrinhoDeCompras() {
		// definir a página JSP que será exibida pelo controlador
		// WEB-INF/views/carrinho-de-compras.jsp
		ModelAndView modelAndView = new ModelAndView("carrinho-de-compras");
		return modelAndView;
	}

	@RequestMapping(value = "/adicionar-produto", method = RequestMethod.POST)
	public ModelAndView adicionarProduto(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("carrinho-de-compras");

		try {

			String idProduto = request.getParameter("idProduto");
			Integer quantidade = Integer.parseInt(request.getParameter("quantidade"));

			// Consultar o produto na API através do ID..
			RestTemplate restTemplate = new RestTemplate();
			String dados = restTemplate.getForObject(endpoint + "/" + idProduto, String.class);

			// Desserializando o JSON retornado para um objeto de produto
			Gson gson = new Gson();
			Type productListType = new TypeToken<LojaProdutosModel>() {
			}.getType();
			LojaProdutosModel produto = gson.fromJson(dados, productListType);

			// Criando um item do carrinho de compras
			ItemCarrinhoModel itemCarrinho = new ItemCarrinhoModel();
			itemCarrinho.setIdProduto(UUID.fromString(produto.getId()));
			itemCarrinho.setNomeProduto(produto.getNome());
			itemCarrinho.setPrecoProduto(Double.parseDouble(produto.getPreco()));
			itemCarrinho.setQuantidadeProduto(quantidade);
			itemCarrinho.setTotalProduto(quantidade * itemCarrinho.getPrecoProduto());
			itemCarrinho.setFoto(produto.getFoto());

			// Criando um carrinho de compras
			CarrinhoDeComprasModel model = new CarrinhoDeComprasModel();
			model.setItens(new ArrayList<ItemCarrinhoModel>());

			// Verificar se já existe um carrinho de compras em sessão
			if (request.getSession().getAttribute(session) != null) {
				model = (CarrinhoDeComprasModel) request.getSession().getAttribute(session);
			}

			// Adicionando o item no carrinho
			if (model.getValorTotal() == null)
				model.setValorTotal(0.0);
			if (model.getQuantidadeItens() == null)
				model.setQuantidadeItens(0);

			// Verificar se o item adicionado já existe no carrinho de compras
			ItemCarrinhoModel item = null;
			for (ItemCarrinhoModel i : model.getItens()) {
				if (i.getIdProduto().equals(itemCarrinho.getIdProduto())) {
					item = i;
					break;
				}
			}

			if (item != null) {
				item.setQuantidadeProduto(item.getQuantidadeProduto() + itemCarrinho.getQuantidadeProduto());
				item.setTotalProduto(item.getTotalProduto() + itemCarrinho.getTotalProduto());
			} else {
				model.getItens().add(itemCarrinho);
			}

			model.setValorTotal(model.getValorTotal() + itemCarrinho.getTotalProduto());
			model.setQuantidadeItens(model.getQuantidadeItens() + itemCarrinho.getQuantidadeProduto());

			// Salvar os dados do carrinho de compras em uma sessão
			request.getSession().setAttribute(session, model);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return modelAndView;
	}

	@RequestMapping(value = "/aumentar-quantidade-item")
	public ModelAndView aumentarQuantidadeItem(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("carrinho-de-compras");

		CarrinhoDeComprasModel model = (CarrinhoDeComprasModel) request.getSession().getAttribute(session);
		UUID id = UUID.fromString(request.getParameter("id"));

		for (ItemCarrinhoModel item : model.getItens()) {
			if (id.equals(item.getIdProduto())) {
				item.setQuantidadeProduto(item.getQuantidadeProduto() + 1);
				item.setTotalProduto(item.getQuantidadeProduto() * item.getPrecoProduto());
				model.setQuantidadeItens(model.getQuantidadeItens() + 1);
				model.setValorTotal(model.getValorTotal() + item.getPrecoProduto());

				request.getSession().setAttribute(session, model);
				break;
			}
		}

		return modelAndView;
	}

	@RequestMapping(value = "/diminuir-quantidade-item")
	public ModelAndView diminuirQuantidadeItem(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("carrinho-de-compras");

		CarrinhoDeComprasModel model = (CarrinhoDeComprasModel) request.getSession().getAttribute(session);
		UUID id = UUID.fromString(request.getParameter("id"));

		for (ItemCarrinhoModel item : model.getItens()) {
			if (id.equals(item.getIdProduto()) && item.getQuantidadeProduto() > 1) {
				item.setQuantidadeProduto(item.getQuantidadeProduto() - 1);
				item.setTotalProduto(item.getQuantidadeProduto() * item.getPrecoProduto());
				model.setQuantidadeItens(model.getQuantidadeItens() - 1);
				model.setValorTotal(model.getValorTotal() - item.getPrecoProduto());

				request.getSession().setAttribute(session, model);
				break;
			}
		}

		return modelAndView;
	}

	@RequestMapping(value = "/remover-item")
	public ModelAndView removerItem(HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("carrinho-de-compras");
		
		CarrinhoDeComprasModel model = (CarrinhoDeComprasModel) request.getSession().getAttribute(session);
		UUID id = UUID.fromString(request.getParameter("id"));
		
		for(ItemCarrinhoModel item : model.getItens()) {
			if(id.equals(item.getIdProduto())) {		
				
				model.setQuantidadeItens(model.getQuantidadeItens() - item.getQuantidadeProduto());
				model.setValorTotal(model.getValorTotal() - item.getTotalProduto());
				
				model.getItens().remove(item);				
				
				if(model.getItens().size() > 0) {
					request.getSession().setAttribute(session, model);
				} else {
					request.getSession().removeAttribute(session);
				}
				
				break;
			}
		}
		
		return modelAndView;
	}

	@RequestMapping(value = "/limpar-carrinho")
	public ModelAndView limparCarrinho(HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("carrinho-de-compras");
		request.getSession().removeAttribute(session);
		
		return modelAndView;
	}
}
