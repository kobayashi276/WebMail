package com.javafinal.WebMail.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends CrudRepository<Label,Integer> {
    List<Label> findAll();
    Label findByName(String name);
    Label findById(int id);
    List<Label> findByEmailList_Title(String title);
}
