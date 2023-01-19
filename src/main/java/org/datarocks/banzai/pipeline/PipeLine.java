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

import lombok.NonNull;
import org.datarocks.banzai.configuration.HandlerConfiguration;
import org.datarocks.banzai.pipeline.exception.HeadTransformerRequiredException;
import org.datarocks.banzai.pipeline.exception.TailsTransformerRequiredException;
import org.datarocks.banzai.processor.AbstractProcessor;
import org.datarocks.banzai.processor.AbstractSingleItemProcessor;
import org.datarocks.banzai.transformer.AbstractTransformer;

public class PipeLine<I, P, O> {
  private AbstractTransformer<I, P> abstractHeadTransformer;
  private AbstractTransformer<P, O> abstractTailTransformer;
  private AbstractSingleItemProcessor<P> processorChainHead;

  private PipeLine() {}

  public O process(@NonNull final String correlationId, @NonNull final I input) {
    P processItem = abstractHeadTransformer.process(correlationId, input);

    if (processorChainHead != null) {
      processItem = processorChainHead.process(correlationId, processItem);
    }

    return abstractTailTransformer.process(correlationId, processItem);
  }

  public static class PipeLineBuilder<INPUT, PROCESS, OUTPUT> { // NOSONAR
    private AbstractTransformer<INPUT, PROCESS> abstractHeadTransformer;
    private AbstractTransformer<PROCESS, OUTPUT> abstractTailTransformer;
    private AbstractSingleItemProcessor<PROCESS> processorChainHead;
    private AbstractProcessor<PROCESS> processorChainTail;
    private final HandlerConfiguration handlerConfiguration;

    private final Class<INPUT> inputClass;
    private final Class<PROCESS> processClass;
    private final Class<OUTPUT> outputClass;

    private PipeLineBuilder(
        final @NonNull HandlerConfiguration handlerConfiguration,
        final @NonNull Class<INPUT> inputClass,
        final @NonNull Class<PROCESS> processClass,
        final @NonNull Class<OUTPUT> outputClass) {
      this.handlerConfiguration = handlerConfiguration;
      this.inputClass = inputClass;
      this.processClass = processClass;
      this.outputClass = outputClass;
    }

    protected Class<?> getInputClass() { // NOSONAR
      return inputClass;
    }

    protected Class<?> getProcessClass() { // NOSONAR
      return processClass;
    }

    protected Class<?> getOutputClass() { // NOSONAR
      return outputClass;
    }

    public PipeLine<INPUT, PROCESS, OUTPUT> build() {
      PipeLine<INPUT, PROCESS, OUTPUT> pipeLine = new PipeLine<>();
      if (abstractHeadTransformer == null) {
        throw new HeadTransformerRequiredException();
      }
      if (abstractTailTransformer == null) {
        throw new TailsTransformerRequiredException();
      }
      pipeLine.abstractHeadTransformer = abstractHeadTransformer;
      pipeLine.abstractTailTransformer = abstractTailTransformer;
      pipeLine.processorChainHead = processorChainHead;
      return pipeLine;
    }

    public PipeLineBuilder<INPUT, PROCESS, OUTPUT> addHeadTransformer(
        final @NonNull AbstractTransformer<INPUT, PROCESS> headTransformer) {
      headTransformer.setHandlerConfiguration(handlerConfiguration);
      this.abstractHeadTransformer = headTransformer;
      return this;
    }

    public PipeLineBuilder<INPUT, PROCESS, OUTPUT> addTailTransformer(
        final @NonNull AbstractTransformer<PROCESS, OUTPUT> tailTransformer) {
      tailTransformer.setHandlerConfiguration(handlerConfiguration);
      this.abstractTailTransformer = tailTransformer;
      return this;
    }

    public PipeLineBuilder<INPUT, PROCESS, OUTPUT> addStep(
        final @NonNull AbstractSingleItemProcessor<PROCESS> processStep) {
      processStep.setHandlerConfiguration(handlerConfiguration);
      if (processorChainHead == null) {
        processorChainHead = processStep;
      } else {
        processorChainTail.setNextProcessor(processStep);
      }
      processorChainTail = processStep;

      return this;
    }
  }

  public static <IC, PC, OC> PipeLineBuilder<IC, PC, OC> builder( // NOSONAR
      final @NonNull HandlerConfiguration handlerConfiguration,
      final @NonNull Class<IC> inputClass,
      final @NonNull Class<PC> processClass,
      final @NonNull Class<OC> outputClass) {
    return new PipeLineBuilder<>(handlerConfiguration, inputClass, processClass, outputClass);
  }
}
