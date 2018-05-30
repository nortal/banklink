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
/*
 * PacketException.java
 *
 * Created on teisip�ev, 14, August 2001, 0:22
 */

package com.nortal.banklink.core.packet;

import com.nortal.banklink.core.BanklinkException;

// TODO: Auto-generated Javadoc
/**
 * @author Administrator
 * 
 * @version 1.0.0
 */
public class PacketException extends BanklinkException {
    private static final long serialVersionUID = 1L;

    /**
     * 
     * Creates new <code>PacketException</code> without detail message.
     */
    public PacketException() {
    }

    /**
     * 
     * Constructs an <code>PacketException</code> with the specified detail
     * message.
     * 
     * @param msg
     *            the the detail message.
     */
    public PacketException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>PacketException</code> with the specified detail
     * message.
     * 
     * @param msg
     *            the detail message.
     * @param e
     *            the e
     */
    public PacketException(String msg, Exception e) {
        super(msg, e);
    }
}