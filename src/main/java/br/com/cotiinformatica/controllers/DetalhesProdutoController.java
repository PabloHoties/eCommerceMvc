package br.com.cotiinformatica.controllers;

import java.lang.reflect.Type;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.cotiinformatica.models.LojaProdutosModel;

@Controller
public class DetalhesProdutoController {
	
	//atributo
	private String endpoint = "http://localhost:8083/api/produtos/obter";

	@RequestMapping(value = "/detalhes-produto")
	public ModelAndView detalhesProduto(String id) {
		// Definir a página que será exibida pelo controlador
		// WEB-INF/views/detalhes-produto.jsp
		ModelAndView modelAndView = new ModelAndView("detalhes-produto");
		
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			String dados = restTemplate.getForObject(endpoint + "/" + id, String.class);

			// Desserializando o JSON retornado para um objeto
			Gson gson = new Gson();
			Type productListType = new TypeToken<LojaProdutosModel>() {
			}.getType();
			LojaProdutosModel produto = gson.fromJson(dados, productListType);
			// Enviando os dados para a página
			modelAndView.addObject("produto", produto);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}
}
