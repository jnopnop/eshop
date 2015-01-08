package org.nop.eshop.dao;


import org.nop.eshop.model.Image;

import java.util.List;

public interface ImageDAO {
    Image get(Long id);
    List<Image> get();
    void saveOrUpdate(Image i);

    void delete(Long id);
}
