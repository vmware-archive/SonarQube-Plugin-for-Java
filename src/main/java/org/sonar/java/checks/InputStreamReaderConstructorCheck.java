/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package org.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Rule(key = "V1005")
public class InputStreamReaderConstructorCheck extends AbstractMethodCharsetDetection {
  private static final String STRING = "java.lang.String";
  private static final String CHARSET = "java.nio.charset.Charset";
  private static final String INPUT_STREAM = "java.io.InputStream";
  private static final String INPUT_STREAM_READER = "java.io.InputStreamReader";
  private static final String INIT = "<init>";

  @Override
  protected MethodMatchers getMethodInvocationMatchers() {
    Predicate<Type> inputStreamSubtype = (type) -> {
      return type.isSubtypeOf(INPUT_STREAM);
    };
    Predicate<Type> charsetType = (type) -> {
      return type.is(CHARSET);
    };
    Predicate<Type> stringType = (type) -> {
      return type.is(STRING);
    };

    Predicate<List<Type>> oneParametersType = (parameterTypes) -> {
      return exactMatchesParameters(Arrays.asList(inputStreamSubtype), parameterTypes);
    };

    Predicate<List<Type>> twoParametersWithCharsetType = (parameterTypes) -> {
      return exactMatchesParameters(Arrays.asList(inputStreamSubtype, charsetType), parameterTypes);
    };

    Predicate<List<Type>> twoParametersWithStringType = (parameterTypes) -> {
      return exactMatchesParameters(Arrays.asList(inputStreamSubtype, stringType), parameterTypes);
    };

    return MethodMatchers.or(
            MethodMatchers.create()
                    .ofTypes(INPUT_STREAM_READER)
                    .constructor()
                    .addParametersMatcher(oneParametersType).build(),
            MethodMatchers.create()
                    .ofTypes(INPUT_STREAM_READER)
                    .constructor()
                    .addParametersMatcher(twoParametersWithCharsetType).build(),
            MethodMatchers.create()
                    .ofTypes(INPUT_STREAM_READER)
                    .constructor()
                    .addParametersMatcher(twoParametersWithStringType).build()
    );
  }

  @Override
  protected void onConstructorFound(NewClassTree newClassTree) {
    Arguments args = newClassTree.arguments();
    if (args.size() == 1) {
      reportIssue(newClassTree, "Use an explicit, valid encoding/charset parameter with InputStreamReader.");
      return;
    }

    if (args.size() == 2 && (isInvalidLiteralStringCharset(args.get(1)) || isInvalidConstVariableCharset(args.get(1)))) {
      reportIssue(args.get(1), "Use an explicit, valid encoding/charset parameter with InputStreamReader.");
      return;
    }

    if (args.size() == 2 && isInvalidStandardCharsetsInstanceCharset(args.get(1))) {
      reportIssue(args.get(1), "Use an explicit, valid encoding/charset parameter with InputStreamReader.");
      return;
    }
  }
}
