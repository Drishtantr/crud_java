package com.crud_spring_angular.crud;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskRepository repository;

	public TaskController(TaskRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Task create(@RequestBody Task task) {
		return repository.save(task);
	}

	@GetMapping
	public List<Task> list() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Task> get(@PathVariable Long id) {
		return repository.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public Task update(@PathVariable Long id, @RequestBody Task task) {
		task.setId(id);
		return repository.save(task);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
