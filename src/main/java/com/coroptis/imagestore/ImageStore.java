package com.coroptis.imagestore;

import java.util.Date;

public interface ImageStore {

    int store(Date createdDate, String fileName, byte[] imageData);

    byte[] get(int id);

}
