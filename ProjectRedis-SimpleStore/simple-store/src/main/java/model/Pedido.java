package model;

import java.util.ArrayList;

public class Pedido {

	int codigo;
	String data_pedido;
	String data_entrega;
	ArrayList<Item> itens;
	Cliente cliente;
	
	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getData_pedido() {
		return data_pedido;
	}

	public void setData_pedido(String data_pedido) {
		this.data_pedido = data_pedido;
	}

	public String getData_entrega() {
		return data_entrega;
	}

	public void setData_entrega(String data_entrega) {
		this.data_entrega = data_entrega;
	}

	public ArrayList<Item> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Item> itens) {
		this.itens = itens;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
