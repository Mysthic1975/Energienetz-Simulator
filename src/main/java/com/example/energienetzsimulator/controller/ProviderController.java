package com.example.energienetzsimulator.controller;

import com.example.energienetzsimulator.entity.Provider;
import com.example.energienetzsimulator.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("provider", new Provider());
        return "provider/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Provider provider) {
        providerService.createProvider(provider);
        return "redirect:/providers/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("providers", providerService.getAllProviders());
        return "provider/list";
    }
}

