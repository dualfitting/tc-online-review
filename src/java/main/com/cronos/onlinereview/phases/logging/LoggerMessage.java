/*
 * Copyright (C) 2007 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.phases.logging;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Encapsulates the entry log data and generates consistent log messages.
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 2.0
 */
public class LoggerMessage {
    /** id for the log message */
    private final Long id;

    /** Operator doing the action */
    private final String operator;

    /** type of the action. */
    private final String type;

    /** free text message to log */
    private final String message;

    /** exception to append to the log message */
    private final Throwable error;

    /** generated log message */
    private String logMessage = null;

    /**
     * Creates a log message. Any parameter can be null.
     *
     * @param type the operator to log.
     * @param id the project id to log.
     * @param operator DOCUMENT ME!
     * @param message a free text message.
     * @param error an exception to append to the log message.
     */
    public LoggerMessage(String type, Long id, String operator, String message, Throwable error) {
        this.id = id;

        this.type = type;

        this.operator = operator;

        this.message = message;

        this.error = error;
    }

    /**
     * Creates a log message. Any parameter can be null
     *
     * @param type the operator to log.
     * @param id the project id to log.
     * @param operator DOCUMENT ME!
     * @param message a free text message.
     */
    public LoggerMessage(String type, Long id, String operator, String message) {
        this(type, id, operator, message, null);
    }

    /**
     * Return the error cause.Can be null.
     *
     * @return the error cause.
     */
    public Throwable getError() {
        return error;
    }

    /**
     * Returns the message.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the type. Can be null.
     *
     * @return the operator
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the id, can be null.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Generate the message need to be logged for this class.
     *
     * @return The log message
     */
    public String getLogMessage() {
        if (logMessage == null) {
            StringBuilder buffer = new StringBuilder();

            buffer.append("type: ").append((type == null) ? "Unknown" : type).append(" id: ")
                  .append((id == null) ? "Unknown" : id.toString()).append(" operator:")
                  .append((operator == null) ? "Unknown" : operator).append(" - ").append(message);

            //This should be done while the Logging Wrapper 1.2 is used.

            //When the LW 1.3 would be ready, it will be possible pass the exception directly to LW.   
            if (error != null) {
                buffer.append('\n').append(getExceptionStackTrace(error));
            }

            logMessage = buffer.toString();
        }

        return logMessage;
    }

    /**
     * Return the exception stack trace string.
     *
     * @param cause the exception to be recorded
     *
     * @return stack trace
     */
    public static String getExceptionStackTrace(Throwable cause) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        cause.printStackTrace(new PrintStream(out));

        return out.toString();
    }

    /**
     * Override the toString method returns the log message.
     *
     * @return logged message
     */
    public String toString() {
        return getLogMessage();
    }
}
