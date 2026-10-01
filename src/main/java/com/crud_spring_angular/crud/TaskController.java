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

	private final TaskRepository taskRepo;

	public TaskController(TaskRepository taskRepo) {
		this.taskRepo = taskRepo;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Task addTask(@RequestBody Task incoming) {
		return taskRepo.save(incoming);
	}

	@GetMapping
	public List<Task> listTasks() {
		return taskRepo.findAll();
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<Task> getTask(@PathVariable Long taskId) {
		return taskRepo.findById(taskId)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{taskId}")
	public Task updateTask(@PathVariable Long taskId, @RequestBody Task incoming) {
		incoming.setTaskId(taskId);
		return taskRepo.save(incoming);
	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<Void> removeTask(@PathVariable Long taskId) {
		if (!taskRepo.existsById(taskId)) {
			return ResponseEntity.notFound().build();
		}
		taskRepo.deleteById(taskId);
		return ResponseEntity.noContent().build();
	}

}
