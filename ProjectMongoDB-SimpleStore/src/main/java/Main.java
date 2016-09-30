/**
 * Created by Vasco on 11/05/2016.
 */
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;

public class Main {

    static List<Pedido> pedidos = new ArrayList<Pedido>();
    static List<Cliente> clientes = new ArrayList<Cliente>();
    static List<Enderecos> enderecos = new ArrayList<Enderecos>();
    static List<Mercadoria> mercadorias = new ArrayList<Mercadoria>();
    static List<Item> itens = new ArrayList<Item>();

    public static void main(String[] args) {
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("pesquisaNoSQL").withReadPreference(ReadPreference.secondary());

        MongoCollection<Document> collLoja = db.getCollection("example");


        //All Documents
        ArrayList<Document> listDocuments = collLoja.find().into(new ArrayList<Document>());

        System.out.println("INFO: O Programa conectou-se com o Banco de Dados do Mongo.");

        int numero = 0;

        while(numero != 5) {
            System.out.println("-----Programa Loja no Mongo-----");
            System.out.println("O que deseja fazer?");
            System.out.println("1 - Consulta completa do que está sendo armazenado");
            System.out.println("2 - Consulta separada por classes");
            System.out.println("3 - Colocar um arquivo JSON com os dados da loja");
            System.out.println("4 - Apagar o que está salvo no banco da loja");
            System.out.println("5 - Exit");

            Scanner ler = new Scanner(System.in);
            numero = Integer.parseInt(ler.nextLine());

            if (numero == 1) {
                consultaCompleta(listDocuments);
            } else if (numero == 2) {
                consultaObjetos(listDocuments);
            } else if (numero == 3) {
                importJson(listDocuments, collLoja);
            } else if (numero == 4) {
                deleteAll(collLoja);
                //Juts for test
                importJson(listDocuments, collLoja);
            }
        }

    }

    private static void importJson(ArrayList<Document> listDocuments, MongoCollection<Document> collLoja) {
        if(collLoja.count() == 0){
            System.out.println("Coleção Vazia, pode importar um arquivo");
            collLoja.insertMany(listDocuments);

            System.out.print("Utilize o comando do mongoImport no seu terminal para importar um novo JSON");

        }
        else{
            System.out.println("A coleção ja possui os valores");
        }
    }

    private static void deleteAll(MongoCollection<Document> collLoja){
        collLoja.drop();
    }


    //Just for test
    public static void consultaCompleta(ArrayList<Document> listDocuments){
        ArrayList<Document> listClientes = new ArrayList<Document>();
        ArrayList<Document> listMercadorias = new ArrayList<Document>();
        ArrayList<Document> listItens = new ArrayList<Document>();
        ArrayList<Document> listPedidos = new ArrayList<Document>();
        ArrayList<Document> listEnderecos = new ArrayList<Document>();

        for(int i =0; i<listDocuments.size(); i++){
            if(listDocuments.get(i).containsKey("cliente_id")){
                listClientes.add(listDocuments.get(i));
            }else if(listDocuments.get(i).containsKey("pedido_id")){
                listPedidos.add(listDocuments.get(i));
            }else if(listDocuments.get(i).containsKey("mercadoria_id")){
                listMercadorias.add(listDocuments.get(i));
            }else if(listDocuments.get(i).containsKey("item_id")){
                listItens.add(listDocuments.get(i));
            }else if(listDocuments.get(i).containsKey("endereco_id")){
                listEnderecos.add(listDocuments.get(i));
            }
        }

        System.out.println(listClientes.toString());
        System.out.println(listPedidos.toString());
        System.out.println(listMercadorias.toString());
        System.out.println(listItens.toString());
        System.out.println(listEnderecos.toString());

    }

