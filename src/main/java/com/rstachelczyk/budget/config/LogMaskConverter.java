package com.rstachelczyk.budget.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import com.fasterxml.jackson.core.JsonStreamContext;
import net.logstash.logback.mask.ValueMasker;

/**
 * Contains logic for sanitizing sensitive data from log events.
 */
public class LogMaskConverter extends CompositeConverter<ILoggingEvent> implements ValueMasker {

  /**
   * Handles log sanitization.
   *
   * @param event logging event
   * @param s     log message string
   *
   * @return sanitized log message string
   */
  public String transform(ILoggingEvent event, String s) {
    // Mask Card Number
    s = s.replaceAll(
        "(?<=cardNumber=)\\d+(?=(,|\\s|}))|(?<=\"cardNumber\":)\\d+(?=(,|\\s|}))",
        "[PAN]"
    );

    //Mask Card Verification Value
    s = s.replaceAll(
        "(?<=cardVerificationValue=)\\d{3,4}(?=(,|\\s|}))|"
            + "(?<=\"cardVerificationValue\":)\\d{3,4}(?=(,|\\s|}))",
        "[CVV]"
    );

    return s;
  }

  /**
   * Mask sensitive data in the message.
   *
   * @param context the current JSON stream context, which can be used to determine the path
   *                within the JSON output. (could be at a field value path or an array element value path)
   * @param value   the number or string scalar value to potentially mask (could be a field value
   *                or an array element value).
   *
   * @return the masked object
   */
  @Override
  public Object mask(JsonStreamContext context, Object value) {
    if (value instanceof CharSequence) {
      String s = value.toString();
      // Mask Card Number
      s = s.replaceAll(
          "(?<=cardNumber=)\\d+(?=(,|\\s|}))|(?<=\"cardNumber\":)\\d+(?=(,|\\s|}))",
          "[PAN]"
      );

      //Mask Card Verification Value
      s = s.replaceAll(
          "(?<=cardVerificationValue=)\\d{3,4}(?=(,|\\s|}))|"
              + "(?<=\"cardVerificationValue\":)\\d{3,4}(?=(,|\\s|}))",
          "[CVV]"
      );

      return s;
    }

    return value;
  }
}
