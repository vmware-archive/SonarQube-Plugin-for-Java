/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
import java.util.Locale;
import java.text.Collator;

public class CollatorCheck {
  void methodA() {
    Collator collatorOk = Collator.getInstance(Locale.CHINA);
    Collator collatorWarning = Collator.getInstance();  // Noncompliant {{Use a locale-specific instance of Collator to sort non-English text correctly.}}
  }
}
