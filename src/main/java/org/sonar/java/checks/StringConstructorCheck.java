/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.*;

@Rule(key = "V1003")
public class StringConstructorCheck extends AbstractMethodCharsetDetection {
  private static final String STRING = "java.lang.String";
  private static final String INIT = "<init>";
  private static final String CHARSET = "java.nio.charset.Charset";
  private static final String BYTE_ARRAY = "byte[]";
  private static final String INT = "int";

  @Override
  protected MethodMatchers getMethodInvocationMatchers() {
    return MethodMatchers.or(
        MethodMatchers.create()
            .ofTypes(STRING).constructor().addParametersMatcher(BYTE_ARRAY).build(),
        MethodMatchers.create()
            .ofTypes(STRING).constructor().addParametersMatcher(BYTE_ARRAY, CHARSET).build(),
        MethodMatchers.create()
            .ofTypes(STRING).constructor().addParametersMatcher(BYTE_ARRAY, STRING).build(),
        MethodMatchers.create()
            .ofTypes(STRING).constructor().addParametersMatcher(BYTE_ARRAY, INT, INT).build(),
        MethodMatchers.create()
            .ofTypes(STRING).constructor().addParametersMatcher(BYTE_ARRAY, INT, INT, CHARSET).build(),
        MethodMatchers.create()
            .ofTypes(STRING).constructor().addParametersMatcher(BYTE_ARRAY, INT, INT, STRING).build(),
        MethodMatchers.create()
            .ofTypes(STRING).constructor().addParametersMatcher(BYTE_ARRAY, INT).build(),
        MethodMatchers.create()
            .ofTypes(STRING).constructor().addParametersMatcher(BYTE_ARRAY, INT, INT, INT).build()
    );
  }

  @Override
  protected void onConstructorFound(NewClassTree newClassTree) {
    Arguments args = newClassTree.arguments();
    if (args.size() == 1 || (args.size() == 3 && args.get(2).symbolType().is(INT))) {
      reportIssue(newClassTree, "Use non-deprecated String constructors with an explicit, valid charset/encoding parameter.");
      return;
    }
    if ((args.size() == 2 && args.get(1).symbolType().is(INT)) || (args.size() == 4 && args.get(3).symbolType().is(INT))) {
      reportIssue(newClassTree, "This constructor has been deprecated and it also can not accept a charset parameter for product internationalization.");
      return;
    }

    ExpressionTree firstArg = args.get(0);
    if (args.size() == 2 && (isInvalidLiteralStringCharset(args.get(1)) || isInvalidConstVariableCharset(args.get(1)))) {
      reportIssue(args.get(1), "Use an explicit, valid encoding/charset argument with String constructor.");
      return;
    }
    if (args.size() == 4 && (isInvalidLiteralStringCharset(args.get(3)) || isInvalidConstVariableCharset(args.get(3)))) {
      reportIssue(args.get(3), "Use an explicit, valid encoding/charset argument with String constructor.");
      return;
    }

    if (args.size() == 2 && isInvalidStandardCharsetsInstanceCharset(args.get(1))) {
      reportIssue(args.get(1), "Use an explicit, valid encoding/charset argument with String constructor.");
      return;
    }
    if (args.size() == 4 && isInvalidStandardCharsetsInstanceCharset(args.get(3))) {
      reportIssue(args.get(3), "Use an explicit, valid encoding/charset argument with String constructor.");
      return;
    }
  }
}
