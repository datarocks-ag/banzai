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

package org.datarocks.banzai.pipeline;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.datarocks.banzai.configuration.HandlerConfiguration;
import org.datarocks.banzai.pipeline.exception.HeadTransformerRequiredException;
import org.datarocks.banzai.pipeline.exception.TailsTransformerRequiredException;
import org.datarocks.banzai.processor.PassTroughProcessor;
import org.datarocks.banzai.transformer.PassTroughTransformer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PipeLineTest {
  private static HandlerConfiguration handlerConfiguration;

  @BeforeAll
  static void setup() {
    handlerConfiguration = HandlerConfiguration.builder().build();
  }

  @Test
  void dummyPipelineTestHappyPath() {
    PipeLine<Object, Object, Object> objectPipeline =
        PipeLine.builder(handlerConfiguration, Object.class, Object.class, Object.class)
            .addHeadTransformer(PassTroughTransformer.builder().build())
            .addStep(PassTroughProcessor.builder().build())
            .addTailTransformer(PassTroughTransformer.builder().build())
            .build();
    assertDoesNotThrow(() -> objectPipeline.process(UUID.randomUUID().toString(), "Test"));
  }

  @Test
  void dummyPipelineTestNoHeadTransformer() {
    PipeLine.PipeLineBuilder<Object, Object, Object> builder =
        PipeLine.builder(handlerConfiguration, Object.class, Object.class, Object.class);
    assertThrows(HeadTransformerRequiredException.class, builder::build);
  }

  @Test
  void dummyPipelineTestNoTailsTransformer() {
    PipeLine.PipeLineBuilder<Object, Object, Object> builder =
        PipeLine.builder(handlerConfiguration, Object.class, Object.class, Object.class)
            .addHeadTransformer(PassTroughTransformer.builder().build());
    assertThrows(TailsTransformerRequiredException.class, builder::build);
  }

  @Test
  void dummyPipelineTestNoProcessorSteps() {
    PipeLine<Object, Object, Object> pipeLine =
        PipeLine.builder(handlerConfiguration, Object.class, Object.class, Object.class)
            .addHeadTransformer(PassTroughTransformer.builder().build())
            .addTailTransformer(PassTroughTransformer.builder().build())
            .build();
    assertDoesNotThrow(() -> pipeLine.process(UUID.randomUUID().toString(), "Test"));
  }
}
