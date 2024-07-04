package com.pandey.jwt.demo.task;

import com.pandey.jwt.demo.dto.UserDto;
import com.pandey.jwt.demo.user.User;
import com.pandey.jwt.demo.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    private User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("user is : " + user);
        return userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public List<TaskDto> getAllTasks() {
        List<Task>tasks =  taskRepository.findAll().stream().collect(Collectors.toList());
        return tasks.stream().map((task) -> convertToTaskDto(task)).collect(Collectors.toList());
    }

    TaskDto convertToTaskDto(Task task){
        User user = task.getUser();
        UserDto userDto = new UserDto(user.getId(),user.getEmail(), user.getName());
        return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), userDto);
    }

    public Task createTask(Task task) {
        User user = getCurrentUser();
        System.out.println("User is : "+user);
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Optional<Task> getUserTaskById(Long id) {
        User user = getCurrentUser();
        return taskRepository.findById(id).filter(task -> task.getUser().getId().equals(user.getId()));
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = getUserTaskById(id).orElseThrow(() -> new IllegalStateException("Task not found or not authorized"));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getUserTaskById(id).orElseThrow(() -> new IllegalStateException("Task not found or not authorized"));
        taskRepository.delete(task);
    }
}
