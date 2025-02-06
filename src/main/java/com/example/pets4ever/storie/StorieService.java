package com.example.pets4ever.storie;

import com.example.pets4ever.infra.aws.AmazonClient;
import com.example.pets4ever.post.PostRepository;
import com.example.pets4ever.storie.DTO.StorieDTO;
import com.example.pets4ever.storie.Response.StorieResponse;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StorieService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StorieRepository storieRepository;

    @Autowired
    AmazonClient amazonClient;


    public List<StorieResponse> show(){
        List<Storie> Stories = this.postRepository.findAllStories();

        return Stories.stream().map((storie) ->
                new StorieResponse(storie.getImageUrl(), storie.getUser().getProfileImgUrl(), storie.getUser().getUsername(), storie.getExpirationTime(), storie.getViews())).toList();
    }
    public String create(StorieDTO storieDTO){
        User user = this.userRepository.findById(storieDTO.userId()).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado!"));

        String awsFilename = null;
        try {
            awsFilename = this.amazonClient.uploadFile(storieDTO.file());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Storie storieToBeInserted = new Storie(storieDTO.description(), awsFilename, user);

        this.postRepository.save(storieToBeInserted);

        return "Oi";

    }
    @Transactional
    @Scheduled(fixedRate = 60000)  // Executa a cada hora (você pode ajustar conforme necessário)
    public void deleteStorieSchedule(){
        System.out.println("Executou agendamento");
        this.storieRepository.deleteByExpirationTimeBeforeAndExpirationTimeIsNotNull(LocalDateTime.now());
    }


}
