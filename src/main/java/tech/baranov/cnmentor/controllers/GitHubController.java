package tech.baranov.cnmentor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tech.baranov.cnmentor.models.GitHubEvent;
import tech.baranov.cnmentor.models.GitHubRepo;
import tech.baranov.cnmentor.models.GitHubUser;
import tech.baranov.cnmentor.services.GitHubService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping("/gh/user/{userName}")
    public GitHubUser getUser(@PathVariable String userName) {
        return gitHubService.fetchUser(userName);
    }

    @GetMapping("/gh/{userName}/repos")
    public List<GitHubRepo> getUserRepos(@PathVariable String userName) {
        return gitHubService.fetchRepos(userName);
    }

    @GetMapping("/gh/{gitHubUserId}/events")
    public List<GitHubEvent> getUserEvents(@PathVariable Integer gitHubUserId) {
        return gitHubService.getStudentEvents(gitHubUserId);
    }

    @GetMapping("/gh/{gitHubUserId}/repo/{gitHubRepoId}/events")
    public List<GitHubEvent> getUserEvents(@PathVariable Integer gitHubUserId, @PathVariable Integer gitHubRepoId) {
        return gitHubService.getStudentEvents(gitHubUserId, gitHubRepoId);
    }
}
