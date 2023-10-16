package com.example.energienetzsimulator.controller;

import com.example.energienetzsimulator.entity.EnergySource;
import com.example.energienetzsimulator.service.EnergyNetworkService;
import com.example.energienetzsimulator.service.EnergySourceService;
import com.example.energienetzsimulator.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/energy-sources")
public class EnergySourceController {

    private final EnergySourceService energySourceService;
    private final EnergyNetworkService energyNetworkService;
    private final ProviderService providerService;

    @Autowired
    public EnergySourceController(EnergySourceService energySourceService, EnergyNetworkService energyNetworkService, ProviderService providerService) {
        this.energySourceService = energySourceService;
        this.energyNetworkService = energyNetworkService;
        this.providerService = providerService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("energySource", new EnergySource());
        model.addAttribute("energyNetworks", energyNetworkService.getAllEnergyNetworks());
        model.addAttribute("providers", providerService.getAllProviders());
        model.addAttribute("networkid", 0L);
        model.addAttribute("providerid", 0L);
        return "energy-source/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute EnergySource energySource) {


        energySourceService.createEnergySource(energySource);
        System.out.println("Creating Energy Source from Controller: " + energySource);

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
            model.addAttribute("energyNetwork", energySource.getEnergyNetwork());
            return "energy-source/edit";
        } else {
            return "redirect:/energy-sources/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute EnergySource updatedEnergySource) {
        EnergySource editedEnergySource = energySourceService.updateEnergySource(id, updatedEnergySource);
        if (editedEnergySource != null) {
            return "redirect:/energy-sources/list";
        } else {
            return "redirect:/energy-sources/edit/" + id;
        }
    }

    @PostMapping("/charge/{id}")
    public String charge(@PathVariable Long id, @RequestParam("chargeAmount") double chargeAmount) {
        EnergySource chargedEnergySource = energySourceService.chargeEnergySource(id, chargeAmount);
        if (chargedEnergySource != null) {
            return "redirect:/energy-sources/list";
        } else {
            return "redirect:/energy-sources/edit/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        energySourceService.deleteEnergySource(id);
        return "redirect:/energy-sources/list";
    }

    // Methode zum Zuweisen einer Energiequelle zu einem Energie-Netzwerk
    @PostMapping("/assign/{energySourceId}/{networkId}")
    public String assignToNetwork(
            @PathVariable Long energySourceId,
            @PathVariable Long networkId) {
        energyNetworkService.assignEnergySourceToNetwork(energySourceId, networkId);
        return "redirect:/energy-sources/list";
    }

    @PostMapping("/assign-provider/{energySourceId}/{providerId}")
    public String assignToProvider(
            @PathVariable Long energySourceId,
            @PathVariable Long providerId) {
        providerService.assignEnergySourceToProvider(energySourceId, providerId);
        return "redirect:/energy-sources/list";
    }
}