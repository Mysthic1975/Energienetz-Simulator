package com.example.energienetzsimulator.controller;

import com.example.energienetzsimulator.entity.EnergySource;
import com.example.energienetzsimulator.service.EnergyNetworkService;
import com.example.energienetzsimulator.service.EnergySourceService;
import com.example.energienetzsimulator.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/energy-sources")
@RequiredArgsConstructor
public class EnergySourceController {

    private final EnergySourceService energySourceService;
    private final EnergyNetworkService energyNetworkService;
    private final ProviderService providerService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("energySource", new EnergySource());
        model.addAttribute("energyNetworks", energyNetworkService.getAllEnergyNetworks());
        model.addAttribute("providers", providerService.getAllProviders());
        return "energy-source/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute EnergySource energySource) {
        energySourceService.createEnergySource(energySource);
        return "redirect:/energy-sources/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("energySources", energySourceService.getAllEnergySources());
        return "energy-source/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        EnergySource energySource = energySourceService.getEnergySourceById(id);
        if (energySource != null) {
            model.addAttribute("energySource", energySource);
            model.addAttribute("energyNetworks", energyNetworkService.getAllEnergyNetworks());
            model.addAttribute("providers", providerService.getAllProviders());
            return "energy-source/edit";
        } else {
            return "redirect:/energy-sources/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute EnergySource updatedEnergySource) {
        EnergySource editedEnergySource = energySourceService.updateEnergySource(id, updatedEnergySource);
        return "redirect:/energy-sources/list";
    }

    @PostMapping("/charge/{id}")
    public String charge(@PathVariable Long id, @RequestParam("chargeAmount") double chargeAmount) {
        EnergySource chargedEnergySource = energySourceService.chargeEnergySource(id, chargeAmount);
        return "redirect:/energy-sources/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        energySourceService.deleteEnergySource(id);
        return "redirect:/energy-sources/list";
    }

    @PostMapping("/assign-network/{energySourceId}/{networkId}")
    public String assignToNetwork(
            @PathVariable Long energySourceId,
            @PathVariable Long networkId) {
        EnergySource assignedEnergySource = energyNetworkService.assignEnergySourceToNetwork(energySourceId, networkId);
        return "redirect:/energy-sources/list";
    }

    @PostMapping("/assign-provider/{energySourceId}/{providerId}")
    public String assignToProvider(
            @PathVariable Long energySourceId,
            @PathVariable Long providerId) {
        EnergySource assignedEnergySource = energySourceService.assignProviderToEnergySource(energySourceId, providerId);
        return "redirect:/energy-sources/list";
    }
}