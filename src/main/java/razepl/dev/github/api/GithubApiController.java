package razepl.dev.github.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import razepl.dev.github.api.interfaces.GithubControllerInterface;
import razepl.dev.github.api.interfaces.GithubServiceInterface;
import razepl.dev.github.data.GitRepository;

import java.util.List;

import static razepl.dev.github.constants.ApiMappings.GET_REPOSITORIES_MAPPING;
import static razepl.dev.github.constants.ApiMappings.GITHUB_API_MAPPING;
import static razepl.dev.github.constants.HttpHeaders.ACCEPT;

@Slf4j
@RestController
@RequestMapping(value = GITHUB_API_MAPPING)
@RequiredArgsConstructor
public class GithubApiController implements GithubControllerInterface {
    private final GithubServiceInterface githubService;

    @Override
    @GetMapping(value = GET_REPOSITORIES_MAPPING)
    public final List<GitRepository> getUsersRepositories(@RequestParam String username,
                                                          @RequestHeader(ACCEPT) String acceptHeader) {
        log.info("Received request to get repositories for user: {}", username);

        return githubService.getUsersRepositories(username, acceptHeader);
    }
}
