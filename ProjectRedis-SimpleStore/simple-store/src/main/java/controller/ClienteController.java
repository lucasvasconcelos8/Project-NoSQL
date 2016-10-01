package controller;

import java.util.ArrayList;

import model.Cliente;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author Vasco
 *	Class for handle clients calls from redis
 *
 */

public class ClienteController {
	
	private ArrayList<Cliente> clientes;
	
	public ClienteController(ArrayList<Cliente> clientes){
		this.clientes = clientes;
	}
	
	public void carregarClientesToRedisViaHash(Jedis jedis) {
		/*
		 * Poderia melhorar aqui em vez de sempre setar a partir i = 1 . Olha o banco e definir quantos j� possuem l� e apenas continuar o que tem la
		 */
		
		int i=1; //Variavel para inserir no banco certo
		int j=0; //Contador do numero de clientes
		
		while(j < clientes.size()){
			jedis.hset("loja:cliente"+String.valueOf(i), "cpf", clientes.get(j).getCpf());
			jedis.hset("loja:cliente"+String.valueOf(i), "nome", clientes.get(j).getNome());
			jedis.hset("loja:cliente"+String.valueOf(i), "telefone1", clientes.get(j).getTelefone1());
			jedis.hset("loja:cliente"+String.valueOf(i), "telefone2", clientes.get(j).getTelefone2());
			jedis.hset("loja:cliente"+String.valueOf(i), "pedido1", clientes.get(j).getPedido1());
			jedis.hset("loja:cliente"+String.valueOf(i), "pedido2", clientes.get(j).getPedido2());
			jedis.hset("loja:cliente"+String.valueOf(i), "pedido3", clientes.get(j).getPedido3());
			j++;
			i++;
		}
		
		if(j == clientes.size()){
		
			 System.out.println("Clientes carregados para o banco");
		}
		else{
			System.out.println("Nenhum cliente foi carregado(verifique se existem dados para ir ao banco)");
		}
	}

	public void carregarClientesFromRedisViaHash(Jedis jedis) {
		int i=1;
		
		
		while(jedis.exists("loja:cliente"+ String.valueOf(i))){
	    	  Cliente client = new Cliente();
	    	  client.setCpf(jedis.hget("loja:cliente"+ String.valueOf(i), "cpf"));
	    	  client.setNome(jedis.hget("loja:cliente"+ String.valueOf(i), "nome"));
	    	  client.setTelefone1(jedis.hget("loja:cliente"+ String.valueOf(i), "telefone1"));
	    	  client.setTelefone2(jedis.hget("loja:cliente"+ String.valueOf(i), "telefone2"));
	    	  client.setPedido1(jedis.hget("loja:cliente"+ String.valueOf(i), "pedido1"));
	    	  client.setPedido2(jedis.hget("loja:cliente"+ String.valueOf(i), "pedido2"));
	    	  client.setPedido3(jedis.hget("loja:cliente"+ String.valueOf(i), "pedido3"));
	    	  clientes.add(client);
	    	  i++;
	    }
		
		if(clientes.size() >0 ){
		
			System.out.println("Clientes carregados");
		}
		else{
			System.out.println("Nenhum cliente foi carregado(verifique se existe algum no banco)");
		}
	}
		
}

