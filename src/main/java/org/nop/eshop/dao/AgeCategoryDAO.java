package org.nop.eshop.dao;


import org.nop.eshop.model.AgeCategory;

import java.util.List;

public interface AgeCategoryDAO {
    List<AgeCategory> getAll();
    AgeCategory getById(Long id);
    AgeCategory getByTitle(String title);
    void save(AgeCategory t);
}
