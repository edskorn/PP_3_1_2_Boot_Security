package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.security.PersonDetails;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/")
	public String printWelcome() {
		return "index";
	}
	@RequestMapping(value = "/admin")
	public String showAll(ModelMap model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("allUsers", users);
		return "all-users";
	}

	@RequestMapping(value = "/addUser")
	public String addUser(ModelMap model){
		model.addAttribute("user", new User());

		List<Role> roles = userService.getAllRoles();
		model.addAttribute("allRoles", roles);

		return "add-user";
	}

	@RequestMapping(value = "/saveUser")
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap model){
		List<Role> roles = userService.getAllRoles();
		model.addAttribute("allRoles", roles);

		if (result.hasErrors()) {
			return "add-user";
		}
		userService.saveUser(user);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/updateUser")
	public String updateUser(@RequestParam("userId") int userId, ModelMap model){
		model.addAttribute("user", userService.getUserById(userId));

		List<Role> roles = userService.getAllRoles();
		model.addAttribute("allRoles", roles);

		return "add-user";
	}

	@RequestMapping(value = "/deleteUser")
	public String deleteUser(@RequestParam("userId") int userId){
		userService.removeUserById(userId);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/user")
	public String showUserInfo(ModelMap model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = ((PersonDetails) authentication.getPrincipal()).getUser();
		model.addAttribute("user", user);
		return "user-info";
	}
}