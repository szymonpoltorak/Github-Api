package razepl.dev.github.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import razepl.dev.github.api.interfaces.GithubServiceInterface;
import razepl.dev.github.data.*;
import razepl.dev.github.exceptions.UserDoesNotExistException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static razepl.dev.github.constants.GithubApiFormats.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService implements GithubServiceInterface {
    private final RestTemplateBuilder restTemplateBuilder;

    @Override
    public final List<GitRepository> getUsersRepositories(String username) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String userUrl = String.format(GITHUB_USER_URL_FORMAT, username);
        String url = String.format(GITHUB_REPO_URL_FORMAT, username);

        try {
            restTemplate.getForObject(userUrl, GithubUserDto.class);
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserDoesNotExistException("User does not exist");
            }
        }
        GithubRepoDto[] githubRepos = restTemplate.getForObject(url, GithubRepoDto[].class);

        if (githubRepos == null) {
            log.info("User {} does not have any repositories", username);

            return Collections.emptyList();
        }
        log.info("User {} has {} repositories", username, githubRepos.length);

        return Arrays.stream(githubRepos)
                .filter(repo -> !repo.fork())
                .map(repo -> GitRepository.builder()
                        .repositoryName(repo.name())
                        .ownerLogin(repo.owner().login())
                        .branches(findBranchesForRepo(repo.name(), username, restTemplate))
                        .build()
                )
                .toList();
    }

    private List<GitBranch> findBranchesForRepo(String repoName, String username, RestTemplate restTemplate) {
        String url = String.format(GITHUB_BRANCH_URL_FORMAT, username, repoName);
        GithubBranchDto[] githubBranches = restTemplate.getForObject(url, GithubBranchDto[].class);

        if (githubBranches == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(githubBranches).map(GithubBranchDto::toGitBranch).toList();
    }
}
