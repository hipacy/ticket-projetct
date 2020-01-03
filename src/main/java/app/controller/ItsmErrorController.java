package app.controller;

import app.model.names.TemplateNames;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItsmErrorController implements ErrorController {

    @GetMapping(value = "/error")
    public String error() {
        return TemplateNames.ACCESS_DENIED_ERROR_TEMPLATE_NAME;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
