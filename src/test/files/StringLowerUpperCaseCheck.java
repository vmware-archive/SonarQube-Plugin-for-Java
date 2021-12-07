/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
import java.util.Locale;

public class StringLowerUpperCaseCheck {
  void methodA(Locale userLocale) {
    String text = "hello";

    text.toLowerCase();  // Noncompliant {{Use String.toLowerCase() and String.toUpperCase() with an explicit, user-preferred locale argument.}}
    text.toLowerCase(Locale.CHINA);
    text.toLowerCase(userLocale);

    text.toUpperCase();  // Noncompliant {{Use String.toLowerCase() and String.toUpperCase() with an explicit, user-preferred locale argument.}}
    text.toUpperCase(Locale.CHINA);
    text.toUpperCase(userLocale);
  }
}
