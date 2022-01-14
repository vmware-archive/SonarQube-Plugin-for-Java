/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = "V1015")
public class StringCompareCheck extends AbstractMethodDetection {

  @Override
  protected MethodMatchers getMethodInvocationMatchers() {
    return MethodMatchers.or(
        buildFormatMethodMatcher("compareTo"),
        buildFormatMethodMatcher("compareToIgnoreCase")
    );
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    reportIssue(mit, "String.compareTo() and String.compareToIgnoreCase() fail to compare non-English text correctly. Use Java Collator instead.");
  }

  private static MethodMatchers buildFormatMethodMatcher(String method) {
    return MethodMatchers.create()
        .ofTypes("java.lang.String").names(method).withAnyParameters().build();
  }
}