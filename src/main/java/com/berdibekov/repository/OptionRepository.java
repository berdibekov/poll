package com.berdibekov.repository;

import com.berdibekov.domain.Option;
import com.berdibekov.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface OptionRepository extends PagingAndSortingRepository<Option, Long> {
//    void findAllByIdAndUser(Set<Option> options, User user);

//    void findAllByIdAndUser(Set<Option> options, User user);
}
