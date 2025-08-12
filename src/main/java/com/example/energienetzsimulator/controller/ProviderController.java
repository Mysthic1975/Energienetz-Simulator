package com.example.energienetzsimulator.controller;

import com.example.energienetzsimulator.entity.Provider;
import com.example.energienetzsimulator.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("provider", new Provider());
        return "provider/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Provider provider) {
        Provider createdProvider = providerService.createProvider(provider);
        return "redirect:/providers/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("providers", providerService.getAllProviders());
        return "provider/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Provider provider = providerService.getProviderById(id);
        model.addAttribute("provider", provider);
        return "provider/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Provider provider) {
        Provider updatedProvider = providerService.updateProvider(id, provider);
        return "redirect:/providers/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return "redirect:/providers/list";
    }
}
