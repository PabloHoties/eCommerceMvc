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
	
	@RequestMapping(value = "/carrinho-de-compras")
	public ModelAndView carrinhoDeCompras() {
		//definir a página JSP que será exibida pelo controlador
		//WEB-INF/views/carrinho-de-compras.jsp
		ModelAndView modelAndView = new ModelAndView("carrinho-de-compras");
		return modelAndView;
	}
	
	@RequestMapping(value = "/adicionar-produto", method = RequestMethod.POST)
	public ModelAndView adicionarProduto(HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("carrinho-de-compras");
		
		try {
			
			String idProduto = request.getParameter("idProduto");
			Integer quantidade = Integer.parseInt(request.getParameter("quantidade"));
			
			//consultar o produto na API através do ID..
			RestTemplate restTemplate = new RestTemplate();
			String dados = restTemplate.getForObject(endpoint + "/" + idProduto, String.class);
			
			// Desserializando o JSON retornado para um objeto de produto
           Gson gson = new Gson();
           Type productListType = new TypeToken<LojaProdutosModel>() {}.getType();
           LojaProdutosModel produto = gson.fromJson(dados, productListType);
			
           //criando um item do carrinho de compras
           ItemCarrinhoModel itemCarrinho = new ItemCarrinhoModel();
           itemCarrinho.setIdProduto(UUID.fromString(produto.getId()));
           itemCarrinho.setNomeProduto(produto.getNome());
           itemCarrinho.setPrecoProduto(Double.parseDouble(produto.getPreco()));
           itemCarrinho.setQuantidadeProduto(quantidade);
           itemCarrinho.setTotalProduto(quantidade * itemCarrinho.getPrecoProduto());
           itemCarrinho.setFoto(produto.getFoto());
          
           //criando um carrinho de compras com este produto..
           CarrinhoDeComprasModel model = new CarrinhoDeComprasModel();
           model.setValorTotal(itemCarrinho.getTotalProduto());
           model.setQuantidadeItens(itemCarrinho.getQuantidadeProduto());
           model.setItens(new ArrayList<ItemCarrinhoModel>());
           model.getItens().add(itemCarrinho);
          
           //salvar os dados do carrinho de compras em uma sessão
           request.getSession().setAttribute("carrinho", model);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}
}
