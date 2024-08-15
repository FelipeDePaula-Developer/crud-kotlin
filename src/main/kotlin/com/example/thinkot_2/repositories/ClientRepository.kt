package com.example.thinkot_2.repositories

import com.example.thinkot_2.entities.Client
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface ClientRepository : CrudRepository<Client, Int> {
    @Query("SELECT u FROM Client u WHERE u.cpf = :cpf")
    fun findByCpf(cpf: String): Client?

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Client u WHERE u.cpf = :cpf")
    fun existsByCpf(cpf: String): Boolean
}
