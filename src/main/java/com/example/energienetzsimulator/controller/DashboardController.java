package com.example.energienetzsimulator.controller;

import com.example.energienetzsimulator.entity.Consumer;
import com.example.energienetzsimulator.entity.EnergyNetwork;
import com.example.energienetzsimulator.entity.EnergySource;
import com.example.energienetzsimulator.repository.ConsumerRepository;
import com.example.energienetzsimulator.repository.EnergyNetworkRepository;
import com.example.energienetzsimulator.repository.EnergySourceRepository;
import com.example.energienetzsimulator.service.SimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final EnergyNetworkRepository energyNetworkRepository;
    private final EnergySourceRepository energySourceRepository;
    private final ConsumerRepository consumerRepository;
    private final SimulationService simulationService;

    @GetMapping("/")
    public String showDashboard(@RequestParam(name = "networkId", required = false) Long networkId, Model model) {
        List<EnergyNetwork> allNetworks = energyNetworkRepository.findAll();
        model.addAttribute("allNetworks", allNetworks);

        EnergyNetwork selectedNetwork = null;
        if (networkId != null) {
            selectedNetwork = energyNetworkRepository.findById(networkId).orElse(null);
        } else if (!allNetworks.isEmpty()) {
            selectedNetwork = allNetworks.get(0);
        }

        if (selectedNetwork != null) {
            List<EnergySource> sources = energySourceRepository.findByEnergyNetworkId(selectedNetwork.getId());
            List<Consumer> consumers = consumerRepository.findAll(); // Vereinfachung
            double totalDemand = consumers.stream()
                    .mapToDouble(c -> c.getExpectedAnnualUsage() / 8760)
                    .sum();

            model.addAttribute("selectedNetwork", selectedNetwork);
            model.addAttribute("sources", sources);
            model.addAttribute("totalDemand", totalDemand);
        } else {
            model.addAttribute("selectedNetwork", null);
            model.addAttribute("sources", Collections.emptyList());
            model.addAttribute("totalDemand", 0.0);
        }

        return "index";
    }

    @PostMapping("/simulate")
    public String simulate(@RequestParam(name = "networkId") Long networkId) {
        simulationService.simulateNetworkTick(networkId);
        return "redirect:/?networkId=" + networkId;
    }
}
