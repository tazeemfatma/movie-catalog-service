package com.example.moviecatalogservice.resource;

import com.example.moviecatalogservice.model.CatalogItem;
import com.example.moviecatalogservice.model.Movie;
import com.example.moviecatalogservice.model.Ratings;
import com.example.moviecatalogservice.model.UserRating;
import com.example.moviecatalogservice.service.MovieInfo;
import com.example.moviecatalogservice.service.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webclientBuilder;

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;

   // @Autowired
   // private DiscoveryClient discoveryClient;//get instance of running service for manually load balancing

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
       /* List<Ratings> ratings= Arrays.asList(new Ratings("1234",4),
                new Ratings("4372", 3));*/
        UserRating userRating = userRatingInfo.getUserRating(userId);
        return userRating.getUserRating().stream()
                .map(rating-> movieInfo.getMovie(rating)).collect(Collectors.toList());
           /* Movie movie = webclientBuilder
                                        .build()
                                        .get()
                                        .uri("http://localhost:8081/movies/" + rating.getMovieId())
                                        .retrieve()
                                        .bodyToMono(Movie.class)
                                        .block();*/
            //return new CatalogItem(movie.getMovieName(), "desc",rating.getRatings());


    }


}
