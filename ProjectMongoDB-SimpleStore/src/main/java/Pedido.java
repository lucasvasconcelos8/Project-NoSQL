import java.util.Date;

/**
 * Created by Vasco on 11/05/2016.
 */
public class Pedido {

    int  pedido_id;
    String data_pedido;
    String data_entrega;
    String cpf_cliente;

    public Pedido(int pedido_id, String data_pedido, String data_entrega, String cpf_cliente) {
        this.pedido_id = pedido_id;
        this.data_pedido = data_pedido;
        this.data_entrega =  data_entrega;
        this.cpf_cliente = cpf_cliente;
    }
}
