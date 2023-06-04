package razepl.dev.github.api.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import razepl.dev.github.data.GitRepository;

import java.util.List;

public interface GithubControllerInterface {
    ResponseEntity<List<GitRepository>> getUsersRepositories(String username);
}
