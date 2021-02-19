package com.ramon.gestaotarefas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tarefas")
public class Tarefa {
	
	// --------------- ATRIBUTOS --------------- //
	
	@SequenceGenerator(name = "tarefaGenerator", sequenceName = "TAREFA_SEQ", allocationSize = 10)
	@Id @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="tarefaGenerator")
	private Long id;
	private String titulo;
	private String descricao;
	private String responsavel;
	private String prioridade;
	private String deadline;
	private String situacao = "em andamento";
	
	// --------------- CONSTRUTORES --------------- //
	public Tarefa() {
		
	}

	public Tarefa(Long id, String titulo, String descricao, String responsavel, String prioridade, String deadline, String situacao) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.responsavel = responsavel;
		this.prioridade = prioridade;
		this.deadline = deadline;
		this.situacao = situacao;
	}

	// --------------- GETTERS E SETTERS --------------- //
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getSituacao() {
		return situacao;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}
