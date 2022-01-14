/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

@Rule(key = "V1018")
public class StringLowerUpperCaseCheck extends AbstractMethodDetection {
  private static final String LOCALE = "java.util.Locale";
  private static final String STRING = "java.lang.String";

  @Override
  protected MethodMatchers getMethodInvocationMatchers() {
    return MethodMatchers.or(
            MethodMatchers.create()
                    .ofTypes(STRING).names("toLowerCase").addWithoutParametersMatcher().build(),
            MethodMatchers.create()
                    .ofTypes(STRING).names("toUpperCase").addWithoutParametersMatcher().build()
    );
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    reportIssue(mit, "Use String.toLowerCase() and String.toUpperCase() with an explicit, user-preferred locale argument.");
  }
}
