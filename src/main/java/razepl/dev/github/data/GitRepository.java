package razepl.dev.github.data;

import lombok.Builder;

import java.util.List;

@Builder
public record GitRepository(String repositoryName, String ownerLogin, List<GitBranch> branches) {
}
