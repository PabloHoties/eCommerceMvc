package br.com.cotiinformatica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CadastroClienteController {

	@RequestMapping(value = "/cadastro-cliente")
	public ModelAndView cadastroCliente() {
		// Definir a página que será exibida pelo controlador
		// WEB-INF/views/cadastro-cliente.jsp
		ModelAndView modelAndView = new ModelAndView("cadastro-cliente");
		return modelAndView;
	}
}
