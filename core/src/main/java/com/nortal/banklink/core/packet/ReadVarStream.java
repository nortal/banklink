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

import java.io.InputStream;

import org.apache.log4j.Logger;

import com.nortal.banklink.core.Version;

// TODO: Auto-generated Javadoc
/**
 * The Class ReadVarStream.
 */
final class ReadVarStream extends Thread {
    private static Logger LOG = Logger.getLogger(ReadVarStream.class);

    /**
     * 
     * InputStream from which data must be read.
     */
    private final InputStream reader;

    /** The parent. */
    private final Packet parent;

    /**
     * Show the status of reading.
     */
    protected int done = 0;

    /**
     * Constructor. Create class with specified InputStream and Packet
     * 
     * @param in
     *            input stream, from which data must be read.
     * @param test
     *            Packet from which it used. (Packet which was sent.)
     */
    public ReadVarStream(InputStream in, Packet test) {
        reader = in;
        parent = test;
    }

    /**
     * 
     * Try to read from input stream.
     * 
     * Can be use for to control during what time data is expected from socket
     * 
     * (slow internet connection, slow proxy ... )
     */
    private void tryToRead() {
        try {
            byte b[] = new byte[2048];
            int count = reader.read(b);
            while (count != -1) {
                String temp = new String(b, 0, count - 1);

                if (parent.getServerHeader() == null) {
                    parent.setServerHeader(temp);
                } else {
                    parent.setServerHeader(parent.getServerHeader() + temp);
                }
                count = reader.read(b);
            }
            done = 1;
        } catch (Exception ex) {
            LOG.error("Banklink " + Version.version + ": unable to read - ", ex);
        }
    }

    /**
     * Start in new thread.
     */
    @Override
    public void run() {
        tryToRead();
    }
}
