/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import org.sonar.check.Rule;

import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

@Rule(key = "V1004")
public class CharsetInstanceCreationCheck extends AbstractMethodCharsetDetection {
  private static final String STRING = "java.lang.String";
  private static final String CHARSET = "java.nio.charset.Charset";
  private static final String DEFAULT_CHARSET = "defaultCharset";
  private static final String FOR_NAME = "forName";

  @Override
  protected MethodMatchers getMethodInvocationMatchers() {
    return MethodMatchers.or(
        MethodMatchers.create()
            .ofTypes(CHARSET).names(DEFAULT_CHARSET).addWithoutParametersMatcher().build(),
        MethodMatchers.create()
            .ofTypes(CHARSET).names(FOR_NAME).addParametersMatcher(STRING).build()
    );
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    String methodName = ExpressionUtils.methodName(mit).name();
    if (DEFAULT_CHARSET.equals(methodName)) {
      reportIssue(mit, "Use the Charset.forName method instead with an explicit charset/encoding argument.");
      return;
    }

    if (FOR_NAME.equals(methodName)) {
      Arguments args = mit.arguments();
      if (args.size() == 1 && (isInvalidLiteralStringCharset(args.get(0)) || isInvalidConstVariableCharset(args.get(0)))) {
        reportIssue(args.get(0), "Use an explicit, valid encoding/charset parameter with Charset factory method.");
        return;
      }
    }
  }
}
