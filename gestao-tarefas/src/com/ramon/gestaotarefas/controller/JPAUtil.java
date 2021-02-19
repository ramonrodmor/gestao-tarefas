package com.ramon.gestaotarefas.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static final EntityManagerFactory eManagerFactory = Persistence.createEntityManagerFactory("default");

	public static EntityManager getEntityManager() {
		return eManagerFactory.createEntityManager();
	}
}