package com.ramon.gestaotarefas.controller.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.ramon.gestaotarefas.controller.JPAUtil;
import com.ramon.gestaotarefas.model.Tarefa;

@ManagedBean
public class TarefaBean {

	// --------------- ATRIBUTOS --------------- //

	private Tarefa filtroDeTarefa = new Tarefa();

	private Tarefa novaTarefa = new Tarefa();

	private Tarefa tarefaEditada = new Tarefa();
	
	private Tarefa tarefaEmEdicao = new Tarefa();

	private List<Tarefa> listaDeTarefas;

	// --------------- GETTERS --------------- //

	public Tarefa getFiltroTarefa() {
		return this.filtroDeTarefa;
	}

	public Tarefa getNovaTarefa() {
		return this.novaTarefa;
	}

	public Tarefa getTarefaEditada() {
		return this.tarefaEditada;
	}
	
	public Tarefa getTarefaEmEdicao() {
		return tarefaEmEdicao;
	}
	
	public List<Tarefa> getListaDeTarefas() {
		return this.listaDeTarefas = listarTarefas(filtroDeTarefa);
	}

	// --------------- MÉTODOS --------------- //

	public List<Tarefa> listarTarefas(Tarefa filtroDeTarefa) {

		if (this.listaDeTarefas == null) {

			EntityManager entityManager = JPAUtil.getEntityManager();
			Query query = entityManager.createQuery(montarQueryFiltro(filtroDeTarefa), Tarefa.class);
			this.listaDeTarefas = query.getResultList();
			entityManager.close();
		}
		return this.listaDeTarefas;
	}

	public String salvarTarefa(Tarefa tarefa) {
		
		tarefa.setSituacao("em andamento");

		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(tarefa);
		entityManager.getTransaction().commit();
		entityManager.close();

		return "listatarefas";
	}

	public String editarTarefa(Tarefa tarefa) {
		
		this.tarefaEditada = this.tarefaEmEdicao = tarefa;
		
		return "editartarefa";
	}

	public String salvarTarefaEditada(Tarefa tarefaEditada) {
		
		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		this.tarefaEmEdicao = entityManager.merge(tarefaEditada);
		entityManager.getTransaction().commit();
		entityManager.close();

		return "listatarefas";
	}

	public void excluirTarefa(Tarefa tarefa) {

		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		tarefa = entityManager.merge(tarefa);
		entityManager.remove(tarefa);
		entityManager.getTransaction().commit();
		entityManager.close();

	}

	public void concluirTarefa(Tarefa tarefa) {

		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		tarefa = entityManager.merge(tarefa);
		tarefa.setSituacao("concluida");
		entityManager.getTransaction().commit();
		entityManager.close();

	}

	public String montarQueryFiltro(Tarefa filtroTarefa) {

		String stringQuery = "";
		if (filtroTarefa.getId() == null) {
			stringQuery = "select a from Tarefa a where a.situacao like 'em andamento'";
		} else {
			stringQuery = "select a from Tarefa a where ";
			if (filtroTarefa.getId() == 0) {
				filtroTarefa.setId(null);
			} else {
				stringQuery += "a.id = " + filtroTarefa.getId() + " and ";
			}
			if (filtroTarefa.getTitulo() != "") {
				stringQuery += "(a.titulo like '%" + filtroTarefa.getTitulo() + "%' or a.descricao like '%"
						+ filtroTarefa.getTitulo() + "%') and ";
			}
			if (filtroTarefa.getResponsavel() != "") {
				stringQuery += "a.responsavel like '%" + filtroTarefa.getResponsavel() + "%' and ";
			}
			if (filtroTarefa.getPrioridade() != "") {
				stringQuery += "a.prioridade like '%" + filtroTarefa.getPrioridade() + "%' and ";
			}
			if (filtroTarefa.getSituacao() != "") {
				stringQuery += "a.situacao like '%" + filtroTarefa.getSituacao() + "%'";
			}

		}
		return stringQuery;
	}

}
