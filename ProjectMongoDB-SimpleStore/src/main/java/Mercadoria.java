/**
 * Created by Vasco on 11/05/2016.
 */
public class Mercadoria {

    private int mercadoria_id;
    private String nome;
    private Double preco;

    public Mercadoria(int mercadoria_id, String nome, Double preco) {
        this.mercadoria_id = mercadoria_id;
        this.nome = nome;
        this.preco = preco;
    }

	public int getMercadoria_id() {
		return mercadoria_id;
	}

	public void setMercadoria_id(int mercadoria_id) {
		this.mercadoria_id = mercadoria_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
    
    
}
