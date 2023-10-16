package com.example.energienetzsimulator.controller;

import com.example.energienetzsimulator.entity.Consumer;
import com.example.energienetzsimulator.service.ConsumerService;
import com.example.energienetzsimulator.service.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/consumers")
public class ConsumerController {

    private final ConsumerService consumerService;
    private final ConsumptionService consumptionService;

    @Autowired
    public ConsumerController(ConsumerService consumerService, ConsumptionService consumptionService) {
        this.consumerService = consumerService;
        this.consumptionService = consumptionService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("consumer", new Consumer());
        return "consumer/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Consumer consumer) {
        consumerService.createConsumer(consumer);
        return "redirect:/consumers/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("consumers", consumerService.getAllConsumers());
        return "consumer/list";
    }

    @GetMapping("/{consumerId}/current-consumption")
    public String showCurrentConsumption(@PathVariable Long consumerId, Model model) {
        // Hier den aktuellen Verbrauch für den Verbraucher aus der Datenbank abrufen
        double currentConsumption = consumptionService.getCurrentConsumptionForConsumer(consumerId);

        // Den aktuellen Verbrauch an die Ansicht übergeben
        model.addAttribute("currentConsumption", currentConsumption);

        return "consumer/current-consumption";
    }

    @PostMapping("/{consumerId}/current-consumption")
    public String updateCurrentConsumption(@PathVariable Long consumerId, @RequestParam Double newConsumption) {
        // Hier den aktuellen Verbrauch für den Verbraucher aktualisieren (z. B. im Service oder direkt in der Datenbank)

        return "redirect:/consumers/list";
    }

}

