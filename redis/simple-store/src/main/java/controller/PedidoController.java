package controller;

import java.util.ArrayList;

import model.Cliente;
import model.Pedido;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author Vasco
 *	Class for handle pedidos calls from redis
 *
 */

public class PedidoController {
	
	private ArrayList<Pedido> pedidos;
	
	public PedidoController(ArrayList<Pedido> pedidos){
		this.pedidos = pedidos;
	}

	public void carregarPedidosToRedisViaHash(Jedis jedis) {
		int i=1;
		int j=0;//Variavel da quantidade de pedidos armazenadas
		
		while(j < pedidos.size()){
			jedis.hset("loja:pedido"+String.valueOf(i), "codigo", String.valueOf(pedidos.get(j).getCodigo()));
			jedis.hset("loja:pedido"+String.valueOf(i), "data:pedido", pedidos.get(j).getData_pedido());
			jedis.hset("loja:pedido"+String.valueOf(i), "data:entrega", pedidos.get(j).getData_entrega());
			jedis.hset("loja:pedido"+String.valueOf(i), "cliente", pedidos.get(j).getCliente().getCpf());
			i++;
			j++;
		}
		
		if(j == pedidos.size() ){
			
			System.out.println("Pedidos carregados para o banco");
		}
		else{
			System.out.println("Nenhum pedido foi carregado(verifique se existe algum dados para ir no banco");
		}
	}

	public void carregarPedidosFromRedisViaHash(ArrayList<Cliente> clientes,Jedis jedis) {
		
		int i=1;
		
		while(jedis.exists("loja:pedido"+String.valueOf(i))){
			Pedido order = new Pedido();
			
			order.setCodigo(Integer.parseInt(jedis.hget("loja:pedido"+ String.valueOf(i), "codigo")));
			//order.setData_entrega(ft.parse(jedis.hget("loja:pedido"+ String.valueOf(i), "data:pedido")));
			//order.setData_pedido(ft.parse(jedis.hget("loja:pedido"+ String.valueOf(i), "data:entrega")));
			
			Cliente client = new Cliente();
			client = buscarCliente(clientes,jedis.hget("loja:pedido"+ String.valueOf(i), "data:entrega"));
			order.setCliente(client);
			
			pedidos.add(order);
			
			i++;
		}
		
		if(pedidos.size() >0 ){
			
			System.out.println("Pedidos carregados");
		}
		else{
			System.out.println("Nenhum pedido foi carregado(verifique se existe algum no banco)");
		}
	}
	
	/*
	 *  OUTRAS FUNÇÕES
	 */
	
	static Cliente buscarCliente(ArrayList<Cliente> clientes,String cpf){
		for(int i =0 ; i< clientes.size() ; i++){
			if(clientes.get(i).getCpf().equalsIgnoreCase(cpf)){
				return clientes.get(i);
			}
		}
		return null;
	}

}
