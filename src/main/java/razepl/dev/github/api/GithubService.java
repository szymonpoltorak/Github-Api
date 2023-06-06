package razepl.dev.github.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import razepl.dev.github.api.interfaces.GithubServiceInterface;
import razepl.dev.github.data.GitBranch;
import razepl.dev.github.data.GitRepository;
import razepl.dev.github.data.GithubBranchDto;
import razepl.dev.github.data.GithubRepoDto;
import razepl.dev.github.exceptions.UserDoesNotExistException;
import razepl.dev.github.exceptions.XmlHeaderException;

import java.util.Collections;
import java.util.List;

import static razepl.dev.github.constants.GithubApiFormats.*;
import static razepl.dev.github.constants.HttpHeaders.ACCEPT_XML;

@Slf4j
@Service
public class GithubService implements GithubServiceInterface {
    @Override
    public final List<GitRepository> getUsersRepositories(String username, String acceptHeader) {
        if (acceptHeader.equals(ACCEPT_XML)) {
            throw new XmlHeaderException("Application/xml header is not supported!");
        }
        var githubRepos = getReposList(username);

        if (githubRepos == null) {
            log.info("User {} does not have any repositories", username);

            return Collections.emptyList();
        }
        log.info("User {} has {} repositories", username, githubRepos.size());

        return githubRepos
                .stream()
                .filter(repo -> !repo.fork())
                .map(repo -> GitRepository.builder()
                        .repositoryName(repo.name())
                        .ownerLogin(repo.owner().login())
                        .branches(findBranchesForRepo(repo.name(), username))
                        .build()
                )
                .toList();
    }

    private List<GithubRepoDto> getReposList(String username) {
        var webClient = WebClient.create();
        String userUrl = String.format(GITHUB_USER_URL_FORMAT, username);
        String url = String.format(GITHUB_REPO_URL_FORMAT, username);

        webClient.get().uri(userUrl)
                .retrieve().onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> {
                    throw new UserDoesNotExistException("User does not exist");
                }).bodyToMono(String.class).block();

        return webClient.get().uri(url).retrieve().bodyToFlux(GithubRepoDto.class).collectList().block();
    }

    private List<GitBranch> findBranchesForRepo(String repoName, String username) {
        var webClient = WebClient.create();
        String url = String.format(GITHUB_BRANCH_URL_FORMAT, username, repoName);

        var githubBranches = webClient.get().uri(url).retrieve()
                .bodyToFlux(GithubBranchDto.class).collectList().block();

        if (githubBranches == null) {
            return Collections.emptyList();
        }
        return githubBranches.stream().map(GithubBranchDto::toGitBranch).toList();
    }
}
