package razepl.dev.github.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import razepl.dev.github.api.interfaces.GithubControllerInterface;
import razepl.dev.github.api.interfaces.GithubServiceInterface;
import razepl.dev.github.data.GitRepository;
import razepl.dev.github.exceptions.XmlHeaderException;

import java.util.List;

import static razepl.dev.github.constants.ApiMappings.GET_REPOSITORIES_MAPPING;
import static razepl.dev.github.constants.ApiMappings.GITHUB_API_MAPPING;
import static razepl.dev.github.constants.HttpHeaders.ACCEPT;
import static razepl.dev.github.constants.HttpHeaders.ACCEPT_XML;

@Slf4j
@RestController
@RequestMapping(value = GITHUB_API_MAPPING)
@RequiredArgsConstructor
public class GithubApiController implements GithubControllerInterface {
    private final GithubServiceInterface githubService;

    @Override
    @GetMapping(value = GET_REPOSITORIES_MAPPING)
    public final ResponseEntity<List<GitRepository>> getUsersRepositories(@RequestParam String username,
                                                                          @RequestHeader(ACCEPT) String acceptHeader) {
        try {
            if (acceptHeader.equals(ACCEPT_XML)) {
                throw new HttpMediaTypeNotAcceptableException("Application/xml header is not supported!");
            }
            log.info("Received request to get repositories for user: {}", username);

            return ResponseEntity.ok(githubService.getUsersRepositories(username));
        } catch (HttpMediaTypeNotAcceptableException exception) {
            throw new XmlHeaderException(exception.getMessage());
        }
    }
}
