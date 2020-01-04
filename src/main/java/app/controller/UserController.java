package app.controller;

import app.model.names.ModelNames;
import app.model.names.TemplateNames;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping(value = {"/usersList"})
    public ModelAndView getAllUsers(ModelAndView modelAndView) {
        modelAndView.addObject(ModelNames.ALL_USERS_MODEL_NAME, userService.findAll());
        modelAndView.setViewName(TemplateNames.USER_LIST_TEMPLATE_NAME);
        return modelAndView;
    }
}
