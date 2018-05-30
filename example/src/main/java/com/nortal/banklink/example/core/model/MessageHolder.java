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
package com.nortal.banklink.example.core.model;

import com.nortal.banklink.core.packet.Packet;
import com.nortal.banklink.example.core.constants.MessageResult;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * General request/response message data container. Holds target message
 * {@link Packet} via {@link PacketWrapper} and {@link MessageResult} for the
 * current message. {@link MessageResult} is included only for response messages
 * but it might be useful for request processing as well
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 05.03.2014
 */
@Data
public class MessageHolder {
  @Setter(AccessLevel.NONE)
  private PacketWrapper packet;
  private MessageResult result;

  public MessageHolder(PacketWrapper packet) {
    this(packet, null);
  }

  public MessageHolder(PacketWrapper packet, MessageResult result) {
    this.packet = packet;
    this.result = result;
  }
}
