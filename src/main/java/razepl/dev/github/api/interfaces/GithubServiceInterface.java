package razepl.dev.github.api.interfaces;

import razepl.dev.github.data.GitRepository;

import java.util.List;

public interface GithubServiceInterface {
    List<GitRepository> getUsersRepositories(String username);
}
