import java.util.ArrayList;

/**
 * Created by Vasco on 11/05/2016.
 */
public class Cliente {

    int cliente_id;
    String cpf,nome;
    ArrayList<Integer> telefones,pedidos;

    public Cliente(int cliente_id, String cpf, String nome, ArrayList<Integer> telefones, ArrayList<Integer> pedidos) {

        this.cliente_id = cliente_id;
        this.cpf = cpf;
        this.nome = nome;
        this.telefones = telefones;
        this.pedidos = pedidos;
    }
}
