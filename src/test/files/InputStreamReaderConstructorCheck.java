/*
 * Copyright 2018-2021 VMware, Inc.
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

class InputStreamReaderConstructorCheck {

  InputStreamReader methodA1(InputStream is) {
    return new InputStreamReader(is);  // Noncompliant {{Use an explicit, valid encoding/charset parameter with InputStreamReader.}}
  }

  InputStreamReader methodB1(InputStream is) throws UnsupportedEncodingException {
    return new InputStreamReader(is, "UTF-8");
  }

  InputStreamReader methodB2(InputStream is) throws UnsupportedEncodingException {
    return new InputStreamReader(is, "US-ASCII");  // Noncompliant {{Use an explicit, valid encoding/charset parameter with InputStreamReader.}}
  }

  InputStreamReader methodC1(InputStream is) throws UnsupportedEncodingException {
    return new InputStreamReader(is, StandardCharsets.UTF_8);
  }

  InputStreamReader methodC2(InputStream is) throws UnsupportedEncodingException {
    return new InputStreamReader(is, StandardCharsets.ISO_8859_1);  // Noncompliant {{Use an explicit, valid encoding/charset parameter with InputStreamReader.}}
  }
}
