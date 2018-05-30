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

import com.nortal.banklink.link.BankLinkConfig.NordeaConfig;

import com.nortal.banklink.example.core.model.PacketConfig;
import lombok.Getter;

/**
 * Extended authentication module configuration for Nordea to support payments
 * message processing as well
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 */
@Getter
public class ExtNordeaConfig extends NordeaConfig implements GeneralBanklinkConfig {
  private String bankSpec;
  private String encoding;
  private PacketConfig packetConfig;

  public ExtNordeaConfig(String url, String returnUrl, String senderId, String mac, String bankSpec, String encoding,
      PacketConfig packetConfig) {
    super(url, returnUrl, senderId, mac);

    this.bankSpec = bankSpec;
    this.encoding = encoding;
    this.packetConfig = packetConfig;
  }
}
