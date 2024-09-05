package com.microservice.comment.comment.service;

import com.microservice.comment.comment.config.RestTempleteConfig;
import com.microservice.comment.comment.entity.Comment;
import com.microservice.comment.comment.payload.Post;
import com.microservice.comment.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RestTempleteConfig restTemplete;

    public Comment saveComment (Comment comment){
        Post post=restTemplete.getRestTemplate().getForObject("http://POST-SERVICE/api/posts/"+comment.getPostId(), Post.class);
        if(post!=null){
            String commentId = UUID.randomUUID().toString();
            comment.setId(commentId);
            Comment savedComment = commentRepository.save(comment);
            return savedComment;
        } else{
            return null;

        }
    }

    public List<Comment> getAllCommentByPostId(String postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments;
    }
}
