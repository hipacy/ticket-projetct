package app.controller;

import app.model.names.ModelNames;
import app.model.names.TemplateNames;
import app.service.TeamService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @GetMapping(value = {"/profile"})
    public ModelAndView getUser(ModelAndView modelAndView) {
        modelAndView.addObject(ModelNames.USER_MODEL_NAME, userService.findUserByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()));
        modelAndView.addObject(ModelNames.EDIT_MODEL_NAME, false);

        modelAndView.setViewName(TemplateNames.PROFILE_TEMPLATE_NAME);
        return modelAndView;
    }

    @GetMapping(value = {"/profile-{id}"})
    public ModelAndView getUserById(ModelAndView modelAndView, @PathVariable Integer id) {
        modelAndView.addObject(ModelNames.USER_MODEL_NAME, userService.findUsersByUserId(id));
        modelAndView.addObject(ModelNames.EDIT_MODEL_NAME, false);

        modelAndView.setViewName(TemplateNames.PROFILE_TEMPLATE_NAME);
        return modelAndView;
    }

    @GetMapping(value = {"/usersList"})
    public ModelAndView getAllUsers(ModelAndView modelAndView) {
        modelAndView.addObject(ModelNames.ALL_USERS_MODEL_NAME, userService.findAll());
        modelAndView.setViewName(TemplateNames.USER_LIST_TEMPLATE_NAME);
        return modelAndView;
    }
}
