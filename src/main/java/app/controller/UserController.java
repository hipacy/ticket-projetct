package app.controller;

import app.model.Users;
import app.model.names.ModelNames;
import app.model.names.TemplateNames;
import app.service.TeamService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

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

    @GetMapping(value = {"/editUser-{id}"})
    public ModelAndView editUser(ModelAndView modelAndView, @PathVariable Integer id) {
        modelAndView.addObject(ModelNames.USER_MODEL_NAME, userService.findUsersByUserId(id));
        modelAndView.addObject(ModelNames.EDIT_MODEL_NAME, true);

        modelAndView.setViewName(TemplateNames.PROFILE_TEMPLATE_NAME);
        return modelAndView;
    }

    @PostMapping(value = {"/editUser-{id}"})
    public ModelAndView editUser(ModelAndView modelAndView,
                                 @PathVariable Integer id,
                                 Users user,
                                 BindingResult br, RedirectAttributes rr) {

        userService.updateUser(user, id);

        if (!br.hasErrors()) {
            rr.addFlashAttribute(ModelNames.SUCCESS_MESSAGE_MODEL_NAME, "User edited successfully!");
        } else {
            rr.addFlashAttribute(ModelNames.ERROR_MESSAGE_MODEL_NAME, "User couldn't be edited, try again.");
        }

        rr.addFlashAttribute(ModelNames.EDIT_MODEL_NAME, false);

        modelAndView.setViewName(TemplateNames.REDIRECT_PREFIX + TemplateNames.PROFILE_TEMPLATE_NAME + "-" + id);
        return modelAndView;
    }

    @GetMapping(value = {"/usersList"})
    public ModelAndView getAllUsers(ModelAndView modelAndView) {
        modelAndView.addObject(ModelNames.ALL_USERS_MODEL_NAME, userService.findAll());
        modelAndView.setViewName(TemplateNames.USER_LIST_TEMPLATE_NAME);
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView registration(ModelAndView modelAndView) {
        modelAndView.addObject(ModelNames.EDIT_MODEL_NAME, false);
        modelAndView.addObject(ModelNames.AVAILABLE_TEAMS_MODEL_NAME, teamService.findAllTeams());
        modelAndView.addObject(ModelNames.USER_MODEL_NAME, new Users());

        modelAndView.setViewName(TemplateNames.REGISTRATION_TEMPLATE_NAME);
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(ModelAndView modelAndView, @Valid Users user, BindingResult bindingResult) {
        modelAndView.addObject(ModelNames.ALL_USERS_MODEL_NAME, teamService.findAllTeams());
        modelAndView.addObject(ModelNames.EDIT_MODEL_NAME, false);

        Optional.ofNullable(userService.findUserByEmail(user.getEmail())).ifPresent(
                u -> bindingResult.rejectValue("email", "error.user", "The email address is in use already!"));


        if (bindingResult.hasErrors()) {
            modelAndView.addObject(ModelNames.ERROR_MESSAGE_MODEL_NAME, "Fill in all fields!!");
            modelAndView.setViewName(TemplateNames.REGISTRATION_TEMPLATE_NAME);
        } else {
            userService.saveNewUser(user);
            modelAndView.addObject(ModelNames.SUCCESS_MESSAGE_MODEL_NAME, "User has been registered!");
            modelAndView.addObject(ModelNames.USER_MODEL_NAME, new Users());
            modelAndView.setViewName(TemplateNames.REGISTRATION_TEMPLATE_NAME);

        }
        return modelAndView;
    }

}