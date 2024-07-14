package com.example.thinkot_2.repositories

import com.example.thinkot_2.entities.User
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface UserRepository : CrudRepository<User, Int> {

    var entityManager: EntityManager

    @PersistenceContext
    fun setEntityManager(entityManager: EntityManager) {
        this.entityManager = entityManager
    }

    fun saveOrUpdateByCPF(user: User) {
        val cpf = user.cpf

        val existingUser: User? = entityManager.createQuery("SELECT u FROM User u WHERE u.cpf = :cpf", User::class.java)
            .setParameter("cpf", cpf)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)

        if (existingUser != null) {
            existingUser.name = user.name
            existingUser.email = user.email
            existingUser.status = user.status
            entityManager.merge(existingUser)
        } else {
            entityManager.persist(user)
        }
    }
}
