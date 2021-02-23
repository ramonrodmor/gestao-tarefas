package com.ramon.gestaotarefas.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tarefas")
public class Tarefa {

	// --------------- ATRIBUTOS --------------- //

	@SequenceGenerator(name = "tarefaGenerator", sequenceName = "TAREFA_SEQ", allocationSize = 10)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarefaGenerator")
	private Long id;
	private String titulo;
	private String descricao;
	private String responsavel;
	private String prioridade;
	@Temporal(TemporalType.DATE)
	private Date deadline;
	private String situacao;

	// --------------- CONSTRUTORES --------------- //
	public Tarefa() {

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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
}
