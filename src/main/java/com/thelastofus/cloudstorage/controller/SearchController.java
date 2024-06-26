package com.thelastofus.cloudstorage.controller;

import com.thelastofus.cloudstorage.service.StorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchController {


    private static final String SEARCH = "/search";

    StorageService storageService;

    @ModelAttribute
    public void addAttributes(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
    }

    @GetMapping(SEARCH)
    public String searchObjects(@RequestParam(value = "query", required = false, defaultValue = "") String query,
                                Principal principal, Model model) {
        model.addAttribute("storageSearchObjects", storageService.search(query, principal));
        log.debug("files and folders successfully find");

        return "storage/search";
    }
}
