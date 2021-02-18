package com.ramon.gestaotarefas.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.ramon.gestaotarefas.model.Tarefa;

@ManagedBean
public class TarefaBean {

	private Tarefa tarefa = new Tarefa();

	private List<Tarefa> listaDeTarefas;

	public List<Tarefa> getListaDeTarefas() {

		if (this.listaDeTarefas == null) {
			// conseguimos a EntityManager
			EntityManager entityManager = JPAUtil.getEntityManager();
			Query query = entityManager.createQuery("select a from Tarefa a", Tarefa.class);

			this.listaDeTarefas = query.getResultList();
			entityManager.close();
		}
		return listaDeTarefas;
	}

	public Tarefa getTarefa() {
		return this.tarefa;
	}

	public void salvar(Tarefa tarefa) {
		System.out.println("Salvo o " + tarefa.getTitulo());

		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.persist(tarefa);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void excluir(Tarefa tarefa) {
		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		tarefa = entityManager.merge(tarefa);
		entityManager.remove(tarefa);
		transaction.commit();
		entityManager.close();
	}

}
