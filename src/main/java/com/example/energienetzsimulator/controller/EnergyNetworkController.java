package com.example.energienetzsimulator.controller;

import com.example.energienetzsimulator.entity.EnergyNetwork;
import com.example.energienetzsimulator.service.EnergyNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/energy-networks")
@RequiredArgsConstructor
public class EnergyNetworkController {
    private final EnergyNetworkService service;

    @GetMapping("/create")
    public String showCreateForm() {
        return "energy-network/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute EnergyNetwork energyNetwork) {
        EnergyNetwork createdEnergyNetwork = service.createEnergyNetwork(energyNetwork);
        return "redirect:/energy-networks/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("energyNetworks", this.service.getAllEnergyNetworks());
        return "energy-network/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        EnergyNetwork energyNetwork = service.getEnergyNetworkById(id);
        model.addAttribute("energyNetwork", energyNetwork);
        return "energy-network/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute EnergyNetwork energyNetwork) {
        EnergyNetwork updatedEnergyNetwork = service.updateEnergyNetwork(id, energyNetwork);
        return "redirect:/energy-networks/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteEnergyNetwork(id);
        return "redirect:/energy-networks/list";
    }

    @GetMapping("/total-energy/{networkId}")
    public String showTotalEnergyForNetwork(@PathVariable Long networkId, Model model) {
        double totalEnergy = service.calculateTotalEnergyInNetwork(networkId);
        model.addAttribute("totalEnergy", totalEnergy);

        EnergyNetwork energyNetwork = service.getEnergyNetworkById(networkId);
        model.addAttribute("networkName", energyNetwork.getName());

        return "energy-network/total-energy";
    }
}
