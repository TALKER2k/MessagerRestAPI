package ru.relex.service.Impl;

import org.springframework.stereotype.Service;
import ru.relex.Enums.City;
import ru.relex.Enums.Country;
import ru.relex.Enums.Hobby;
import ru.relex.Enums.Profession;
import ru.relex.models.User;
import ru.relex.repository.UserRepository;
import ru.relex.service.RecommendsService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendsServiceImpl<T extends Enum<T>> implements RecommendsService {
    private final UserRepository userRepository;
    private final static Double COEFFICIENT = 1.5;

    public RecommendsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Enum<T>> init(User user) {
        List<Enum<T>> list = new ArrayList<>();
        list.add((Enum<T>) setHobby(user));
        list.add((Enum<T>) setCountry(user));
        list.add((Enum<T>) setProf(user));
        list.add((Enum<T>) setCity(user));

        return list;
    }

    private Hobby setHobby(User user) {
        for (Hobby profession : Hobby.values()) {
            if (user.getHobby().equals(profession.name())) {
                return profession;
            }
        }
        return null;
    }

    private Country setCountry(User user) {
        for (Country profession : Country.values()) {
            if (user.getCountry().equals(profession.name())) {
                return profession;
            }
        }
        return null;
    }

    private City setCity(User user) {
        for (City profession : City.values()) {
            if (user.getCity().equals(profession.name())) {
                return profession;
            }
        }
        return null;
    }

    private Profession setProf(User user) {
        for (Profession profession : Profession.values()) {
            if (user.getProfession().equals(profession.name())) {
                return profession;
            }
        }
        return null;
    }

    @Override
    public List<User> getRecommendsFriends(User user) {
        List<Enum<T>> listUser = init(user);
        System.out.println(listUser);
        userRepository.save(user);
        return findRecommendUser(listUser, user);
    }

    private List<User> findRecommendUser(List<Enum<T>> mapUser, User user) {
        return userRepository.findAll().stream()
                .filter(u -> !(u.getId().equals(user.getId())))
                .filter(u -> findDeviation(init(u), mapUser) <= COEFFICIENT)
                .toList();
    }

    private double findDeviation(List<Enum<T>> mapFriend, List<Enum<T>> mapUser) {
        double sum = 0d;
        for (int i = 0; i < mapUser.size(); i++) {
            sum += Math.pow(mapUser.get(i).ordinal() - mapFriend.get(i).ordinal(), 2);
        }

        System.out.println(Math.sqrt(sum/mapUser.size()));
        return Math.sqrt(sum/mapUser.size());
    }
}
