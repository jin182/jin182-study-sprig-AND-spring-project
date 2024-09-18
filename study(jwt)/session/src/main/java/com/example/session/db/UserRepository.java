package com.example.session.db;

import com.example.session.model.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRepository {

    private List<UserDto> userlist = new ArrayList<>();

    public Optional<UserDto> findByName(String name) {
        return userlist.stream().filter(it -> {
            return it.getName().equals(name);
        }).findFirst();

    }

    @PostConstruct
    public void init() {
        userlist.add(
               new  UserDto(
                        "홍길동",
                       "1234"
                )
        );
        userlist.add(
                new  UserDto(
                        "김민석",
                        "0911"
                )
        );
        userlist.add(
                new  UserDto(
                        "철수",
                        "1234"
                )
        );

    }
}
