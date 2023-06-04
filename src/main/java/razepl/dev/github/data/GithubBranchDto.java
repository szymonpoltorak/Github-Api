package razepl.dev.github.data;

public record GithubBranchDto(String name, GithubCommitDto commit) {
    public GitBranch toGitBranch() {
        return new GitBranch(name, commit.sha());
    }
}
