package com.ramon.gestaotarefas.controller.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ramon.gestaotarefas.controller.JPAUtil;
import com.ramon.gestaotarefas.model.Tarefa;

@ManagedBean
@RequestScoped
public class TarefaBean {

	// --------------- ATRIBUTOS --------------- //

	private Tarefa filtroTarefa = new Tarefa();

	private Tarefa novaTarefa = new Tarefa();

	private Tarefa tarefaEmEdicao = new Tarefa();

	private List<Tarefa> listaDeTarefas;

	private List<Tarefa> listaDeTarefasFiltrada;

	// --------------- GETTERS --------------- //

	public Tarefa getFiltroTarefa() {
		return this.filtroTarefa;
	}

	public Tarefa getNovaTarefa() {
		return this.novaTarefa;
	}

	public Tarefa getTarefaEmEdicao() {
		return this.tarefaEmEdicao;
	}

	public List<Tarefa> getListaDeTarefas() {

		if (this.listaDeTarefas == null) {
			EntityManager entityManager = JPAUtil.getEntityManager();
			Query query = entityManager.createQuery("select a from Tarefa a where a.situacao='em andamento'",
					Tarefa.class);
			this.listaDeTarefas = query.getResultList();
			entityManager.close();
		}

		return this.listaDeTarefas;
	}

	public List<Tarefa> getListaDeTarefasFiltrada() {

		return this.listaDeTarefasFiltrada;
	}

	// --------------- MÉTODOS --------------- //

	public void filtrarTarefas(Tarefa filtro) {

		this.filtroTarefa = filtro;
		this.listaDeTarefasFiltrada = getListaDeTarefasFiltrada();
		if (this.listaDeTarefasFiltrada == null) {
			
			EntityManager entityManager = JPAUtil.getEntityManager();
			Query query = entityManager.createQuery(montarQueryFiltro(this.filtroTarefa), Tarefa.class);
			this.listaDeTarefasFiltrada = query.getResultList();
			entityManager.close();
		}
	}

	public String salvarTarefa(Tarefa tarefa) {

		tarefa.setSituacao("em andamento");

		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(tarefa);
		entityManager.getTransaction().commit();
		entityManager.close();

		return "lista";
	}

	public String editarTarefa(Tarefa tarefa) {

		this.tarefaEmEdicao = tarefa;

		return "editartarefa";
	}

	public String salvarTarefaEditada(Tarefa tarefaEditada) {

		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		this.tarefaEmEdicao = entityManager.merge(tarefaEditada);
		entityManager.getTransaction().commit();
		entityManager.close();

		return "lista";
	}

	public void excluirTarefa(Tarefa tarefa) {

		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		tarefa = entityManager.merge(tarefa);
		entityManager.remove(tarefa);
		entityManager.getTransaction().commit();
		entityManager.close();

	}

	public void concluirTarefa(Tarefa tarefa) {

		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		tarefa = entityManager.merge(tarefa);
		tarefa.setSituacao("concluida");
		entityManager.getTransaction().commit();
		entityManager.close();
		
		this.listaDeTarefas.remove(tarefa);

	}

	public String montarQueryFiltro(Tarefa filtroTarefa) {

		String stringQuery = "";
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
		if (filtroTarefa.getSituacao().equals("todas") || filtroTarefa.getSituacao().equals("")) {
			stringQuery += "a.situacao like '%'";
		} else if (filtroTarefa.getSituacao() != "") {
			stringQuery += "a.situacao like '%" + filtroTarefa.getSituacao() + "%'";
		}

		return stringQuery;
	}

}
