package br.com.alura.spring.data.orm;

import java.math.BigDecimal;

public class FuncionarioDTO {

	private Integer id;
	private String nome;
	private BigDecimal salario;
	

	public FuncionarioDTO() {
	}
	

	public FuncionarioDTO(Integer id, String nome, BigDecimal salario) {
		this.id = id;
		this.nome = nome;
		this.salario = salario;
	}



	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getSalario() {
		return salario;
	}
	
	
	
	
	
		
}
