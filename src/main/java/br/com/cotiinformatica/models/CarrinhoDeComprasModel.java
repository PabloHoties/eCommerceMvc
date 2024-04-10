package br.com.cotiinformatica.models;

import java.util.List;

import lombok.Data;

@Data
public class CarrinhoDeComprasModel {

	private Double valorTotal;
	private Integer quantidadeItens;
	private List<ItemCarrinhoModel> itens;
}
