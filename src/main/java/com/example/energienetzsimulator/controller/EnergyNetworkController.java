package com.example.energienetzsimulator.controller;

import com.example.energienetzsimulator.entity.EnergyNetwork;
import com.example.energienetzsimulator.service.EnergyNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/energy-network")
public class EnergyNetworkController {
    private final EnergyNetworkService service;

    @Autowired
    public EnergyNetworkController(EnergyNetworkService service) {
        this.service = service;
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "energy-network/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute EnergyNetwork energyNetwork) {
        service.createEnergyNetwork(energyNetwork);
        return "redirect:/energy-network/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("energyNetworks", this.service.getAllEnergyNetworks());
        return "energy-network/list";
    }

    @GetMapping("/total-energy/{networkId}")
    public String showTotalEnergyForNetwork(@PathVariable Long networkId, Model model) {
        double totalEnergy = service.calculateTotalEnergyInNetwork(networkId);
        model.addAttribute("totalEnergy", totalEnergy);

        EnergyNetwork energyNetwork = service.getEnergyNetworkById(networkId);
        model.addAttribute("networkName", energyNetwork.getName());

        return "energy-network/total-energy";
    }

    @GetMapping("/dashboard") // test
    public String showDashboard(Model model) {

        Long networkId = 1L;

        double totalCurrentStorage = service.calculateTotalCurrentStorageInNetwork(networkId);

        model.addAttribute("totalCurrentStorage", totalCurrentStorage);

        return "index";
    }
}

