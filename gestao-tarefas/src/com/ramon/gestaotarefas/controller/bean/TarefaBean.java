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

		if (filtroDeTarefa.getSituacao().equals(".")) {
			return this.listaDeTarefas = buscaListaNoBanco();
		} else {
			return this.listaDeTarefas = filtrarTarefas(this.filtroDeTarefa);
		}
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
			// conseguimos a EntityManager
			EntityManager entityManager = JPAUtil.getEntityManager();
			Query query = entityManager.createQuery("select a from Tarefa a where a.situacao like 'em andamento'",
					Tarefa.class);

			this.listaDeTarefas = query.getResultList();
			entityManager.close();
		}
		return listaDeTarefas;
	}

	public List<Tarefa> filtrarTarefas(Tarefa filtroDeTarefa) {

		if (this.listaDeTarefas == null) {
			// conseguimos a EntityManager
			EntityManager entityManager = JPAUtil.getEntityManager();
			Query query;

			if (filtroDeTarefa.getSituacao().equals(".")) {
				query = entityManager.createQuery("select a from Tarefa a", Tarefa.class);
			} else {
				query = entityManager.createQuery(
						"select a from Tarefa a where a.situacao='" + filtroDeTarefa.getSituacao() + "'", Tarefa.class);
			}
			this.listaDeTarefas = query.getResultList();
			entityManager.close();
		}
		return listaDeTarefas;
	}

	public void salvarTarefa(Tarefa tarefa) {
		System.out.println("Salvo a " + tarefa.getTitulo());
		System.out.println("Responsavel e o " + tarefa.getResponsavel());

		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(tarefa);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void excluirTarefa(Tarefa tarefa) {
		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		tarefa = entityManager.merge(tarefa);
		this.listaDeTarefas.remove(tarefa);
		entityManager.remove(tarefa);
		transaction.commit();
		entityManager.close();
	}

	public void concluirTarefa(Tarefa tarefa) {

		tarefa.setSituacao("concluida");

		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		tarefa = entityManager.merge(tarefa);
		transaction.commit();
		entityManager.close();
	}

}
