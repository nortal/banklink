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
package com.nortal.banklink.core.log;

// TODO: Auto-generated Javadoc
/**
 * Representation of information that needs to be logged about packet verifying.
 * 
 * @author Imre Lumiste
 */
public final class PacketForwardLog extends PacketLog {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Sole constructor. Initializes required private fields.
     * 
     * @since 20.07.2001
     * @roseuid 3B616CC401EC
     */
    public PacketForwardLog() {
        super();
    }

    /**
     * Gets name of operation that logs this kind of packets.
     * 
     * @return String &quot;forward&quot;
     * @since 20.07.2001
     * @roseuid 3B616A2202B5
     * 
     * @return the operation name
     */
    public String getOperationName() {
        return "forward";
    }
}