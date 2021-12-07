/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
import java.util.Locale;
import java.text.Collator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class ArraysSortCheck {
  void methodA() {
    String[] names = new String[]{"Mike", "Tom"};
    Arrays.sort(names);  // Noncompliant {{Use a locale-specific instance of Collator to sort non-English text correctly.}}
    Arrays.sort(names, 0, 2);  // Noncompliant {{Use a locale-specific instance of Collator to sort non-English text correctly.}}
    Arrays.sort(names, Collator.getInstance(Locale.CHINA));
    Arrays.sort(names, 0, 2, Collator.getInstance(Locale.CHINA));

    int[] numbers = new int[]{1, 2, 3};
    Arrays.sort(numbers);
  }
}
