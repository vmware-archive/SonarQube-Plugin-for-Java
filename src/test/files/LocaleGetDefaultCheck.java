/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
import java.util.Locale;
import java.text.Collator;

public class LocaleGetDefaultCheck {
  void methodA() {
    Collator collator = Collator.getInstance(Locale.getDefault());  // Noncompliant {{Locale.getDefault is error prone. Use explicit, user-preferred locales instead.}}
  }
}
