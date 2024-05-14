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
   * @param string log message string
   *
   * @return sanitized log message string
   */
  @Override
  public String transform(final ILoggingEvent event, final String string) {
    // Mask Card Number
    final String maskCardNumber = string.replaceAll(
        "(?<=cardNumber=)\\d+(?=(,|\\s|}))|(?<=\"cardNumber\":)\\d+(?=(,|\\s|}))",
        "[PAN]"
    );

    //Mask Card Verification Value
    return maskCardNumber.replaceAll(
        "(?<=cardVerificationValue=)\\d{3,4}(?=(,|\\s|}))|"
            + "(?<=\"cardVerificationValue\":)\\d{3,4}(?=(,|\\s|}))",
        "[CVV]"
    );
  }

  /**
   * Mask sensitive data in the message.
   *
   * @param context the current JSON stream context, which can be used to determine the path
   *                within the JSON output. (could be at a field value path or an array element
   *                value path)
   * @param value   the number or string scalar value to potentially mask (could be a field value
   *                or an array element value).
   *
   * @return the masked object
   */
  @Override
  public Object mask(final JsonStreamContext context, final Object value) {
    if (value instanceof CharSequence) {
      final String string = value.toString();
      // Mask Card Number
      final String noCardNumber = string.replaceAll(
          "(?<=cardNumber=)\\d+(?=(,|\\s|}))|(?<=\"cardNumber\":)\\d+(?=(,|\\s|}))",
          "[PAN]"
      );

      //Mask Card Verification Value
      return noCardNumber.replaceAll(
          "(?<=cardVerificationValue=)\\d{3,4}(?=(,|\\s|}))|"
              + "(?<=\"cardVerificationValue\":)\\d{3,4}(?=(,|\\s|}))",
          "[CVV]"
      );
    }

    return value;
  }
}
