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

    @GetMapping("/code")
    public String getCode() {

        HashMap<String,HashSet<String>> list = new HashMap<>();
        String letter ;

        char[][] triplets = {
                {'t','u','p'},
                {'w','h','i'},
                {'t','s','u'},
                {'a','t','s'},
                {'h','a','p'},
                {'t','i','s'},
                {'w','h','s'}
        };
        for(int i = 0; i < triplets.length; i++) {
            for(int j = 0; j < triplets[i].length; j++) {
                letter = String.valueOf(triplets[i][j]);
                if(!list.containsKey(letter)){
                    list.put(String.valueOf(triplets[i][j]), new HashSet<>());
                }
                if(j == 1){
                   list.get(letter).add(String.valueOf(triplets[i][j-1]));
                }
                if(j == 2){
                    list.get(letter).add(String.valueOf(triplets[i][j-1]));
                    list.get(letter).add(String.valueOf(triplets[i][j-2]));

                }
            }
        }
        String[] list2 = new String[list.size()];
        list.forEach((key, value) ->{
              if( value.isEmpty()){
                  list2[0] = key;
            }
        });
        System.out.println(list2);

        return list2.toString();
    }
}
