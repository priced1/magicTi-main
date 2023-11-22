package com.itb.mif3an.magictilogin.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.itb.mif3an.magictilogin.model.Role;
import com.itb.mif3an.magictilogin.model.User;
import com.itb.mif3an.magictilogin.service.UserService;
import com.itb.mif3an.magictilogin.web.dto.UserDto;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/pagina-curso")
	public String paginaCurso() {
		return "pagina-curso";
	}
	@GetMapping("/pagina-curso2")
	public String paginaCurso2() {
		return "pagina-curso2";
	}
	@GetMapping("/pagina-curso3")
	public String paginaCurso3() {
		return "pagina-curso3";
	}
	@GetMapping("/pagina-cursos")
	public String paginaCursos() {
		return "pagina-cursos";
	}
	
	@GetMapping("/modulo2_redes")
	public String modulo2_redes() {
		return "modulo2_redes";
	}
	
	@GetMapping("/dois")
	public String dois() {
		return "dois";
	}
	
	@GetMapping("/H")
	public String H() {
		return "H";
	}
	@GetMapping("/2_pacote")
	public String pacote2() {
		return "2_pacote";
	}
	
	@GetMapping("/inicial")
	public String inicial() {
		return "inicial";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/users/living-room")
	public String livingRoom() {
		String home = "redirect:/users/index";

		User user = userService.getAuthenticatedUser();

		String principalRole = user.getPrincipalRole();
		Collection<Role> roles = user.getRoles();

		for (Role r : roles) {
			if (r.getName().equals("ROLE_ADMIN") && principalRole.equals("ROLE_ADMIN")) {
				home = "redirect:/admin/home";
			} else if (r.getName().equals("ROLE_USER") && principalRole.equals("ROLE_USER")) {
				home = "redirect:/users/home";
			}

		}

		return home;
	}

	@GetMapping("/users/home")
	public String homeUser(Model model) {

		String home = "home";
		User user = userService.getAuthenticatedUser();
		String username = user.getEmail();
		model.addAttribute("username", username);

		return "index";
	}

	@GetMapping("/users/perfil/{username}")
	public String showPerfilForm(@PathVariable("username") String username, ModelMap model) {

		UserDto userDto = new UserDto();
		userDto.setEmail(username);
		User user = userService.findByEmail(userDto.getEmail());
		model.addAttribute("user", user);

		return "update-registration";
	}

	@PostMapping("/users/perfil")
	public String updatePerfilAccount(@ModelAttribute("user") UserDto userDto) {

		User user = userService.update(userDto);

		System.out.println(user.getEnderecos().get(0).getId());
		System.out.println(user.getEnderecos().get(0).getCep());

		return "redirect:/users/perfil/" + user.getEmail();

	}

}
