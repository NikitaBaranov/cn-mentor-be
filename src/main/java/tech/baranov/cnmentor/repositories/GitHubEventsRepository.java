package tech.baranov.cnmentor.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.baranov.cnmentor.models.GitHubEvent;

import java.util.List;

public interface GitHubEventsRepository extends MongoRepository<GitHubEvent, String> {
    List<GitHubEvent> findGitHubEventsByActor_Id(Integer actorId);

    List<GitHubEvent> findGitHubEventsByActor_IdAndRepo_Id(Integer actorId, Integer repoId);
}
