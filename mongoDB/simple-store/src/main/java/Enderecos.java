/**
 * Created by Vasco on 11/05/2016.
 */
public class Enderecos {

    private int endereco_id;
    private String cep;
    private String logradouro;
    private String uf;
    private String cidade;
    private String cpf_cliente;

    public Enderecos(int endereco_id, String cep, String logradouro, String uf, String cidade, String cpf_cliente) {
        this.endereco_id = endereco_id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.uf = uf;
        this.cidade = cidade;
        this.cpf_cliente = cpf_cliente;
    }

	public int getEndereco_id() {
		return endereco_id;
	}

	public void setEndereco_id(int endereco_id) {
		this.endereco_id = endereco_id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCpf_cliente() {
		return cpf_cliente;
	}

	public void setCpf_cliente(String cpf_cliente) {
		this.cpf_cliente = cpf_cliente;
	}
    
    
}
