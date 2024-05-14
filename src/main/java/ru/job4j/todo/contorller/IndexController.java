package ru.job4j.todo.contorller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

@ThreadSafe
@Controller
public class IndexController {

    private final TaskService taskService;

    public IndexController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping({"/", "/index"})
    public String getIndex(Model model, @SessionAttribute User user) {
        model.addAttribute("tasks", taskService.setUserTimezoneToTasks(taskService.findAll(), user));
        return "tasks/list";

    }
}
