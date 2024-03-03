package pl.michalstrzygowski.restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.michalstrzygowski.restapi.controller.PostDtoMaper;
import pl.michalstrzygowski.restapi.controller.dto.PostDto;
import pl.michalstrzygowski.restapi.model.Comment;
import pl.michalstrzygowski.restapi.model.Post;
import pl.michalstrzygowski.restapi.repository.CommentRepository;
import pl.michalstrzygowski.restapi.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private static final int PAGE_SIZE =3;

    public List<Post> getPosts(int page) {

        List<Post>  posts= postRepository.findAllPosts(PageRequest.of(page, PAGE_SIZE));
        List<Long> postsIds = posts.stream().map(Post::getId).collect(Collectors.toList());
        List<Comment> comments = commentRepository.findAllByPostIdIn(postsIds);
        posts.forEach(post -> post.setComments(
                comments.stream()
                        .filter(comment -> comment.getPostId().equals(post.getId()))
                        .collect(Collectors.toList())
        ));
        return posts;
    }




    public Post getPostById(Long id) {
        Post post =  postRepository.findById(id).orElseThrow();
        return post;

    }
}
