package pl.michalstrzygowski.restapi.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime created_at;
}
