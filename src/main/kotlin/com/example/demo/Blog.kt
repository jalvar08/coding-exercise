package com.example.demo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.*

@Entity
data class Blog(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val author: String,
    val title: String,
    val content: String,
    val date: String
)

@Repository
interface BlogRepository: CrudRepository<Blog, Long>
