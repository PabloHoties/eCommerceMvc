package br.com.cotiinformatica.controllers;

import java.lang.reflect.Type;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import br.com.cotiinformatica.models.LojaProdutosModel;

@Controller
public class LojaProdutosController {

	// Atributo
	private String endpoint = "http://localhost:8083/api/produtos/consultar";

	@RequestMapping(value = "/") // rota raiz do projeto
	public ModelAndView lojaProdutos() {

		// Definir a página JSP que será exibida pelo controlador
		// WEB-INF/views/loja-produtos.jsp
		ModelAndView modelAndView = new ModelAndView("loja-produtos");

		try {

			RestTemplate restTemplate = new RestTemplate();
			String dados = restTemplate.getForObject(endpoint, String.class);

			// Desserializando o JSON retornado para uma lista de objetos
			Gson gson = new Gson();
			Type productListType = new TypeToken<List<LojaProdutosModel>>() {
			}.getType();
			List<LojaProdutosModel> produtos = gson.fromJson(dados, productListType);
			// Enviando os dados para a página
			modelAndView.addObject("produtos", produtos);
		} catch (Exception e) {
			// Exibir o log de erro no servidor
			e.printStackTrace();
		}

		return modelAndView;
	}
}
