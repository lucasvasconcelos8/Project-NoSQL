package controller;

import java.util.ArrayList;

import model.Endereco;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author Vasco
 *	Class for handle endereco calls from redis and console
 *
 */
public class EnderecoController {

	ArrayList<Endereco> enderecos;
	
	public EnderecoController(ArrayList<Endereco> enderecos){
		this.enderecos = enderecos;
	}
	
	public void carregarEnderecoToRedisViaHash(Jedis jedis) {
		int i = 1;
		int j=0;//Variavel da quantidade de pedidos armazenadas
		
		while(j < enderecos.size()){
			jedis.hset("loja:endereco"+String.valueOf(i), "logradouro", enderecos.get(j).getLogradouro());
			jedis.hset("loja:endereco"+String.valueOf(i), "cidade", enderecos.get(j).getCidade());
			jedis.hset("loja:endereco"+String.valueOf(i), "uf", enderecos.get(j).getUf());
			jedis.hset("loja:endereco"+String.valueOf(i), "cep", enderecos.get(j).getCep());
			jedis.hset("loja:endereco"+String.valueOf(i), "cliente", enderecos.get(j).getCliente_cpf());
			i++;
			j++;
		}	
		
		if(j == enderecos.size()){
			System.out.println("Enderecos carregados para o banco");
		}
		else{
			System.out.println("Nenhum endereco foi carregado(Verifique se existe algum dado para ir ao banco");
		}	
	}

	public void carregarEnderecosFromRedisViaHash(Jedis jedis) {
		int i = 1;
		
		while(jedis.exists("loja:endereco"+String.valueOf(i))){
			Endereco endereco = new Endereco();
			
			endereco.setLogradouro(jedis.hget("loja:endereco"+String.valueOf(i), "logradouro"));
			endereco.setCidade(jedis.hget("loja:endereco"+String.valueOf(i), "cidade"));
			endereco.setUf(jedis.hget("loja:endereco"+String.valueOf(i), "uf"));
			endereco.setCep(jedis.hget("loja:endereco"+String.valueOf(i), "cep"));
			endereco.setCliente_cpf(jedis.hget("loja:endereco"+String.valueOf(i), "cliente"));
			
			enderecos.add(endereco);
			
			i++;		
		}
		if(enderecos.size()>0){
			System.out.println("Enderecos carregados");
		}
		else{
			System.out.println("Nenhum endereco foi carregado(Verifique se existe algum no banco");
		}	
	} 
}
