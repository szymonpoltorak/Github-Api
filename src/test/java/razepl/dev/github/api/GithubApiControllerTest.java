package razepl.dev.github.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import razepl.dev.github.data.GitRepository;

import java.util.List;

import static razepl.dev.github.constants.ApiMappings.GET_REPOSITORIES_MAPPING;
import static razepl.dev.github.constants.ApiMappings.GITHUB_API_MAPPING;

@SpringBootTest
@AutoConfigureMockMvc
class GithubApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final String API_MAPPING = String.format("%s%s", GITHUB_API_MAPPING, GET_REPOSITORIES_MAPPING);

    @Test
    final void test_getUsersRepositories_user_does_not_exist() throws Exception {
        // given
        var username = "hbjasdlhjasdghjlasdghjklasdhjklasd";

        // when
        mockMvc.perform(MockMvcRequestBuilders.get(API_MAPPING)
                        .param("username", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // then

    }

    @Test
    final void test_getUsersRepositories_application_xml_header() throws Exception {
        // given
        var username = "toursonlk";

        // when
        mockMvc.perform(MockMvcRequestBuilders.get(API_MAPPING)
                        .param("username", username)
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        // then

    }

    @Test
    final void test_getUsersRepositories_application_json_header() throws Exception {
        // given
        var username = "toursonlk";

        // when
        mockMvc.perform(MockMvcRequestBuilders.get(API_MAPPING)
                        .param("username", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
    }

    @Test
    final void test_getUsersRepositories_user_has_no_repositories() throws Exception {
        // given
        var username = "toursonlk";
        var expected = 0;

        // when
        var response = mockMvc.perform(MockMvcRequestBuilders.get(API_MAPPING)
                        .param("username", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        var result = getResultSetLength(response);

        // then
        Assertions.assertEquals(expected, result, "User has some repositories");
    }

    @Test
    final void test_getUsersRepositories_user_has_1_repository() throws Exception {
        // given
        var username = "igorkedzierawski";
        var expected = 1;

        // when
        var response = mockMvc.perform(MockMvcRequestBuilders.get(API_MAPPING)
                        .param("username", username)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var result = getResultSetLength(response);

        // then
        Assertions.assertEquals(expected, result, "User has " + result + " repositories");
    }

    private int getResultSetLength(String response) throws JsonProcessingException {
        return new ObjectMapper()
                .readValue(response, new TypeReference<List<GitRepository>>() {})
                .size();
    }
}
