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

import com.nortal.banklink.link.BankLinkConfig.IPizzaConfig;

import com.nortal.banklink.example.core.model.PacketConfig;
import java.security.PrivateKey;
import java.security.PublicKey;
import lombok.Getter;

/**
 * Extended authentication module configuration for IPizza protocol banks to
 * support payments message processing as well
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 */
@Getter
public class ExtIPizzaConfig extends IPizzaConfig implements GeneralBanklinkConfig {
  private String bankSpec;
  private String encoding;
  private PacketConfig packetConfig;

  public ExtIPizzaConfig(String url, String returnUrl, String senderId, String receiverId, PublicKey pub,
      PrivateKey priv, String bankSpec, String encoding, PacketConfig packetConfig) {
    super(url, returnUrl, senderId, receiverId, pub, priv);

    this.bankSpec = bankSpec;
    this.encoding = encoding;
    this.packetConfig = packetConfig;
  }
}
