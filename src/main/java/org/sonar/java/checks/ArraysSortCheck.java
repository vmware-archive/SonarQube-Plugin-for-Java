/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = "V1017")
public class ArraysSortCheck extends AbstractMethodDetection {

  @Override
  protected MethodMatchers getMethodInvocationMatchers() {
    return MethodMatchers.or(
        MethodMatchers.create()
            .ofTypes("java.util.Arrays").names("sort").addParametersMatcher("java.lang.Object[]").build(),
        MethodMatchers.create()
            .ofTypes("java.util.Arrays").names("sort").addParametersMatcher("java.lang.Object[]", "int", "int").build()
    );
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    Type firstArgType = mit.arguments().get(0).symbolType();
    if ((firstArgType instanceof Type.ArrayType) && ((Type.ArrayType)firstArgType).elementType().is("java.lang.String")) {
      reportIssue(mit, "Use a locale-specific instance of Collator to sort non-English text correctly.");
    }
  }
}