package com.group07.controller;

import com.group07.entity.ResultSummary;
import com.group07.service.VoteProcessingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WebController {
    private final VoteProcessingService voteProcessingService;

    public WebController(VoteProcessingService voteProcessingService) {
        this.voteProcessingService = voteProcessingService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(defaultValue = "1") Long episodeId, Model model) {
        List<ResultSummary> leaderboard = voteProcessingService.getLiveLeaderboard(episodeId);
        model.addAttribute("episodeId", episodeId);
        model.addAttribute("leaderboard", leaderboard);
        return "dashboard";
    }

    // HANDLES BOTH CREATE & EDIT ACTIONS: Performs backend validation checks before processing
    @PostMapping("/dashboard/update-score")
    public String updateScoreFromUI(@RequestParam Long candidateId,
                                    @RequestParam Long episodeId,
                                    @RequestParam Double judgeScore,
                                    RedirectAttributes redirectAttributes) {
        if (candidateId == null || candidateId < 1 || candidateId > 100) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Contestant ID must be between 1 and 100.");
            return "redirect:/dashboard?episodeId=" + episodeId;
        }
        if (judgeScore == null || judgeScore < 1.0 || judgeScore > 10.0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Score must be a decimal between 1.0 and 10.0.");
            return "redirect:/dashboard?episodeId=" + episodeId;
        }

        voteProcessingService.updateJudgeScore(candidateId, episodeId, judgeScore);
        redirectAttributes.addFlashAttribute("successMessage", "Score processed successfully!");
        return "redirect:/dashboard?episodeId=" + episodeId;
    }

    // NEW INDEPENDENT FEATURE: Route mapping for target item removal forms
    @PostMapping("/dashboard/delete-contestant")
    public String deleteContestantFromUI(@RequestParam Long candidateId,
                                         @RequestParam Long episodeId,
                                         RedirectAttributes redirectAttributes) {
        voteProcessingService.deleteCandidateData(candidateId, episodeId);
        redirectAttributes.addFlashAttribute("successMessage", "Contestant " + candidateId + " removed successfully.");
        return "redirect:/dashboard?episodeId=" + episodeId;
    }

    @PostMapping("/dashboard/purge")
    public String purgeEpisodeFromUI(@RequestParam Long episodeId) {
        voteProcessingService.purgeEpisodeData(episodeId);
        return "redirect:/dashboard?episodeId=" + episodeId;
    }

    @GetMapping("/test")
    @ResponseBody
    public String testRoute() {
        return "Controller is working!";
    }
}
