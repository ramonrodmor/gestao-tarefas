package com.ramon.gestaotarefas.controller.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.ramon.gestaotarefas.controller.JPAUtil;
import com.ramon.gestaotarefas.model.Responsavel;

@ManagedBean
public class ResponsavelBean {

	// --------------- ATRIBUTOS --------------- //

	private Responsavel novoResponsavel = new Responsavel();

	private List<Responsavel> listaDeResponsaveis;

	private List<SelectItem> listaSelecaoResponsaveis;

	// --------------- GETTERS --------------- //

	public Responsavel getNovoResponsavel() {
		return novoResponsavel;
	}

	public List<Responsavel> getListaDeResponsaveis() {
		if (this.listaDeResponsaveis == null) {
			// conseguimos a EntityManager
			EntityManager entityManager = JPAUtil.getEntityManager();
			Query query = entityManager.createQuery("select a from Responsavel a", Responsavel.class);

			this.listaDeResponsaveis = query.getResultList();
			entityManager.close();
		}
		return listaDeResponsaveis;
	}

	public List<SelectItem> getListaSelecaoResponsaveis() {
		this.listaSelecaoResponsaveis = new ArrayList<SelectItem>();
		List<Responsavel> listaDeResponsaveis = getListaDeResponsaveis(); 
		for(Responsavel responsavel : listaDeResponsaveis) {
			SelectItem item = new SelectItem (responsavel.getNome(), responsavel.getNome());
			this.listaSelecaoResponsaveis.add(item);
		}
		
		return this.listaSelecaoResponsaveis;
	}

	// --------------- MÉTODOS --------------- //

	public String salvarResponsavel(Responsavel responsavel) {

		System.out.println("Salvo o " + responsavel.getNome());

		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(responsavel);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		return "responsaveis";
	}

	public String excluirResponsavel(Responsavel responsavel) {
		// conseguimos a EntityManager
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		responsavel = entityManager.merge(responsavel);
		listaDeResponsaveis.remove(responsavel);
		entityManager.remove(responsavel);
		transaction.commit();
		entityManager.close();
		
		return "responsaveis";
	}
}
