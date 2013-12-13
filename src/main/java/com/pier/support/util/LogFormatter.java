package com.pier.support.util;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;

public class LogFormatter extends java.util.logging.Formatter {

    private static final String DATE_FORMAT = "yyyy/MM/dd - kk:mm:ss.SSS";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String ENTRY_SEPARATOR = "+-+-+" + LINE_SEPARATOR;
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);

    /**
     * formats log entries to ebrary's style
     *
     * @param pRecord Log record info, caller level etc
     */

    public String format(LogRecord pRecord) {

        StringBuffer formattedLogRecord = new StringBuffer();

        Object[] logRecordParameters = pRecord.getParameters();

        // Add Level to formatted log record.

        formattedLogRecord.append(pRecord.getLevel());

        // Add timestamp to formatted log record.

        formattedLogRecord.append(" [");

        dateFormatter.format(new Date(pRecord.getMillis()), formattedLogRecord, new FieldPosition(null));

        //formattedLogRecord.append("]" + LINE_SEPARATOR);

        // Add class and method information to formatted log record.

        formattedLogRecord.append(" @ ");

        // log class name (if available)
        // note: unless source class name was explicitly specified on logging call, it maybe inaccurate
        if (pRecord.getSourceClassName() != null) {
            formattedLogRecord.append(pRecord.getSourceClassName());
        } else {
            formattedLogRecord.append(pRecord.getLoggerName());
        }

        // log method name (if available)
        // note: unless method name was explicitly specified on logging call, it maybe inaccurate
        if (pRecord.getSourceMethodName() != null) {
            formattedLogRecord.append(".");
            formattedLogRecord.append(pRecord.getSourceMethodName());
            formattedLogRecord.append("()");
        }

        // Add thread information to formatted log record.

        formattedLogRecord.append(" THREAD-");

        formattedLogRecord.append(pRecord.getThreadID());

        formattedLogRecord.append("]" + LINE_SEPARATOR);


        // Add log message to formatted log record.

        formattedLogRecord.append("MSG [").append(pRecord.getMessage()).append("]" + LINE_SEPARATOR);

        // Add object parameters to formatted log record.
        if (logRecordParameters != null) {
            for (int i = 0; i < logRecordParameters.length; i++) {
                if (logRecordParameters[i] instanceof Exception) {
                    StringWriter stringWriter = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(stringWriter);
                    ((Exception) logRecordParameters[i]).printStackTrace(printWriter);
                    printWriter.close();
                    formattedLogRecord.append("EXCEPTION [" + LINE_SEPARATOR).append(stringWriter.toString()).append(LINE_SEPARATOR + "]" + LINE_SEPARATOR);
                }
            }
        }

        // Add Thrown parameter information to formatted log record.

        if (pRecord.getThrown() != null) {
            formattedLogRecord.append(LINE_SEPARATOR);
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            pRecord.getThrown().printStackTrace(printWriter);
            printWriter.close();
            formattedLogRecord.append("EXCEPTION [" + LINE_SEPARATOR).append(stringWriter.toString()).append(LINE_SEPARATOR + "]" + LINE_SEPARATOR);
        }

        // Separate entries.

        formattedLogRecord.append(ENTRY_SEPARATOR);

        // Return formatted log entry.

        return formattedLogRecord.toString();
    }

}


