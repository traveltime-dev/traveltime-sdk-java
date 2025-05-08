package com.traveltime.sdk.dto.responses.errors;

import com.traveltime.sdk.utils.Utils;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class IOError implements TravelTimeError {
  @NonNull Throwable cause;

  @NonNull String errorMessage;

  @Override
  public Option<Throwable> retrieveCause() {
    return Option.of(cause);
  }

  @Override
  public String getMessage() {
    return errorMessage;
  }

  @Override
  public String toString() {
    return "IOError( " + getMessage() + ")\n" + Utils.printableStackTrace(getCause());
  }
}
