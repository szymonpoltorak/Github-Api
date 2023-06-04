package razepl.dev.github.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/github")
@RequiredArgsConstructor
public class GithubApiController {
    @GetMapping(value = "/test")
    ResponseEntity<String> test() {
        log.info("Test endpoint has been called!");

        return ResponseEntity.ok("Test endpoint has been called!");
    }
}
