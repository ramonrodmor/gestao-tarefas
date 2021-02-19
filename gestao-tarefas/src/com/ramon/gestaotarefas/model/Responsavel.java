package com.ramon.gestaotarefas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="responsaveis")
public class Responsavel {

	// --------------- ATRIBUTOS --------------- //
	@SequenceGenerator(name = "responsavelGenerator", sequenceName = "RESPONSAVEL_SEQ", allocationSize = 10)
	@Id @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="responsavelGenerator") 
	private Long id;
	private String nome;
	
	// --------------- GETTERS E SETTERS --------------- //
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
