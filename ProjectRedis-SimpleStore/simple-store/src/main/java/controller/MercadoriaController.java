package controller;

import java.util.ArrayList;

import model.Mercadoria;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author Vasco
 *	Class for handle mercadorias calls from redis
 *
 */

public class MercadoriaController {
	
	ArrayList<Mercadoria> mercadorias;
	
	public MercadoriaController(ArrayList<Mercadoria> mercadorias){
		this.mercadorias = mercadorias;
	}

	public void carregarMercadoriasToRedisViaHash(Jedis jedis) {
		//Codigo,nome,preco
		int i = 1;
		int j=0;//Variavel da quantidade de pedidos armazenadas
		
		while(j < mercadorias.size()){
			jedis.hset("loja:mercadoria"+String.valueOf(i), "codigo", mercadorias.get(j).getCodigo());
			jedis.hset("loja:mercadoria"+String.valueOf(i), "nome", mercadorias.get(j).getNome());
			jedis.hset("loja:mercadoria"+String.valueOf(i), "preco", String.valueOf(mercadorias.get(j).getPreco()));
			i++;
			j++;
		}
		
		if(mercadorias.size() > 0){
			System.out.println("Mercadorias carregadas para o banco");
		}
		else
			System.out.println("Nenhuma mercadoria foi carregadas(Verifique se existe alguma no banco");
	}

	public void carregarMercadoriasFromRedisViaHash(Jedis jedis) {
		int i = 1;
		
		while(jedis.exists("loja:mercadoria"+String.valueOf(i))){
			Mercadoria mercadoria = new Mercadoria();
			
			mercadoria.setCodigo(jedis.hget("loja:mercadoria"+ String.valueOf(i), "codigo"));
			mercadoria.setNome(jedis.hget("loja:mercadoria"+ String.valueOf(i), "nome"));
			mercadoria.setPreco(Float.parseFloat(jedis.hget("loja:mercadoria"+ String.valueOf(i), "preco")));
			
			mercadorias.add(mercadoria);
			
			i++;
		}
		if(mercadorias.size() > 0){
			System.out.println("Mercadorias carregadas");
		}
		else
			System.out.println("Nenhuma mercadoria foi carregadas(Verifique se existe alguma no banco");
	}

}
