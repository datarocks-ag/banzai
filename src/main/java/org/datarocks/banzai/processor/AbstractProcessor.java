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

import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.datarocks.banzai.configuration.HandlerConfiguration;
import org.datarocks.banzai.event.ProcessorEventListener;
import org.datarocks.banzai.exception.HandlerConfigurationMissingException;

@SuperBuilder
public abstract class AbstractProcessor<T> {
  protected AbstractSingleItemProcessor<T> singleItemProcessor;

  private HandlerConfiguration handlerConfiguration;
  protected ProcessorEventListener processorEventListener;

  public HandlerConfiguration getHandlerConfiguration() {
    if (handlerConfiguration == null) {
      throw new HandlerConfigurationMissingException();
    }
    return handlerConfiguration;
  }

  public void setHandlerConfiguration(final @NonNull HandlerConfiguration handlerConfiguration) {
    this.handlerConfiguration = handlerConfiguration;
  }

  public void setNextProcessor(final AbstractSingleItemProcessor<T> processor) {
    this.singleItemProcessor = processor;
  }

  protected AbstractSingleItemProcessor<T> getNextProcessor() {
    return singleItemProcessor;
  }
}
