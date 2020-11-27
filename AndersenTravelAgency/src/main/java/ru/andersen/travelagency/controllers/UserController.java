/*
package ru.andersen.travelagency.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.andersen.travelagency.entity.User;
import ru.andersen.travelagency.hibreq.UserHib;

@Controller
@RequestMapping("/user")
public class UserController {

    UserHib userHib;

    @Autowired
    public UserController(UserHib userHib) {
        this.userHib = userHib;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("user", userHib.show());
        return "user/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userHib.showById(id));
        return "user/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user) {
        return "user/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userHib.save(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userHib.showById(id));
        return "user/edit";
    }

    @PatchMapping()
    public String update(@ModelAttribute("user") User user) {
        userHib.update(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userHib.delete(id);
        return "redirect:/user";
    }
}
*/
