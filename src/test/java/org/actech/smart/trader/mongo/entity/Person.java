package org.actech.smart.trader.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by paul on 2018/3/10.
 */
@Document
@Data
public class Person {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
}
