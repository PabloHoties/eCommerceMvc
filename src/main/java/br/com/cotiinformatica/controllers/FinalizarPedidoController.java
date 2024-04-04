package br.com.cotiinformatica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FinalizarPedidoController {

	@RequestMapping(value = "/finalizar-pedido")
	public ModelAndView finalizarPedido() {
		// Definir a página que será exibida pelo controlador
		// WEB-INF/views/finalizar-pedido.jsp
		ModelAndView modelAndView = new ModelAndView("finalizar-pedido");
		return modelAndView;
	}
}
