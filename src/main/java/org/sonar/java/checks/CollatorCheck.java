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

@Rule(key = "V1014")
public class CollatorCheck extends AbstractMethodDetection {

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    return ImmutableList.<MethodMatcher>builder()
        .add(MethodMatcher.create().typeDefinition("java.text.Collator").name("getInstance").withoutParameter())
        .build();
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    reportIssue(mit, "Use a locale-specific instance of Collator to sort non-English text correctly.");
  }
}