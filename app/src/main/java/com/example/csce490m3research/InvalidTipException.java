package com.example.csce490m3research;

/**
 * Throws an error message if a user inputs an unacceptable value
 */
class InvalidTipException extends Throwable {
    public InvalidTipException(String message) {
        super(message);
    }
}
