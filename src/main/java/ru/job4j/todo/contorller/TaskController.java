package ru.job4j.todo.contorller;

import jakarta.servlet.http.HttpServletResponse;
import net.jcip.annotations.ThreadSafe;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.Optional;

@ThreadSafe
@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/completed")
    public String getCompleted(Model model) {
        model.addAttribute("tasks", taskService.findCompleted());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findNew());
        return "tasks/list";
    }

    @GetMapping("/task/{id}")
    public String getById(Model model, @PathVariable int id, HttpServletResponse response) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            model.addAttribute("message", "Can't get task with id=" + id
                    + " because this task not found");
            return "errors/error";
        }
        model.addAttribute("task", task.get());
        return "tasks/one";
    }

    @GetMapping("/task/add")
    public String getAddPage() {
        return "tasks/add";
    }

    @PostMapping("/task/add")
    public String create(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/tasks/all";
    }

    @GetMapping("/task/{id}/done")
    public String changeStatus(@PathVariable int id, Model model) {
        boolean isChanged = taskService.changeStatus(id);
        if (!isChanged) {
            model.addAttribute("message", "Can't change status of task with id=" + id
                    + " because this task not found");
            return "errors/error";
        }
        return "redirect:/tasks/all";
    }

    @GetMapping("/task/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        boolean isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Can't delete task with id=" + id
                    + " because this task not found");
            return "errors/error";
        }
        return "redirect:/tasks/all";
    }

    @GetMapping("/task/{id}/edit")
    public String getEditPage(Model model, @PathVariable int id) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Can't edit task with id=" + id
                    + " because this task not found");
            return "errors/error";
        }
        model.addAttribute("task", task.get());
        return "tasks/edit";
    }

    @PostMapping("/task/{id}/edit")
    public String edit(@ModelAttribute Task task, Model model) {
        boolean isUpdated = taskService.edit(task);
        if (!isUpdated) {
            model.addAttribute("message", "Can't edit task with id=" + task.getId()
                    + " because this task not found");
            return "errors/error";
        }
        return "redirect:/tasks/task/" + task.getId();
    }
}
