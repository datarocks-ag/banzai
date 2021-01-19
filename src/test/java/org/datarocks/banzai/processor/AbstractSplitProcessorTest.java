/*
 * MIT License
 *
 * Copyright (c) 2021 Data Rocks AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.datarocks.banzai.processor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AbstractSplitProcessorTest {
  @Test
  void testWithNullSingleItemProcessor() {
    AbstractSplitProcessor<String> abstractSplitProcessor =
        AbstractSplitProcessorImpl.builder().build();

    List<String> result = abstractSplitProcessor.process(UUID.randomUUID().toString(), "Test");
    assertEquals(1, result.size());
    assertEquals("Test", result.get(0));
  }

  @Test
  void testWithDefinedSingleItemProcessor() {
    AbstractSplitProcessor<String> abstractSplitProcessor1 =
        AbstractSplitProcessorImpl.builder().build();

    PassTroughProcessor<String> abstractSplitProcessor2 =
        PassTroughProcessor.<String>builder().build();

    abstractSplitProcessor1.setNextProcessor(abstractSplitProcessor2);

    List<String> result = abstractSplitProcessor1.process(UUID.randomUUID().toString(), "Test");
    assertEquals(1, result.size());
    assertEquals("Test", result.get(0));
  }
}
