package razepl.dev.github.data;

import java.util.List;

public record GitRepository(String repositoryName, String ownerLogin, List<GitBranch> branches) {
}
