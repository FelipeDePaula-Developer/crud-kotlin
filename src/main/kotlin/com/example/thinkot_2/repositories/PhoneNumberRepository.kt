package com.example.thinkot_2.repositories

import com.example.thinkot_2.entities.PhoneNumber
import org.springframework.data.repository.CrudRepository

interface PhoneNumberRepository : CrudRepository<PhoneNumber, Int>