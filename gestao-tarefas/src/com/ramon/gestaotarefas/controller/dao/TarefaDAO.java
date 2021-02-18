package com.ramon.gestaotarefas.controller.dao;

import java.text.ParseException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.ramon.gestaotarefas.controller.JPAUtil;
import com.ramon.gestaotarefas.model.Tarefa;

public class TarefaDAO {

	public static void main(String[] args) throws ParseException {
		// conseguimos a EntityManager
		EntityManager em = JPAUtil.getEntityManager();

		Tarefa tarefa = new Tarefa();
		tarefa.setTitulo("Primeira tarefa");
		tarefa.setDescricao(
				"Esta é a primeira terefa criada para a nossa aplicação, utilizada apenas para fins de testes.");
		tarefa.setResponsavel("Ramon");
		tarefa.setPrioridade("média");
		tarefa.setDeadline("23/02/2021");

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(tarefa);
		tx.commit();
		em.close();
	}
}
