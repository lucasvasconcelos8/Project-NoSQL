/**
 * Created by Vasco on 11/05/2016.
 */
public class Item {

    private int item_id;
    private int numero;
    private int quantidade;
    private int mercadoriaId;
    private int pedidoId;

    public Item(int item_id, int numero, int quantidade, int mercadoriaId, int pedidoId) {
        this.item_id = item_id;
        this.numero = numero;
        this.quantidade = quantidade;
        this.mercadoriaId = mercadoriaId;
        this.pedidoId = pedidoId;
    }

	public int getItem_id() {
		return item_id;
	}

	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getMercadoriaId() {
		return mercadoriaId;
	}

	public void setMercadoriaId(int mercadoriaId) {
		this.mercadoriaId = mercadoriaId;
	}

	public int getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(int pedidoId) {
		this.pedidoId = pedidoId;
	}
    
    
}
