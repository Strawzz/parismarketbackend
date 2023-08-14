package com.capstone.parismarketbackend.controller;

import com.capstone.parismarketbackend.model.Comment;
import com.capstone.parismarketbackend.model.dbUser;
import com.capstone.parismarketbackend.repository.CommentRepository;
import com.capstone.parismarketbackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    public CommentController(CommentRepository commentRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/get")
    public ResponseEntity<Comment> getComment(@RequestParam long commentId){
        Optional<Comment> existingComment = commentRepository.findById(commentId);
        if(existingComment.isPresent()){
            return ResponseEntity.ok(existingComment.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Comment>> getAllComment(){
        List<Comment> existingComments = commentRepository.findAll();
        if(!existingComments.isEmpty()){
            return ResponseEntity.ok(existingComments);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> postComment(@RequestBody Comment comment, @RequestParam String loginId, @RequestParam String marketName){

        String decodedLoginId = URLDecoder.decode(loginId, StandardCharsets.UTF_8);
        String decodedMarketName = URLDecoder.decode(marketName, StandardCharsets.UTF_8);
        Optional<dbUser> existingUser = userRepository.findByLoginId(loginId);

        if(existingUser.isPresent()){
            comment.setUser(existingUser.get());
            comment.setMarketName(marketName);
            comment.setLoginId(loginId);
            commentRepository.save(comment);
            return ResponseEntity.ok("Comment posted successfully");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
        }


    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody Comment comment){
        Optional<Comment> existingCommentOptional = commentRepository.findById(comment.getCommentId());
        if (existingCommentOptional.isPresent()){
            Comment existingComment = existingCommentOptional.get();
            existingComment.setContent(comment.getContent());
            commentRepository.save(existingComment);
            return ResponseEntity.ok("Comment updated successfully");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(@RequestParam long commentId){
        Optional<Comment> existingComment = commentRepository.findById(commentId);
        if(existingComment.isPresent()){
            commentRepository.delete(existingComment.get());
            return ResponseEntity.ok("Comment deleted sucessfully");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllCommentByMarketName")
    public ResponseEntity<List<Comment>> getAllCommentByMarketName(@RequestParam String marketName) {
        List<Comment> comments = commentRepository.findByMarketName(marketName);
        return ResponseEntity.ok(comments);
    }



}
