/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
import java.util.Locale;
import java.text.Collator;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class CollectionsSortCheck {
  void methodA() {
    ArrayList<String> names = new ArrayList<String>();
    names.add("Mike");
    names.add("Tom");
    Collections.sort(names);  // Noncompliant {{Use a locale-specific instance of Collator to sort non-English text correctly.}}
    Collections.sort(names, Collator.getInstance(Locale.CHINA));
  }
}
