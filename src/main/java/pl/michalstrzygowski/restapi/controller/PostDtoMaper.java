package pl.michalstrzygowski.restapi.controller;

import pl.michalstrzygowski.restapi.controller.dto.PostDto;
import pl.michalstrzygowski.restapi.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostDtoMaper {

    private PostDtoMaper() {
    }
    public static List<PostDto> mapToPostDtos(List<Post> posts) {
        return posts.stream()
                .map(post -> mapToPostDto(post))
                .collect(Collectors.toList());
    }

    public static PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .created_at(post.getCreated_at())
                .build();
    }

}
