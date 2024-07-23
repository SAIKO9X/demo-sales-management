package org.sales.management.db;

import java.io.Serial;

public class DbException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public DbException(String message) {
        super(message);
    }
}
