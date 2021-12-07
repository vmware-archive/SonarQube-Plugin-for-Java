/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
public class StringCompareCheck {
  void methodA() {
    String a = "abc";
    a.compareTo("aaa");  // Noncompliant {{String.compareTo() and String.compareToIgnoreCase() fail to compare non-English text correctly. Use Java Collator instead.}}
    a.compareToIgnoreCase("aaa");  // Noncompliant {{String.compareTo() and String.compareToIgnoreCase() fail to compare non-English text correctly. Use Java Collator instead.}}
  }
}
