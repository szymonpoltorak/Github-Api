package razepl.dev.github.api.interfaces;

import org.springframework.http.ResponseEntity;
import razepl.dev.github.data.GitRepository;

import java.util.List;

public interface GithubControllerInterface {
    ResponseEntity<List<GitRepository>> getUsersRepositories(String username, String acceptHeader);
}
