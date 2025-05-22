package com.project.pm360.controller;

import com.project.pm360.model.Investment;
import com.project.pm360.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/investments")
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;

    @GetMapping
    public String listInvestments(Model model) {
        model.addAttribute("investments", investmentService.getAllInvestments());
        return "index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("investment", new Investment());
        return "add-investment";
    }

    @PostMapping
    public String saveInvestment(@ModelAttribute Investment investment) {
        investmentService.saveInvestment(investment);
        return "redirect:/investments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Investment investment = investmentService.getInvestmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid investment ID"));
        model.addAttribute("investment", investment);
        return "edit-investment";
    }

    @PostMapping("/update/{id}")
    public String updateInvestment(@PathVariable Long id, @ModelAttribute Investment investment) {
        investment.setId(id);
        investmentService.saveInvestment(investment);
        return "redirect:/investments";
    }

    @GetMapping("/delete/{id}")
    public String deleteInvestment(@PathVariable Long id) {
        investmentService.deleteInvestment(id);
        return "redirect:/investments";
    }
}