    @SuppressWarnings("unchecked")
	private static void consultaObjetos(ArrayList<Document> listDocuments) {


        for(int i =0; i<listDocuments.size(); i++){
            if(listDocuments.get(i).containsKey("cliente_id")){
                int cliente_id = Integer.parseInt(listDocuments.get(i).get("cliente_id").toString());
                String cpf  = (String) listDocuments.get(i).get("cpf");
                String nome = (String) listDocuments.get(i).get("nome");
                @SuppressWarnings("unchecked")
				ArrayList<Integer> telefones = (ArrayList<Integer>) listDocuments.get(i).get("telefones");
                ArrayList<Integer> pedidos = (ArrayList<Integer>) listDocuments.get(i).get("pedidos");
                Cliente cliente = new Cliente(cliente_id,cpf,nome,telefones,pedidos);

                clientes.add(cliente);

            }else if(listDocuments.get(i).containsKey("pedido_id")){
                int pedido_id = Integer.parseInt(listDocuments.get(i).get("pedido_id").toString());
                String data_pedido  = listDocuments.get(i).get("data_pedido").toString();
                String data_entrega = listDocuments.get(i).get("data_entrega").toString();
                String cpf_cliente = (String) listDocuments.get(i).get("cliente_cpf");
                Pedido pedido = new Pedido(pedido_id,data_pedido,data_entrega,cpf_cliente);

                pedidos.add(pedido);

            }else if(listDocuments.get(i).containsKey("mercadoria_id")){
                int mercadoria_id = Integer.parseInt(listDocuments.get(i).get("mercadoria_id").toString());
                String nome  = (String) listDocuments.get(i).get("nome");
                Double preco = Double.parseDouble(listDocuments.get(i).get("preco").toString());
                Mercadoria mercadoria = new Mercadoria(mercadoria_id,nome,preco);

                mercadorias.add(mercadoria);

            }else if(listDocuments.get(i).containsKey("item_id")){
                int item_id = Integer.parseInt(listDocuments.get(i).get("item_id").toString());
                int numero  = Integer.parseInt(listDocuments.get(i).get("numero").toString());
                int quantidade = Integer.parseInt(listDocuments.get(i).get("quantidade").toString());
                int mercadoriaId = Integer.parseInt(listDocuments.get(i).get("mercadoria").toString());
                int pedidoId = Integer.parseInt(listDocuments.get(i).get("pedido").toString());
                Item item = new Item(item_id,numero,quantidade,mercadoriaId,pedidoId);

                itens.add(item);

            }else if(listDocuments.get(i).containsKey("endereco_id")){
                int endereco_id = Integer.parseInt(listDocuments.get(i).get("endereco_id").toString());
                String cep  = (String) listDocuments.get(i).get("cep");
                String logradouro = (String) listDocuments.get(i).get("logradouro");
                String uf = (String) listDocuments.get(i).get("uf");
                String cidade = (String) listDocuments.get(i).get("cidade");
                String cpf_cliente = (String) listDocuments.get(i).get("cliente");
                Enderecos endereco = new Enderecos(endereco_id,cep,logradouro,uf,cidade,cpf_cliente);

                enderecos.add(endereco);

            }

        }


        int number = 0;

        while(number != 6){
            System.out.println("-----Classes Loja-----");
            System.out.println("Qual parte da loja você deseja analisar?");
            System.out.println("1 - Cliente");
            System.out.println("2 - Mercadoria");
            System.out.println("3 - Item");
            System.out.println("4 - Pedidos");
            System.out.println("5 - Endereços");
            System.out.println("6 - Exit");

            Scanner ler = new Scanner(System.in);
            number = Integer.parseInt(ler.nextLine());

            if (number == 1) {
                System.out.println("->Os clientes são:");
                for(int i=0 ; i < clientes.size() ; i++){
                    System.out.println("-> "+clientes.get(i).getNome()+" - "+clientes.get(i).getCpf());
                }
            } else if (number == 2) {
                System.out.println("->As mercadorias são:");
                for(int i=0 ; i < mercadorias.size() ; i++){
                    System.out.println("-> "+mercadorias.get(i).getNome()+" - "+mercadorias.get(i).getPreco());
                }
            } else if (number == 3) {
                System.out.println("->Os itens são:");
                for(int i=0 ; i < itens.size() ; i++){
                    System.out.println("-> "+itens.get(i).getPedidoId()+" - "+itens.get(i).getQuantidade());
                }
            } else if (number == 4) {
                System.out.println("->Os pedidos são:");
                for(int i=0 ; i < pedidos.size() ; i++){
                    System.out.println("-> CPF:"+pedidos.get(i).getCpf_cliente());
                }
            } else if (number == 5) {
                System.out.println("->Os endereços são:");
                for(int i=0 ; i < enderecos.size() ; i++){
                    System.out.println("-> "+enderecos.get(i).getLogradouro()+" - "+enderecos.get(i).getCidade()+" - "+enderecos.get(i).getUf());
                }
            }
        }
    }
}
