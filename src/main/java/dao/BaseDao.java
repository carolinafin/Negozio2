package dao;

import java.sql.Connection;
import java.util.ArrayList;

public interface BaseDao <T, ID> {

    T findByID(ID id);

    ArrayList<T> findAll();

    void insert(T entity);
}
