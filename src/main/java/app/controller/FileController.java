package app.controller;

import app.exceptions.StorageException;
import app.model.names.ModelNames;
import app.model.names.TemplateNames;
import app.service.StorageService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.NotNull;

@Controller
public class FileController {


    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;


    @PostMapping(value = "/uploadFile-{domain}-{id}", consumes = {"multipart/form-data"})
    public String upload(@PathVariable @NotNull String domain, @PathVariable Integer id,
                         @RequestParam MultipartFile file,
                         RedirectAttributes rr) {
        storageService.uploadImage(file, domain, userService.findUsersByUserId(id).getEmail());

        rr.addFlashAttribute(ModelNames.SUCCESS_MESSAGE_MODEL_NAME, "File has been uploaded!");
        return TemplateNames.REDIRECT_PREFIX + TemplateNames.PROFILE_TEMPLATE_NAME + "-" + id;


    }

    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException e, RedirectAttributes rr) {

        rr.addFlashAttribute(ModelNames.ERROR_MESSAGE_MODEL_NAME, "Couldn't be uploaded! Error: " + e.toString());

        return TemplateNames.REDIRECT_PREFIX + TemplateNames.PROFILE_TEMPLATE_NAME;
    }
}

