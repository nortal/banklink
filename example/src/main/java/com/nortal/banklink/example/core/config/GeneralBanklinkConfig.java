/**
 *   Copyright 2014 Nortal AS
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.nortal.banklink.example.core.config;

import com.nortal.banklink.example.core.model.PacketConfig;

/**
 * General banklink configuration holder. Actual implementations extends
 * configuration classes from the authentication module.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 */
public interface GeneralBanklinkConfig {
  /**
   * Encoding to encode/decode message parameters
   * 
   * @return
   */
  String getEncoding();

  /**
   * Returns current configuration bank specification for convenient
   * PacketFactory initialization
   * 
   * @return
   */
  String getBankSpec();

  /**
   * Returns current configuration predefined packet parameters configuration
   * 
   * @return {@link PacketConfig}
   */
  PacketConfig getPacketConfig();

  /**
   * Returns current bank service endpoint URL
   * 
   * @return
   */
  String getUrl();

  /**
   * Returns application service endpoint URL
   * 
   * @return
   */
  String getReturnUrl();
}
