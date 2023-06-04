package razepl.dev.github.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import razepl.dev.github.api.interfaces.GithubServiceInterface;
import razepl.dev.github.data.GitBranch;
import razepl.dev.github.data.GitRepository;
import razepl.dev.github.data.GithubBranchDto;
import razepl.dev.github.data.GithubRepoDto;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService implements GithubServiceInterface {
    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public final List<GitRepository> getUsersRepositories(String username) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = String.format("https://api.github.com/users/%s/repos", username);
        GithubRepoDto[] githubRepos = restTemplate.getForObject(url, GithubRepoDto[].class);

        if (githubRepos == null) {
            throw new RuntimeException("No repositories found for user: " + username);
        }

        return Arrays.stream(githubRepos)
                .filter(repo -> !repo.fork())
                .map(repo -> new GitRepository(repo.name(), repo.owner().login(),
                        findBranchesForRepo(repo.name(), username, restTemplate)))
                .toList();
    }

    private List<GitBranch> findBranchesForRepo(String repoName, String username, RestTemplate restTemplate) {
        String url = String.format("https://api.github.com/repos/%s/%s/branches", username, repoName);
        GithubBranchDto[] githubBranches = restTemplate.getForObject(url, GithubBranchDto[].class);

        if (githubBranches == null) {
            throw new RuntimeException("No branches found for repository: " + repoName);
        }
        return Arrays.stream(githubBranches).map(GithubBranchDto::toGitBranch).toList();
    }
}
