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
package com.nortal.banklink.core.packet;

import javax.net.ssl.SSLSocket;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * A MakeHandShake class.
 * 
 * @author Vladimir Tsastsin
 */
final class MakeHandShake extends Thread {
    private static Logger LOG = Logger.getLogger(MakeHandShake.class);

    /** Security Socket in which handshakes must be done. */
    private final SSLSocket socket;

    /** Show the status of handshakes. */
    public int done;

    /**
     * Constructor. Create class MakeHandShake with specified security socket
     * 
     * @param inSocket
     *            security socket in which handshakes must be done.
     */
    public MakeHandShake(SSLSocket inSocket) {
        socket = inSocket;
        done = 0;
    }

    /**
     * * Try to make handshakes with specified SSLSocket (security socket).
     */
    private void tryMakeHandShake() {
        try {
            socket.startHandshake();
            done = 1;
        } catch (Exception ex) {
            LOG.error("BANKLINK: unable to make handshakes", ex);
        }
    }

    /**
     * Start class in new thread.
     */
    @Override
    public void run() {
        tryMakeHandShake();
    }
}