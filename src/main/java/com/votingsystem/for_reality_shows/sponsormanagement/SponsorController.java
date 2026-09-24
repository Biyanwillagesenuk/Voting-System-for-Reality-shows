package com.votingsystem.for_reality_shows.sponsormanagement;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sponsors")
public class SponsorController {

    private final SponsorService sponsorService;

    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @GetMapping
    public String listSponsors(Model model) {
        model.addAttribute("sponsors", sponsorService.getAll());
        return "sponsor/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("sponsor", new Sponsor());
        model.addAttribute("tiers", SponsorTier.values());
        model.addAttribute("statuses", PaymentStatus.values());
        return "sponsor/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("sponsor", sponsorService.getById(id));
        model.addAttribute("tiers", SponsorTier.values());
        model.addAttribute("statuses", PaymentStatus.values());
        return "sponsor/form";
    }

    @GetMapping("/{id}")
    public String viewSponsor(@PathVariable Long id, Model model) {
        model.addAttribute("sponsor", sponsorService.getById(id));
        return "sponsor/view";
    }

    @PostMapping
    public String saveSponsor(@Valid @ModelAttribute("sponsor") Sponsor sponsor,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tiers", SponsorTier.values());
            model.addAttribute("statuses", PaymentStatus.values());
            return "sponsor/form";
        }
        sponsorService.save(sponsor);
        return "redirect:/sponsors";
    }

    @PostMapping("/{id}/deactivate")
    public String deactivateSponsor(@PathVariable Long id) {
        sponsorService.deactivate(id);
        return "redirect:/sponsors";
    }

    @PostMapping("/{id}/delete")
    public String deleteSponsor(@PathVariable Long id) {
        sponsorService.delete(id);
        return "redirect:/sponsors";
    }
}
