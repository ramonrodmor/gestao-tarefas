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
			EntityManager em = JPAUtil.getEntityManager();
			Query q = em.createQuery("select a from Tarefa a", Tarefa.class);

			this.listaDeTarefas = q.getResultList();
			em.close();
		}
		return listaDeTarefas;
	}

	public Tarefa getTarefa() {
		return this.tarefa;
	}

	public void salvar(Tarefa tarefa) {
		System.out.println("Salvo o " + tarefa.getTitulo());

		// conseguimos a EntityManager
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(tarefa);
		em.getTransaction().commit();
		em.close();
	}

	public void excluir(Tarefa tarefa) {
		// conseguimos a EntityManager
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		tarefa = em.merge(tarefa);
		em.remove(tarefa);
		tx.commit();
		em.close();
	}

}
