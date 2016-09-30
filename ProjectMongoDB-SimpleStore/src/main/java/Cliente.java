import java.util.ArrayList;

/**
 * Created by Vasco on 11/05/2016.
 */
public class Cliente {

    private int cliente_id;
    private String cpf,nome;
    private ArrayList<Integer> telefones,pedidos;

    public Cliente(int cliente_id, String cpf, String nome, ArrayList<Integer> telefones, ArrayList<Integer> pedidos) {

        this.cliente_id = cliente_id;
        this.cpf = cpf;
        this.nome = nome;
        this.telefones = telefones;
        this.pedidos = pedidos;
    }

	public int getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(int cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Integer> getTelefones() {
		return telefones;
	}

	public void setTelefones(ArrayList<Integer> telefones) {
		this.telefones = telefones;
	}

	public ArrayList<Integer> getPedidos() {
		return pedidos;
	}

	public void setPedidos(ArrayList<Integer> pedidos) {
		this.pedidos = pedidos;
	}
    
    
}
