package com.pronoia.deployment.interfaces;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * Abstract class for all qualification beans to implement.
 *
 * @author KilburB
 */
public abstract class Filter {
  protected Logger log = LoggerFactory.getLogger(this.getClass());
  /**
   * Audit format for standard disqualified statements.
   */
  public static final String QUAL_FAILED =
      "{0}(): Qualification failed - Expected value: \"{1}\".  Message value: \"{2}\".";

  /**
   * Audit format for custom disqualified statements.
   */
  public static final String QUAL_FAILED_CUSTOM = "{0}(): {1}";

  /**
   * Used when value field is empty during audit.
   */
  public static final String EMPTY_VALUE = "No value received in message";

  /**
   * class logger.
   */
  Logger _logger = LoggerFactory.getLogger(this.getClass());

  /**
   * Method to generate qualification failure messages and log the results.
   *
   * @param qual   method name that failed
   * @param expect expected value used for compare
   * @param value  value from the message or headers that was compared
   * @return formatted failed audit message
   */
  public String qualFailed(String qual, String expect, String value) {
    String failureMessage = MessageFormat
        .format(QUAL_FAILED, qual, expect, (value == null || value.length() == 0) ? EMPTY_VALUE : value);
    _logger.debug(failureMessage);

    return failureMessage;
  }

  /**
   * Method to generate custom qualification failure messages and log the results
   *
   * @param qual method name that failed
   * @param msg  custom message to use for audit
   * @return formatted failed audit message
   */
  public String qualFailed(String qual, String msg) {
    String failureMessage = MessageFormat.format(QUAL_FAILED_CUSTOM, qual, msg);
    _logger.debug(failureMessage);

    return failureMessage;
  }

  public abstract boolean qualify(@Body String body);

}
