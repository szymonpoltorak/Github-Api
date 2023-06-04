package razepl.dev.github.data;

public record GithubRepoDto(GithubUserDto owner, String name, boolean fork) {
}
