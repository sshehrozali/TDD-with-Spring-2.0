package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @InjectMocks
    private TodoService serviceUnderTest;
    @Mock
    private TodoRepository todoRepository;

    @Test
    @DisplayName("Should Get All Todos")
    void shouldGetAllTodos() {
        ArrayList<Todo> existingTodos = new ArrayList<>();
        existingTodos.add(new Todo(1, "Todo 1", "Clean home"));
        existingTodos.add(new Todo(2, "Todo 2", "Pack Bag"));
        existingTodos.add(new Todo(3, "Todo 3", "Iron clothes"));
        when(todoRepository.findAll()).thenReturn(existingTodos);

        assertThat(serviceUnderTest.getAllTodos()).isEqualTo(existingTodos);
    }

    @Test
    @DisplayName("Should return Todo by name")
    void shouldGetTodoByTitle() {
        Todo existingTodo = Todo.builder()
                .title("Todo 1")
                .description("Pack bag")
                .build();
        when(todoRepository.findTodoByTitle(any())).thenReturn(existingTodo);

        assertThat(serviceUnderTest.getTodoByName("Todo 2")).isNotNull();
    }

    @Test
    @DisplayName("Should Add New Todo If New Todo Already Not Exists By Title")
    void shouldAddNewTodoIfNewTodoAlreadyNotExistsByTitle() {
        Todo todoToSave = Todo.builder()
                .title("Todo 1")
                .description("Make some preparations")
                .build();
        assertThat(serviceUnderTest.addNewTodo(todoToSave)).isTrue();
    }

    @Test
    @DisplayName("Should Not Add New Todo If New Todo Already Exists By Title")
    void shouldNotAddNewTodoIfNewTodoAlreadyExistsByTitle() {
        Todo existingTodo = Todo.builder()
                .title("Todo 1")
                .description("Clean laptop")
                .build();
        when(todoRepository.findTodoByTitle(any())).thenReturn(existingTodo);

        Todo newTodoToSave = Todo.builder()
                .title("Todo 1")
                .description("Cook food")
                .build();
        assertThrows(RuntimeException.class, () -> {
            serviceUnderTest.addNewTodo(newTodoToSave);
        });
    }

    @Test
    @DisplayName("Should save all todos and return true")
    void shouldSaveAllTodosAndReturnTrue() {
        ArrayList<Todo> todosToSave = new ArrayList<>();
        todosToSave.add(Todo.builder()
                .title("Todo 4")
                .description("Clean room")
                .build());

        todosToSave.add(Todo.builder()
                .title("Todo 5")
                .description("Iron clothes")
                .build());
        assertThat(serviceUnderTest.addAllTodos(todosToSave)).isTrue();
    }

    @Test
    @DisplayName("Should Get Todo By Description")
    void shouldGetTodoByDescription() {
        Todo existingTodo = Todo.builder()
                .title("Todo 1")
                .description("Clean room")
                .build();
        when(todoRepository.findTodoByDescription(existingTodo.getDescription())).thenReturn(existingTodo);

        assertThat(serviceUnderTest.getTodoByDescription(existingTodo.getDescription())).isEqualTo(existingTodo);
    }

    @Test
    @DisplayName("Should Throw Exception If Todo Not Found By Description")
    void shouldThrowExceptionIfTodoNotFoundByDescription() {
        assertThrows(RuntimeException.class, () -> {
            serviceUnderTest.getTodoByDescription("Clean Room");
        });
    }

    @Test
    @DisplayName("Should Get Todo By Title And Description")
    void shouldGetTodoByTitleAndDescription() {
        Todo existingTodo = Todo.builder()
                .title("Todo 1")
                .description("Clean laptop")
                .build();
        when(todoRepository.findTodoByTitleAndDescription(
                existingTodo.getTitle(),
                existingTodo.getDescription()
        ))
                .thenReturn(existingTodo);

        assertThat(serviceUnderTest.getTodoByTitleAndDescription(
                existingTodo.getTitle(),
                existingTodo.getDescription()
        )).isNotNull();
    }

    @Test
    @DisplayName("Should Throw Exception If Todo Not Found By Title And Description")
    void shouldThrowExceptionIfTodoNotFoundByTitleAndDescription() {
        assertThrows(RuntimeException.class, () -> {
            serviceUnderTest.getTodoByTitleAndDescription("Todo 1", "Clean laptop");
        });
    }
}