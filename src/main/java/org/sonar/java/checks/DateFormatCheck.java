/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.java.matcher.TypeCriteria;

import java.util.List;

@Rule(key = "V1010")
public class DateFormatCheck extends AbstractMethodDetection {

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    return ImmutableList.<MethodMatcher>builder()
        .add(buildFormatMethodMatcher("format"))
        .add(buildFormatMethodMatcher("formatToCharacterIterator"))
        .build();
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    reportIssue(mit, "Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.");
  }

  private static MethodMatcher buildFormatMethodMatcher(String method) {
    return MethodMatcher.create()
        .callSite(TypeCriteria.is("java.text.DateFormat")).name(method).withAnyParameters();
  }
}