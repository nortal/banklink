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
package com.nortal.banklink.example.core.message;

import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.example.core.config.GeneralBanklinkConfig;
import com.nortal.banklink.example.core.model.GeneralPayment;
import com.nortal.banklink.example.core.model.MessageHolder;
import com.nortal.banklink.example.core.model.PacketWrapper;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Common payment message packets processing implementation.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 * @param <T>
 */
public abstract class AbstractPaymentMessage<T extends GeneralBanklinkConfig> implements GeneralPaymentMessage {
  protected static final String RESULT_PARAM = "result";

  @Getter
  private T config;
  @Getter(AccessLevel.PROTECTED)
  private String packetId;

  public AbstractPaymentMessage(String packetId, T config) {
    this.packetId = packetId;
    this.config = config;
  }

  @Override
  public MessageHolder createRequest(GeneralPayment payment) {
    PacketWrapper wrapper = new PacketWrapper(createRequestPacket());
    wrapper.appendParams(getConfig().getPacketConfig().getPacketConfig(packetId));
    wrapper.appendParams(toRequestParams(payment, config.getReturnUrl()));

    wrapper.getPacket().sign();
    return new MessageHolder(wrapper);
  }

  /**
   * Creates payment request message packet according to protocol and payment
   * packet code
   * 
   * @return {@link Packet}
   */
  protected abstract Packet createRequestPacket();

  /**
   * Converts payment data and returnUrl into key value mapping according to
   * bank message packet description
   * 
   * @param payment
   *          {@link GeneralPayment}
   * @param returnUrl
   *          application service endpoint
   * @return {@link Map} of packet specific data
   */
  protected abstract Map<String, String> toRequestParams(GeneralPayment payment, String returnUrl);
}
