/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = "V1011")
public class SimpleDateFormatCheck extends AbstractMethodDetection {

  @Override
  protected MethodMatchers getMethodInvocationMatchers() {
    return MethodMatchers.or(
        buildFormatMethodMatcher("format"),
        buildFormatMethodMatcher("formatToCharacterIterator")
    );
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    reportIssue(mit, "Use the Unicode ICU DateTimePatternGenerator instead as it is far less error-prone.");
  }

  private static MethodMatchers buildFormatMethodMatcher(String method) {
    return MethodMatchers.create()
        .ofTypes("java.text.SimpleDateFormat").names(method).withAnyParameters().build();
  }
}