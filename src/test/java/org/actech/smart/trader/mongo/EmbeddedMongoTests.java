package org.actech.smart.trader.mongo;

import org.actech.smart.trader.Application;
import org.actech.smart.trader.UnitTestConfiguration;
import org.actech.smart.trader.mongo.entity.Person;
import org.actech.smart.trader.mongo.repositories.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;


/**
 * Created by paul on 2018/3/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, UnitTestConfiguration.class})
@ActiveProfiles("dev")
public class EmbeddedMongoTests {
    @Autowired(required = false)
    ApplicationContext applicationContext = null;

    @Test
    public void shouldStartWithApplicationContext() {
        assertThat(applicationContext, is(notNullValue()));
    }

    @Autowired(required = false)
    PersonRepository repository;

    @Test
    public void shouldStartWithRepository() {
        assertThat(repository, is(notNullValue()));
    }

    @Test
    public void shouldSaveAndLoadPerson() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("Paul");
        person.setLastName("Wong");

        repository.save(person);

        Person anotherPerson = repository.findById(1L).get();
        assertThat(person, is(anotherPerson));
    }

}
