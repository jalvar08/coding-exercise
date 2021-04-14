package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture

@RestController
class BlogsController {

    @Autowired
    lateinit var blogRepository: BlogRepository

    @GetMapping("/blogs/{id}")
    @Async
    fun getBlogById(@PathVariable("id") id: Long): CompletableFuture<ResponseEntity<Blog>> {
        val blog = blogRepository.findById(id).orElse(null)
        return when (blog == null) {
            true -> CompletableFuture.completedFuture(ResponseEntity<Blog>(HttpStatus.NO_CONTENT))
            false -> CompletableFuture.completedFuture(ResponseEntity<Blog>(blog, HttpStatus.OK))
        }
    }

    @PostMapping("/blogs")
    @Async
    fun addOrUpdateBlog(@RequestBody blog: Blog): CompletableFuture<ResponseEntity<Blog>> {
        return when (ObjectUtils.isEmpty(blogRepository.save(blog))) {
            true -> CompletableFuture.completedFuture(ResponseEntity<Blog>(HttpStatus.BAD_REQUEST))
            false -> CompletableFuture.completedFuture(ResponseEntity<Blog>(HttpStatus.CREATED))
        }
    }
}
