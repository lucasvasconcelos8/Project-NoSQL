import java.util.ArrayList;


public class Cliente {

	String cpf;
	String nome;
	String telefone1;
	String telefone2;
	String pedido1;
	String pedido2;
	String pedido3;
	Endereco endereco;
	
	/*
	 * chave : cliente*
	 * 
	 * valor:"06325348346//Lucas Moreira de Vasconcelos//97010921//32794709//99695548"
	 * 		 "07489148235//Fernando Alvarez//88715621//00000000//00000000"
	 * 		 "03489112235//Alexandre Silva//89000621//00000000//00000000"
	 */
	
	
	/*
	 * Usando HASH
	 * 
	 * chave : cliente*
	 * 
	 * campos : cpf: 06325348346
	 * 			nome: Lucas Moreira de Vasconcelos
	 * 			telefone1: 97010921
	 * 			telefone2: 32794709
	 * 			telefone3: 00000000
	 * 			pedidos1: "001"
	 * 
	 */
	
	
	//UTIL http://pt.slideshare.net/kinncj/redis-um-banco-chave-valor
	public Cliente() {
		// TODO Auto-generated constructor stub
		
		
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

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getPedido1() {
		return pedido1;
	}

	public void setPedido1(String pedido1) {
		this.pedido1 = pedido1;
	}

	public String getPedido2() {
		return pedido2;
	}

	public void setPedido2(String pedido2) {
		this.pedido2 = pedido2;
	}

	public String getPedido3() {
		return pedido3;
	}

	public void setPedido3(String pedido3) {
		this.pedido3 = pedido3;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
