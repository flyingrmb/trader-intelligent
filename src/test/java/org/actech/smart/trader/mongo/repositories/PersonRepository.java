package org.actech.smart.trader.mongo.repositories;

import org.actech.smart.trader.mongo.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by paul on 2018/3/10.
 */
public interface PersonRepository extends CrudRepository<Person, Long> {
    Optional<Person> findById(Long primaryKey);
    Person save(Person person);
}
