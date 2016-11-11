package controller;

import java.util.ArrayList;

import model.Item;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author Vasco
 *	Class for handle items calls from redis
 *
 */

public class ItemController {

	private ArrayList<Item> itens;
	
	public ItemController(ArrayList<Item> itens){
		this.itens = itens;
	}
	
	public void carregarItensToRedisViaHash(Jedis jedis) {
		int i =1;
		int j=0;//Variavel da quantidade de pedidos armazenadas
		
		while(j < itens.size()){
			jedis.hset("loja:item"+String.valueOf(i), "numero", itens.get(j).getNumero());
			jedis.hset("loja:item"+String.valueOf(i), "quantidade", String.valueOf(itens.get(j).getQuantidade()));
			jedis.hset("loja:item"+String.valueOf(i), "mercadoria", itens.get(j).getMercadoria());
			jedis.hset("loja:item"+String.valueOf(i), "pedido", itens.get(j).getPedido());
			i++;
			j++;
		}
		if(j == itens.size()){
			
			System.out.println("Itens carregados para o banco");
		}
		else{
			System.out.println("Nenhum item foi carregado(verifique se existe algum dado para ir ao banco)");
		}
	}

	public void carregarItensFromRedisViaHash(Jedis jedis) {
		int i =1;
		
		while(jedis.exists("loja:item"+String.valueOf(i))){
			Item item = new Item();
			
			item.setNumero(jedis.hget("loja:item"+String.valueOf(i), "numero"));
			item.setQuantidade(Integer.parseInt(jedis.hget("loja:item"+ String.valueOf(i), "quantidade")));
			item.setMercadoria(jedis.hget("loja:item"+String.valueOf(i), "mercadoria"));
			item.setPedido(jedis.hget("loja:item"+String.valueOf(i), "pedido"));
			
			itens.add(item);
			
			i++;
		}
		if(itens.size() >0 ){
			
			System.out.println("Itens carregados");
		}
		else{
			System.out.println("Nenhum item foi carregado(verifique se existe algum no banco)");
		}
	}

}
