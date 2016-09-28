import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;


//APENAS DE TESTE

public class Janela extends JFrame {

	
	private static  JTextArea nome = new JTextArea();
	private static  JTextArea cpf = new JTextArea();
	
	public Janela() {
		// TODO Auto-generated constructor stub
		getContentPane().add(nome);
	    getContentPane().add(cpf);
		
	}
	
	static void montaJanela(ArrayList<Cliente> clientes){ 
			
		  nome.setText(clientes.get(0).getNome());
		  cpf.setText(clientes.get(0).getCpf());
	   }  

}
