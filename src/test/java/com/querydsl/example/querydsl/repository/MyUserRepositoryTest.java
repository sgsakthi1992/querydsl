package com.querydsl.example.querydsl.repository;

import com.querydsl.example.querydsl.model.MyUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@DataJpaTest
class MyUserRepositoryTest {
    @Autowired
    private MyUserRepository repo;

    private MyUser userJohn;
    private MyUser userTom;

    @BeforeEach
    public void init() {
        userJohn = new MyUser();
        userJohn.setFirstName("John");
        userJohn.setLastName("Doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        repo.save(userJohn);

        userTom = new MyUser();
        userTom.setFirstName("Tom");
        userTom.setLastName("Doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        repo.save(userTom);
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder()
                .with("lastName", ":", "Doe");

        var results = repo.findAll(builder.build());
        assertThat(results, containsInAnyOrder(userJohn, userTom));
    }
}