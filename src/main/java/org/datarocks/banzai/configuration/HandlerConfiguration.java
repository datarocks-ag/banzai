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

package org.datarocks.banzai.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class HandlerConfiguration {

  private final Map<String, Object> configurationItems;

  public static class HandlerConfigurationBuilder {

    public HandlerConfigurationBuilder handlerConfigurationItem(
        @NonNull final String key, @NonNull final Object value) {
      if (this.configurationItems == null) {
        this.configurationItems = new HashMap<>();
      }
      this.configurationItems.put(key, value);
      return this;
    }
  }

  public Optional<Object> getConfigurationItem(@NonNull final String key) {
    if (configurationItems == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(configurationItems.get(key));
  }

  public <T> Optional<T> getConfigurationItem(Class<T> clazz, @NonNull final String key) {
    if (configurationItems == null) {
      return Optional.empty();
    }

    Object configurationItem = configurationItems.get(key);

    if (configurationItem == null) {
      return Optional.empty();
    }

    try {
      return Optional.of(clazz.cast(configurationItem));
    } catch (ClassCastException e) {
      return Optional.empty();
    }
  }
}
