/**
 * Created by Vasco on 11/05/2016.
 */
public class Item {

    int item_id;
    int numero;
    int quantidade;
    int mercadoriaId;
    int pedidoId;

    public Item(int item_id, int numero, int quantidade, int mercadoriaId, int pedidoId) {
        this.item_id = item_id;
        this.numero = numero;
        this.quantidade = quantidade;
        this.mercadoriaId = mercadoriaId;
        this.pedidoId = pedidoId;
    }
}
