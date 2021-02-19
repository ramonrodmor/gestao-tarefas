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

	private List<Tarefa> listaDeTarefas;

	// --------------- GETTERS --------------- //

	public List<Tarefa> getListaDeTarefas() {

		return this.listaDeTarefas = buscaListaNoBanco();
	}

	public Tarefa getNovaTarefa() {
		return this.novaTarefa;
	}

	public Tarefa getFiltroTarefa() {
		return this.filtroDeTarefa;
	}

	// --------------- MÉTODOS --------------- //

	public List<Tarefa> buscaListaNoBanco() {
		
		if (this.listaDeTarefas == null) {
			EntityManager entityManager = JPAUtil.getEntityManager();
			Query query = entityManager.createQuery(
					"select a from Tarefa a where a.situacao like 'em andamento'", Tarefa.class);
			this.listaDeTarefas = query.getResultList();
			entityManager.close();
		}
		return this.listaDeTarefas;
	}

	public List<Tarefa> filtrarTarefas(Tarefa filtroDeTarefa) {

		return buscaListaNoBanco();
	}

	public String salvarTarefa(Tarefa tarefa) {
		tarefa.setSituacao("em andamento");

		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(tarefa);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return "cadastratarefa";
	}

	public void excluirTarefa(Tarefa tarefa) {
		

		this.listaDeTarefas.remove(tarefa);
		
		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		tarefa = entityManager.merge(tarefa);
		entityManager.remove(tarefa);
		transaction.commit();
		entityManager.close();
	}

	public void concluirTarefa(Tarefa tarefa) {

		tarefa.setSituacao("concluida");
		this.listaDeTarefas.remove(tarefa);

		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		tarefa = entityManager.merge(tarefa);
		transaction.commit();
		entityManager.close();
	}

}
