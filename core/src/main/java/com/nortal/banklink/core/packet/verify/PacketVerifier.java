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
package com.nortal.banklink.core.packet.verify;

import com.nortal.banklink.core.BanklinkException;
import com.nortal.banklink.core.packet.Packet;

/**
 * Interface for verifying some rule on a packet.
 * 
 * @author Alrik Peets
 */
public interface PacketVerifier {

    /**
     * Verify.
     * 
     * @param packet
     *            the packet
     * @return true, if successful
     * @throws BanklinkException
     *             the banklink exception
     */
    public boolean verify(Packet packet) throws BanklinkException;
}
