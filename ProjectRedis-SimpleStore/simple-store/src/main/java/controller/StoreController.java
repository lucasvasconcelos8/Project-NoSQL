package controller;

import java.util.ArrayList;

import model.Cliente;
import model.Endereco;
import model.Item;
import model.Mercadoria;
import model.Pedido;
import redis.clients.jedis.Jedis;

/**
 * Class for pack all calls from redis 
 * @author Vasco
 *
 */
public class StoreController {
	
	
	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private ArrayList<Pedido> pedidos = new ArrayList<Pedido>(); 
	private ArrayList<Item> itens = new ArrayList<Item>();
	private ArrayList<Mercadoria> mercadorias = new ArrayList<Mercadoria>();
	private ArrayList<Endereco>  enderecos = new ArrayList<Endereco>();
	private Jedis jedis;
	
	public StoreController(Jedis jedis,ArrayList<Cliente> clientes, ArrayList<Pedido> pedidos, ArrayList<Item> itens, ArrayList<Mercadoria> mercadorias, ArrayList<Endereco> enderecos ){
		this.clientes = clientes;
		this.pedidos = pedidos;
		this.mercadorias = mercadorias;
		this.itens = itens;
		this.enderecos = enderecos;
		this.jedis = jedis;
	}
	
	public void SendToRedis(){
		
		ClienteController clienteC = new ClienteController(clientes);
		PedidoController  pedidoC = new PedidoController(pedidos);
		ItemController itemC = new ItemController(itens);
		MercadoriaController mercadoriaC = new MercadoriaController(mercadorias);
		EnderecoController enderecoC = new EnderecoController(enderecos);
		
		clienteC.carregarClientesToRedisViaHash(jedis);
		pedidoC.carregarPedidosToRedisViaHash(jedis);
		itemC.carregarItensToRedisViaHash(jedis);
		mercadoriaC.carregarMercadoriasToRedisViaHash(jedis);
		enderecoC.carregarEnderecoToRedisViaHash(jedis);
	}
	
	public void receiveFromRedis(){
		
		ClienteController clienteC = new ClienteController(clientes);
		PedidoController  pedidoC = new PedidoController(pedidos);
		ItemController itemC = new ItemController(itens);
		MercadoriaController mercadoriaC = new MercadoriaController(mercadorias);
		EnderecoController enderecoC = new EnderecoController(enderecos);
		
		clienteC.carregarClientesFromRedisViaHash(jedis);
  	  	enderecoC.carregarEnderecosFromRedisViaHash(jedis);
  	  	itemC.carregarItensFromRedisViaHash(jedis);
  	  	mercadoriaC.carregarMercadoriasFromRedisViaHash(jedis);
  	  	pedidoC.carregarPedidosFromRedisViaHash(clientes,jedis);
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public ArrayList<Item> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Item> itens) {
		this.itens = itens;
	}

	public ArrayList<Mercadoria> getMercadorias() {
		return mercadorias;
	}

	public void setMercadorias(ArrayList<Mercadoria> mercadorias) {
		this.mercadorias = mercadorias;
	}

	public ArrayList<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(ArrayList<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
	
	
}
