package com.example.thinkot_2.repositories

import com.example.thinkot_2.entities.Client
import org.springframework.data.repository.CrudRepository

interface ClientRepository : CrudRepository<Client, Int>