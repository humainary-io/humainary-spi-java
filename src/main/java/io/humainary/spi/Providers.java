/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.spi;

import java.security.PrivilegedAction;

import static java.lang.System.getProperty;
import static java.security.AccessController.doPrivileged;

public final class Providers {

  private Providers () {}

  public static < T extends Provider > T create (
    final String property,
    final String defValue,
    final Class< T > type
  ) {

    try {

      //noinspection unchecked
      return
        type.cast (
          ( (Factory< T >)
            Class.forName (
                doPrivileged (
                  (PrivilegedAction< String >) () ->
                    getProperty (
                      property,
                      defValue
                    ) ) )
              .getConstructor ()
              .newInstance ()
          ).create ()
        );

    } catch (
      final Exception error
    ) {

      throw
        new RuntimeException (
          error
        );

    }

  }


  public interface Provider {

  }

  @FunctionalInterface
  public interface Factory< T extends Provider > {

    T create ();

  }

}
