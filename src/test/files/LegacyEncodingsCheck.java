/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

class LegacyEncodingsCheck {
  public static final String UTF_8 = "UTF-8";
  public static final String BIG5 = "Big5";  // Noncompliant

  String methodA1(byte[] bytes) throws UnsupportedEncodingException {
    return new String(bytes, "ISO-8859-1");  // Noncompliant
  }

  String methodA1(byte[] bytes) throws UnsupportedEncodingException {
    return new String(bytes, "UTF-16");
  }
}
