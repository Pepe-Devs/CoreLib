package com.pepedevs.corelib.database.sql;

import java.sql.SQLException;

public interface SQLFunction<T> {

    <R> R apply(T t) throws SQLException;

}
