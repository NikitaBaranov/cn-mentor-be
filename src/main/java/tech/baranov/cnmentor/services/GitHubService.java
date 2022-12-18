package tech.baranov.cnmentor.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.baranov.cnmentor.clients.GitHubClient;
import tech.baranov.cnmentor.models.GitHubEvent;
import tech.baranov.cnmentor.models.GitHubRepo;
import tech.baranov.cnmentor.models.GitHubUser;
import tech.baranov.cnmentor.repositories.GitHubEventsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final GitHubEventsRepository gitHubEventsRepository;
    private final GitHubClient gitHubClient;

    public GitHubUser fetchUser(String userName) {
        return gitHubClient.fetchUser(userName);
    }

    public List<GitHubRepo> fetchRepos(String userName) {
        return gitHubClient.fetchRepos(userName);
    }

    public List<GitHubEvent> getStudentEvents(Integer gitHubUserId) {
        return gitHubEventsRepository.findGitHubEventsByActor_Id(gitHubUserId);
    }

    public List<GitHubEvent> getStudentEvents(Integer gitHubUserId, Integer gitHubRepoId) {
        return gitHubEventsRepository.findGitHubEventsByActor_IdAndRepo_Id(gitHubUserId, gitHubRepoId);
    }

    public List<GitHubEvent> updateGitHubEvents(String userName) {
        return gitHubEventsRepository.saveAll(gitHubClient.fetchGitHubEvents(userName));
    }

}
