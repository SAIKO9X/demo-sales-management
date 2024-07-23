package org.sales.management.db;

import java.io.Serial;

public class DbIntegrityException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1;

    public DbIntegrityException(String message) {
        super(message);
    }
}
