package pl.michalstrzygowski.restapi.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.michalstrzygowski.restapi.controller.dto.PostDto;
import pl.michalstrzygowski.restapi.model.Post;
import pl.michalstrzygowski.restapi.service.PostService;

import java.sql.Array;
import java.util.*;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping("/posts")
    public List<Post> getPosts(@RequestParam(required = false) Integer page) {

        int pageNumber =  page != null && page >= 0 ? page : 0 ;
       return postService.getPosts(pageNumber);
    }

    @GetMapping("/posts/{id}")
    public Post getPostById(@Parameter(name = "id") @PathVariable Long id) {
        return postService.getPostById(id);
    }
}
