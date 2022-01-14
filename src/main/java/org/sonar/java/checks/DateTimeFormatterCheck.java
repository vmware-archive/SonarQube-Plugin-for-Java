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

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Rule(key = "V1009")
public class DateTimeFormatterCheck extends AbstractMethodDetection {

  @Override
  protected MethodMatchers getMethodInvocationMatchers() {
    final String DATE_TIME_FORMATTER = "java.time.format.DateTimeFormatter";
    Predicate<Type> temporalAccessorSubtype = (type) -> {
      return type.isSubtypeOf("java.time.temporal.TemporalAccessor");
    };
    Predicate<Type> appendableSubtype = (type) -> {
      return type.isSubtypeOf("java.lang.Appendable");
    };

    Predicate<List<Type>> formatParametersType = (parameterTypes) -> {
      return exactMatchesParameters(Arrays.asList(temporalAccessorSubtype), parameterTypes);
    };

    Predicate<List<Type>> formatToParametersType = (parameterTypes) -> {
      return exactMatchesParameters(Arrays.asList(temporalAccessorSubtype, appendableSubtype), parameterTypes);
    };

    return MethodMatchers.or(
            buildFormatMethodMatcher("java.time.LocalDate"),
            buildFormatMethodMatcher("java.time.LocalDateTime"),
            buildFormatMethodMatcher("java.time.LocalTime"),
            buildFormatMethodMatcher("java.time.MonthDay"),
            buildFormatMethodMatcher("java.time.OffsetDateTime"),
            buildFormatMethodMatcher("java.time.OffsetTime"),
            buildFormatMethodMatcher("java.time.Year"),
            buildFormatMethodMatcher("java.time.YearMonth"),
            buildFormatMethodMatcher("java.time.ZonedDateTime"),
            buildFormatMethodMatcher("java.time.chrono.ChronoLocalDate"),
            buildFormatMethodMatcher("java.time.chrono.ChronoLocalDateTime"),
            buildFormatMethodMatcher("java.time.chrono.ChronoZonedDateTime"),
            MethodMatchers.create()
                    .ofTypes(DATE_TIME_FORMATTER)
                    .names("format")
                    .addParametersMatcher(formatParametersType).build(),
            MethodMatchers.create()
                    .ofTypes(DATE_TIME_FORMATTER)
                    .names("formatTo")
                    .addParametersMatcher(formatToParametersType).build()
    );
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
      reportIssue(mit, "Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.");
  }

  private static MethodMatchers buildFormatMethodMatcher(String type) {
    return MethodMatchers.create()
        .ofTypes(type).names("format").addParametersMatcher("java.time.format.DateTimeFormatter").build();
  }
}