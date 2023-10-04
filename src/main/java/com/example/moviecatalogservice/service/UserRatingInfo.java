package com.example.moviecatalogservice.service;

import com.example.moviecatalogservice.model.Ratings;
import com.example.moviecatalogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
@Service
public class UserRatingInfo {
    @Autowired
    RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+ userId, UserRating.class);

    }


    public UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
        UserRating userRating=new UserRating();
        userRating.setUserRating(Arrays.asList(new Ratings("0",0)));
        return userRating;
    }
}
