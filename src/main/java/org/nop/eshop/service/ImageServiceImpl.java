package org.nop.eshop.service;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.nop.eshop.dao.ImageDAO;
import org.nop.eshop.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDAO imageDAO;

    @Override
    public synchronized byte[] getImage(String entity, String filename) throws FileNotFoundException {
        assert !StringUtils.isEmpty(entity) && !StringUtils.isEmpty(filename);
        InputStream is;
        try {
            is = new FileInputStream(new File(IMG_ROOT + entity + "/" + filename));
        } catch (FileNotFoundException e) {
            is = new FileInputStream(new File(IMG_ROOT + entity + "/" + NOT_FOUND));
        }

        try {
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    @Override
    public synchronized void update(String entity, Long id, byte[] file) throws IOException {
        delete(entity, id);
        save(file, path(entity, id));
    }

    @Override
    public synchronized void delete(String entity, Long id) throws IOException {
        File toDelete = new File(path(entity, id));
        FileUtils.forceDelete(toDelete);
    }

    @Override
    @Transactional
    public synchronized void upload(byte[] file, String forEntity, String ofType) throws IOException {
        Image i = new Image();
        i.setType(ofType);
        imageDAO.saveOrUpdate(i);
        upload(file, forEntity, i);
    }

    @Override
    public void upload(byte[] file, String forEntity, Image image) throws IOException {
        save(file, path(forEntity, image.getId()));
    }

    @Override
    public void upload(String externalURL, int timeout, String forEntity, Image image) throws IOException {
        URL url = new URL(externalURL);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        upload(IOUtils.toByteArray(connection.getInputStream()), forEntity, image);
    }

    @Override
    public void upload(Map<String, Image> images, int timeout, String forEntity) {
        for (Map.Entry<String, Image> entry: images.entrySet()) {
            try {
                upload(entry.getKey(), timeout, forEntity, entry.getValue());
            } catch (Exception e) {
            }

        }
    }

    private void save(byte[] data, String path) throws IOException {
        File file = new File(path);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream.write(data);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new IOException("Can not save image: " + e.getLocalizedMessage());
        }
    }

    private String path(String entity, Long id) {
        return IMG_ROOT + entity + "/" + id;
    }
}
