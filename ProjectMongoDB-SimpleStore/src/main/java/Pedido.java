

/**
 * Created by Vasco on 11/05/2016.
 */
public class Pedido {

    private int  pedido_id;
    private String data_pedido;
    private String data_entrega;
    private String cpf_cliente;

    public Pedido(int pedido_id, String data_pedido, String data_entrega, String cpf_cliente) {
        this.pedido_id = pedido_id;
        this.data_pedido = data_pedido;
        this.data_entrega =  data_entrega;
        this.cpf_cliente = cpf_cliente;
    }

	public int getPedido_id() {
		return pedido_id;
	}

	public void setPedido_id(int pedido_id) {
		this.pedido_id = pedido_id;
	}

	public String getData_pedido() {
		return data_pedido;
	}

	public void setData_pedido(String data_pedido) {
		this.data_pedido = data_pedido;
	}

	public String getData_entrega() {
		return data_entrega;
	}

	public void setData_entrega(String data_entrega) {
		this.data_entrega = data_entrega;
	}

	public String getCpf_cliente() {
		return cpf_cliente;
	}

	public void setCpf_cliente(String cpf_cliente) {
		this.cpf_cliente = cpf_cliente;
	}
    
    
}
