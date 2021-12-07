/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.lang.StringBuilder;

public class DateTimeFormatterCheck {
  String methodA() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
    LocalDate date = LocalDate.now();
    return date.format(formatter);  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.}}
  }

  String methodB() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
    LocalDate date = LocalDate.now();
    return formatter.format(date);  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.}}
  }

  String methodC() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
    LocalDate date = LocalDate.now();
    return formatter.formatTo(date, new StringBuilder());  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.}}
  }
}
