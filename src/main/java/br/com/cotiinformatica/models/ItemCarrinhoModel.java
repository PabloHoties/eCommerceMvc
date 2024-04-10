package br.com.cotiinformatica.models;

import java.util.UUID;

import lombok.Data;

@Data
public class ItemCarrinhoModel {

	private UUID idProduto;
	private String nomeProduto;
	private Double precoProduto;
	private Integer quantidadeProduto;
	private Double totalProduto;
	private String foto;
}
