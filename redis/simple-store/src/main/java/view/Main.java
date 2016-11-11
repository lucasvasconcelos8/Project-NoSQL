package project_redis.simple_store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import controller.StoreController;
import model.Cliente;
import model.Endereco;
import model.Item;
import model.Mercadoria;
import model.Pedido;
import redis.clients.jedis.*;



public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public Main() {
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
		  Jedis jedis = new Jedis("localhost");
	      
	      System.out.println("Connection to server sucessfully");
	      //check whether server is running or not
	      System.out.println("Server is running: "+jedis.ping());
	      
	      //storage conecction between aplication and redis
	      StoreController store = new StoreController(jedis,clientes,pedidos,itens,mercadorias,enderecos);
	      
	      ///////////////////////////MENU/////////////////////////////////
	      int numero = 0;
	      
	      
	      while(numero != 6){ 
		      
	    	  System.out.println("------------------------MENU----------------------");
		      System.out.println("1.Carregar dados de datasets");
		      System.out.println("2.Carregar dados a partir do Banco Redis");
		      System.out.println("3.Carregar dados para o Banco Redis");
		      System.out.println("4.Deletar daddos no banco redis (isto � todas as chaves do projeto");
		      System.out.println("5.Listar dados");
		      System.out.println("6.Sair");
		      
		      System.out.println("Digite o n�mero da a��o desejada");
		      @SuppressWarnings("resource")
		      Scanner ler = new Scanner(System.in);
		      numero = Integer.parseInt(ler.nextLine());
		      
		      if(numero == 1){
		    	  carregarClientes();
			      carregarPedidos();
			      carregarItens();
			      carregarMercadorias();
			      carregarEnderecos();
			      System.out.println("Foram carregados os dados dos datasest");
		      }
		      if(numero == 2){ 
		    	  store.receiveFromRedis();
		    	  
		    	  if(store.getClientes().size() == 0 && store.getEnderecos().size() == 0 && store.getPedidos().size() == 0 && store.getMercadorias().size() == 0 && store.getItens().size() == 0){
		    		  System.out.println("O banco est� vazio");
		    	  }
		    	  else{
		    		  System.out.println("Foi carregado dados do banco Redis");
		    		  System.out.println(store.getClientes().get(0).getNome());
		    	  }
		      }
		      if(numero == 3){
		    	  System.out.println("Enviando para o Redis...");
		    	  
		    	  store.SendToRedis();
		      }
		      if(numero == 4){
		    	  System.out.println("Deletando keys...");
		    	  deletarChavesFromRedis(jedis);
		      }
		      if(numero == 5){
		  			store.receiveFromRedis();
		  	  
		    	  if(store.getClientes().size() == 0 && store.getEnderecos().size() == 0 && store.getPedidos().size() == 0 && store.getMercadorias().size() == 0 && store.getItens().size() == 0){
		    		  System.out.println("O banco est� vazio");
		    	  }
		    	  else{
		    		  System.out.println("Foi carregado dados do banco Redis");
		    		  
		    		  //Pick in controller all types in redis
		    		  ArrayList<Cliente> clientes = store.getClientes();
		    		  ArrayList<Pedido> pedidos = store.getPedidos();
		    		  ArrayList<Mercadoria> mercadorias = store.getMercadorias();
		    		  ArrayList<Item> itens = store.getItens();
		    		  ArrayList<Endereco> enderecos = store.getEnderecos();
		    		  
		    		  //Show data with some relevants attributes
		    		  for(int i = 0 ; i < clientes.size() ; i++){
		    			  System.out.println("Nome: "+clientes.get(i).getNome()+ " , CPF: "+clientes.get(i).getCpf());
		    		  }
		    		  for(int i = 0 ; i < pedidos.size() ; i++){
		    			  System.out.println("Nº Pedido: "+pedidos.get(i).getCodigo()+ " Data Pedido: "+pedidos.get(i).getData_pedido());
		    		  }
		    		  for(int i = 0 ; i < mercadorias.size() ; i++){
		    			  System.out.println("Mercadoria: "+mercadorias.get(i).getNome()+ " Preço: "+mercadorias.get(i).getPreco());
		    		  }
		    		  for(int i = 0 ; i < itens.size() ; i++){
		    			  System.out.println("Nº Item: "+itens.get(i).getNumero()+ " QtD: "+itens.get(i).getQuantidade());
		    		  }
		    		  for(int i = 0 ; i < enderecos.size() ; i++){
		    			  System.out.println("Enderecos: "+enderecos.get(i).getCep()+ " Cidade: "+enderecos.get(i).getCidade());
		    		  }
		    	  }
		      }
		      if(numero == 6){
		    	 System.out.println("Tchau!");
		      }
	      }    
	      
	 }
	

	private static void deletarChavesFromRedis(Jedis jedis) {
		// TODO Auto-generated method stub
		int i=1;
		int total=0;
		while(jedis.exists("loja:cliente"+ String.valueOf(i))){
			jedis.del("loja:cliente"+ String.valueOf(i));
			i++;
		}
		total= total+i;
		i=1;
		while(jedis.exists("loja:endereco"+ String.valueOf(i))){
			jedis.del("loja:endereco"+ String.valueOf(i));
			i++;
		}
		total= total+i;
		i=1;
		while(jedis.exists("loja:mercadoria"+ String.valueOf(i))){
			jedis.del("loja:mercadoria"+ String.valueOf(i));
			i++;
		}
		total= total+i;
		i=1;
		while(jedis.exists("loja:item"+ String.valueOf(i))){
			jedis.del("loja:item"+ String.valueOf(i));
			i++;
		}
		total= total+i;
		i=1;
		while(jedis.exists("loja:pedido"+ String.valueOf(i))){
			jedis.del("loja:pedido"+ String.valueOf(i));
			i++;
		}
		total= total+i-5;
		System.out.println("Deletado com sucesso!/nNumero de chaves deletadas: "+total);
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
					   cliente.setCpf(linha.substring(0, i));
					   atual_palavra = atual_palavra+i;
					   contador++;
				   }
				   else if(contador == 1){
					   cliente.setNome(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 2){
					   cliente.setTelefone1(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 3){
					   cliente.setTelefone2(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 4){
					   cliente.setPedido1(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 5){
					   cliente.setPedido2(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   cliente.setPedido3(linha.substring(atual_palavra));
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
					   pedido.setCodigo(Integer.parseInt((linha.substring(0, i))));
					   atual_palavra = atual_palavra+i;
					   contador++;
				   }
				   else if(contador == 1){
					   pedido.setData_pedido(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 2){
					   pedido.setData_entrega(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
					   pedido.setCliente(buscarCliente(linha.substring(atual_palavra)));
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
					   item.setNumero((linha.substring(0, i)));
					   atual_palavra = atual_palavra+i;
					   contador++;
				   }
				   else if(contador == 1){
					   item.setQuantidade(Integer.parseInt(linha.substring(atual_palavra,i)));
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 2){
					   item.setMercadoria(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
					   item.setPedido(linha.substring(atual_palavra));
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
					   mercadoria.setCodigo((linha.substring(0, i)));
					   atual_palavra = atual_palavra+i;
					   contador++;
				   }
				   else if(contador == 1){
					   mercadoria.setNome(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
					   mercadoria.setPreco(Float.parseFloat(linha.substring(atual_palavra)));
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
					   endereco.setCep((linha.substring(0, i)));
					   atual_palavra = atual_palavra+i;
					   contador++;
				   }
				   else if(contador == 1){
					   endereco.setLogradouro(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 2){
					   endereco.setUf(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
				   }
				   else if(contador == 3){
					   endereco.setCidade(linha.substring(atual_palavra,i));
					   atual_palavra = i+1;
					   contador++;
					   endereco.setCliente_cpf(linha.substring(atual_palavra));
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
