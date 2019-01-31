package com.demo.eureka.invoker.model;

import java.io.Serializable;

/**
 * Person
 *
 * @author 10905 2019/1/30
 * @version 1.0
 */
public class Person implements Serializable {
    private Integer id;
    private String name;
    private String url;
    private Integer age;

    public Person(Integer id, String name, String url, Integer age) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.age = age;
    }

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", age=" + age +
                '}';
    }
}
