import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.google.gson.Gson;

//import com.google.gson.Gson;





import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MainFinal extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainFinal() {
		// TODO Auto-generated constructor stub
		//Parte de carregamento para o Banco redis...colocar tudo em um json
	}
	
	static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	static ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
	static ArrayList<Item> itens = new ArrayList<Item>();
	static ArrayList<Mercadoria> mercadorias = new ArrayList<Mercadoria>();
	static ArrayList<Endereco>  enderecos = new ArrayList<Endereco>();
	
	//ERRADO
	public static void main(String[] args) throws ParseException, IOException {
		//Connecting to Redis server on localhost
	      @SuppressWarnings("resource")
		  Jedis jedis = new Jedis("localhost");
	      
	      System.out.println("Connection to server sucessfully");
	      //check whether server is running or not
	      System.out.println("Server is running: "+jedis.ping());
	      
	      ///////////////////////////MENU/////////////////////////////////
	      boolean fim_menu = false;
	      int i = 0;
	      System.out.println("oi");
	      while(i != 4){ 
		      System.out.println("------------------------MENU----------------------");
		      System.out.println("1.Carregar dados de datasets");
		      System.out.println("2.Carregar dados a partir do Banco Redis");
		      System.out.println("3.Carregar dados para o Banco Redis");
		      System.out.println("4.Sair");
		      
		      System.out.println("Digite o n�mero da a��o desejada");
		      @SuppressWarnings("resource")
			Scanner ler = new Scanner(System.in);
		      int numero = Integer.parseInt(ler.nextLine());
		      
		      if(numero == 1){
		    	  carregarClientes();
			      carregarPedidos();
			      carregarItens();
			      carregarMercadorias();
			      carregarEnderecos();
		      }
		      if(numero == 2){
		    	  carregarClientesFromRedisViaHash(jedis);
		    	  carregarEnderecosFromRedisViaHash(jedis);
		    	  carregarItensFromRedisViaHash(jedis);
		    	  carregarMercadoriasFromRedisViaHash(jedis);
		    	  carregarPedidosFromRedisViaHash(jedis);
		      }
		      if(numero == 3){
		    	  carregarClientesToRedisViaHash(jedis);
		    	  carregarEnderecosToRedisViaHash(jedis);
		    	  carregarItensToRedisViaHash(jedis);
		    	  carregarMercadoriasToRedisViaHash(jedis);
		    	  carregarPedidosToRedisViaHash(jedis);
		      }
		      if(numero == 4){
		    	 fim_menu = true; 
		    	 i = 1;
		      }
	      }    
	      
	      /* BUSCANDO DO BANCO DE DADOS
	      System.out.println("Buscando do banco de dados de Redis");
	      
	      long tempoInicial = System.currentTimeMillis();
	      System.out.println(carregarClientes(jedis));
	      System.out.println(carregarPedidos(jedis));
	      System.out.println(carregarItens(jedis));
	      System.out.println(carregarMercadorias(jedis));
	      System.out.println(carregarEnderecos(jedis));
	      long tempoFinal = System.currentTimeMillis() - tempoInicial;
	      
	      System.out.println("O numero de chaves que est� no redis � :");
	      //	System.out.println(jedis.dbSize());
	      System.out.println(clientes.size()+pedidos.size()+itens.size()+mercadorias.size()+enderecos.size());
	      
	      System.out.println("O tempo que levou para carregar todo o banco:");
	      System.out.println(tempoFinal+"ms");
	      */
	      
	 }
	
	/*
	 *  FUNÇÕES COM CARREGAMENTO DO BANCO REDIS
	 */
	
	//Carrega os clientes direto do Redis
	static String carregarClientesFromRedisViaHash(Jedis jedis){
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
		
			return "Clientes carregados";
		}
		else{
			return "Nenhum cliente foi carregado(verifique se existe algum no banco)";
		}
	}
	
	//Carrega os pedidos direto do Redis
	static String carregarPedidosFromRedisViaHash(Jedis jedis) throws ParseException{
		int i=1;
		
		SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		
		while(jedis.exists("loja:pedido"+String.valueOf(i))){
			Pedido order = new Pedido();
			
			order.setCodigo(Integer.parseInt(jedis.hget("loja:pedido"+ String.valueOf(i), "codigo")));
			//order.setData_entrega(ft.parse(jedis.hget("loja:pedido"+ String.valueOf(i), "data:pedido")));
			//order.setData_pedido(ft.parse(jedis.hget("loja:pedido"+ String.valueOf(i), "data:entrega")));
			
			Cliente client = new Cliente();
			client = buscarCliente(jedis.hget("loja:pedido"+ String.valueOf(i), "data:entrega"));
			order.setCliente(client);
			
			pedidos.add(order);
			
			i++;
		}
		
		if(pedidos.size() >0 ){
			
			return "Pedidos carregados";
		}
		else{
			return "Nenhum pedido foi carregado(verifique se existe algum no banco)";
		}
	}
	
	//Carrega os itens direto do Redis
	static String carregarItensFromRedisViaHash(Jedis jedis){
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
			
			return "Itens carregados";
		}
		else{
			return "Nenhum item foi carregado(verifique se existe algum no banco)";
		}
	}
	
	static String carregarMercadoriasFromRedisViaHash(Jedis jedis){
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
			return "Mercadorias carregadas";
		}
		else
			return "Nenhuma mercadoria foi carregadas(Verifique se existe alguma no banco";
	}
	
	static String carregarEnderecosFromRedisViaHash(Jedis jedis){
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
			return "Enderecos carregados";
		}
		else{
			return "Nenhum endereco foi carregado(Verifique se existe algum no banco";
		}	
	}
	
	/*
	 * FUNÇÕES DE CARREGAMENTO DE UM DATASET
	 */
	
	/*
	 *  TODAS OK
	 */
	
	//Função funcionando perfeitamente
	//Objetivo:  Buscar as informações dos clientes no txt e armazenalas na classe Cliente.
	static void carregarClientes() throws IOException{
		int i = 0;
		int contador = 0;
		int atual_palavra = 1;
		
		BufferedReader br = new BufferedReader(new FileReader("clientes.txt"));
		  
		while(br.ready()){  
		   String linha = br.readLine(); 
		   Cliente cliente = new Cliente();
		   for(i=0;i< linha.length();i++){
			   if (linha.substring(i, i+1).equalsIgnoreCase(",")){
				   if(contador == 0){
					   cliente.cpf = linha.substring(0, i);
					   atual_palavra = atual_palavra+i;
					   contador++;
				   }
				   else if(contador == 1){
					   cliente.nome = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 2){
					   cliente.telefone1 = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 3){
					   cliente.telefone2 = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 4){
					   cliente.pedido1 = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 5){
					   cliente.pedido2 = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   cliente.pedido3 = linha.substring(atual_palavra);
					   contador++;
				   }
				   
			   }
			}
		   clientes.add(cliente);
		   contador = 0;
		   atual_palavra = 1;
		}  
		br.close();
	}
	
	//Função funcionando perfeitamente
	//Objetivo:  Buscar as informações dos pedidos no txt e armazenalas na classe Pedido.
	static void carregarPedidos() throws IOException{
		int i = 0;
		int contador = 0;
		int atual_palavra = 1;
		
		BufferedReader br = new BufferedReader(new FileReader("pedidos.txt"));
		  
		while(br.ready()){  
		   String linha = br.readLine(); 
		   Pedido pedido = new Pedido();
		   for(i=0;i< linha.length();i++){
			   if (linha.substring(i, i+1).equalsIgnoreCase(",")){
				   if(contador == 0){
					   pedido.codigo = Integer.parseInt((linha.substring(0, i)));
					   atual_palavra = atual_palavra+i;
					   contador++;
				   }
				   else if(contador == 1){
					   pedido.data_pedido = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 2){
					   pedido.data_entrega = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
					   pedido.cliente = buscarCliente(linha.substring(atual_palavra));
				   }
				   
			   }
			}
		   pedidos.add(pedido);
		   contador = 0;
		   atual_palavra = 1;
		}  
		br.close();
	}
	
	//Função funcionando perfeitamente
	//Objetivo:  Buscar as informações dos itens no txt e armazenalas na classe Item.
	static void carregarItens() throws IOException{
		int i = 0;
		int contador = 0;
		int atual_palavra = 1;
		
		BufferedReader br = new BufferedReader(new FileReader("itens.txt"));
		  
		while(br.ready()){  
		   String linha = br.readLine(); 
		   Item item = new Item();
		   for(i=0;i< linha.length();i++){
			   if (linha.substring(i, i+1).equalsIgnoreCase(",")){
				   if(contador == 0){
					   item.numero = (linha.substring(0, i));
					   atual_palavra = atual_palavra+i;
					   contador++;
				   }
				   else if(contador == 1){
					   item.quantidade = Integer.parseInt(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 2){
					   item.mercadoria = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
					   item.pedido = linha.substring(atual_palavra);
				   }
				   
			   }
			}
		   itens.add(item);
		   contador = 0;
		   atual_palavra = 1;
		}  
		br.close();
		
	}
	
	//Função funcionando perfeitamente
	//Objetivo:  Buscar as informações dos mercadorias no txt e armazenalas na classe Mercadoria.
	static void carregarMercadorias() throws IOException{
		int i = 0;
		int contador = 0;
		int atual_palavra = 1;
		
		BufferedReader br = new BufferedReader(new FileReader("mercadorias.txt"));
		  
		while(br.ready()){  
		   String linha = br.readLine(); 
		   Mercadoria mercadoria = new Mercadoria();
		   for(i=0;i< linha.length();i++){
			   if (linha.substring(i, i+1).equalsIgnoreCase(",")){
				   if(contador == 0){
					   mercadoria.codigo = (linha.substring(0, i));
					   atual_palavra = atual_palavra+i;
					   contador++;
				   }
				   else if(contador == 1){
					   mercadoria.nome = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
					   mercadoria.preco = Float.parseFloat(linha.substring(atual_palavra));
				   }
				   
			   }
			}
		   mercadorias.add(mercadoria);
		   contador = 0;
		   atual_palavra = 1;
		}  
		br.close();
		
	}
	
	//Função funcionando perfeitamente
	//Objetivo:  Buscar as informações dos enderecos no txt e armazenalas na classe Endereco.
	static void carregarEnderecos() throws IOException{
		int i = 0;
		int contador = 0;
		int atual_palavra = 1;
		
		BufferedReader br = new BufferedReader(new FileReader("enderecos.txt"));
		  
		while(br.ready()){  
		   String linha = br.readLine(); 
		   Endereco endereco = new Endereco();
		   for(i=0;i< linha.length();i++){
			   if (linha.substring(i, i+1).equalsIgnoreCase(",")){
				   if(contador == 0){
					   endereco.cep = (linha.substring(0, i));
					   atual_palavra = atual_palavra+i;
					   contador++;
				   }
				   else if(contador == 1){
					   endereco.logradouro = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 2){
					   endereco.uf = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 3){
					   endereco.cidade = linha.substring(atual_palavra,i);
					   atual_palavra = i+1;
					   contador++;
					   endereco.cliente_cpf = linha.substring(atual_palavra);
				   }
				   
			   }
			}
		   enderecos.add(endereco);
		   contador = 0;
		   atual_palavra = 1;
		}  
		br.close();
		
	}

	
	/*
	 *  OUTRAS FUNÇÕES
	 */
	
	static Cliente buscarCliente(String cpf){
		for(int i =0 ; i< clientes.size() ; i++){
			if(clientes.get(i).getCpf().equalsIgnoreCase(cpf)){
				return clientes.get(i);
			}
		}
		return null;
	}
	
	/*
	 *	FUNCOES DE CARREGAMENTO PARA O REDIS
	 *	(FALTA FAZER )
	 *
	 */
	
	//Carrega os clientes direto do Redis
		static String carregarClientesToRedisViaHash(Jedis jedis){
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
			
				return "Clientes carregados para o banco";
			}
			else{
				return "Nenhum cliente foi carregado(verifique se existem dados para ir ao banco)";
			}
		}
		
		//Carrega os pedidos direto do Redis
		static String carregarPedidosToRedisViaHash(Jedis jedis) throws ParseException{
			int i=1;
			int j=0;//Variavel da quantidade de pedidos armazenadas
			
			while(j < pedidos.size()){
				jedis.hset("loja:pedidos"+String.valueOf(i), "codigo", String.valueOf(pedidos.get(j).getCodigo()));
				jedis.hset("loja:pedidos"+String.valueOf(i), "data:pedido", pedidos.get(j).getData_pedido());
				jedis.hset("loja:pedidos"+String.valueOf(i), "data:entrega", pedidos.get(j).getData_entrega());
				jedis.hset("loja:pedidos"+String.valueOf(i), "cliente", pedidos.get(j).getCliente().getCpf());
				i++;
				j++;
			}
			
			if(j == pedidos.size() ){
				
				return "Pedidos carregados para o banco";
			}
			else{
				return "Nenhum pedido foi carregado(verifique se existe algum dados para ir no banco";
			}
		}
		
		//Carrega os itens direto do Redis
		static String carregarItensToRedisViaHash(Jedis jedis){
			int i =1;
			int j=0;//Variavel da quantidade de pedidos armazenadas
			
			while(j < itens.size()){
				jedis.hset("loja:itens"+String.valueOf(i), "numero", itens.get(j).getNumero());
				jedis.hset("loja:itens"+String.valueOf(i), "quantidade", String.valueOf(itens.get(j).getQuantidade()));
				jedis.hset("loja:itens"+String.valueOf(i), "mercadoria", itens.get(j).getMercadoria());
				jedis.hset("loja:itens"+String.valueOf(i), "pedido", itens.get(j).getPedido());
				i++;
				j++;
			}
			if(j == itens.size()){
				
				return "Itens carregados para o banco";
			}
			else{
				return "Nenhum item foi carregado(verifique se existe algum dado para ir ao banco)";
			}
		}
		
		static String carregarMercadoriasToRedisViaHash(Jedis jedis){
			//Codigo,nome,preco
			int i = 1;
			int j=0;//Variavel da quantidade de pedidos armazenadas
			
			while(j < mercadorias.size()){
				jedis.hset("loja:mercadorias"+String.valueOf(i), "codigo", mercadorias.get(j).getCodigo());
				jedis.hset("loja:mercadorias"+String.valueOf(i), "nome", mercadorias.get(j).getNome());
				jedis.hset("loja:mercadorias"+String.valueOf(i), "preco", String.valueOf(mercadorias.get(j).getPreco()));
				i++;
				j++;
			}
			
			if(mercadorias.size() > 0){
				return "Mercadorias carregadas para o banco";
			}
			else
				return "Nenhuma mercadoria foi carregadas(Verifique se existe alguma no banco";
		}
		
		static String carregarEnderecosToRedisViaHash(Jedis jedis){
			int i = 1;
			int j=0;//Variavel da quantidade de pedidos armazenadas
			
			while(j < enderecos.size()){
				jedis.hset("loja:enderecos"+String.valueOf(i), "logradouro", enderecos.get(j).getLogradouro());
				jedis.hset("loja:enderecos"+String.valueOf(i), "cidade", enderecos.get(j).getCidade());
				jedis.hset("loja:enderecos"+String.valueOf(i), "uf", enderecos.get(j).getUf());
				jedis.hset("loja:enderecos"+String.valueOf(i), "cep", enderecos.get(j).getCep());
				jedis.hset("loja:enderecos"+String.valueOf(i), "cliente", enderecos.get(j).getCliente_cpf());
				i++;
				j++;
			}	
			
			if(j == enderecos.size()){
				return "Enderecos carregados para o banco";
			}
			else{
				return "Nenhum endereco foi carregado(Verifique se existe algum dado para ir ao banco";
			}	
		}

	
	/*
	 * Base de dados:
	 * 		Chaves:
	 * 				loja:cliente
	 * 						cpf
	 * 						nome
	 * 						telefone1
	 * 						telefone2
	 * 						telefone3
	 * 						pedido1
	 * 						pedido2
	 * 						pedido3
	 * 				loja:pedidos
	 * 						codigo
	 * 						data:entrega
	 * 						data:pedido
	 * 						cliente
	 * 				loja:item			
	 * 						numero
	 * 						quantidade
	 * 						mercadoria
	 * 						pedido
	 * 				loja:mercadoria
	 * 						codigo
	 * 						nome
	 * 						preco
	 * 				loja:endereco
	 * 						logradouro
	 * 						cidade
	 * 						uf
	 * 						cep
	 * 						cliente
	 * 	
	 * 
	 */
}
