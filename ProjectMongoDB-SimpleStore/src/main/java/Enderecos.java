/**
 * Created by Vasco on 11/05/2016.
 */
public class Enderecos {

    int endereco_id;
    String cep;
    String logradouro;
    String uf;
    String cidade;
    String cpf_cliente;

    public Enderecos(int endereco_id, String cep, String logradouro, String uf, String cidade, String cpf_cliente) {
        this.endereco_id = endereco_id;
        this.cep = cep;
        this.logradouro = logradouro;
        this.uf = uf;
        this.cidade = cidade;
        this.cpf_cliente = cpf_cliente;
    }
}
