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

import com.nortal.banklink.core.BanklinkException;
import com.nortal.banklink.core.Version;
import com.nortal.banklink.core.algorithm.Algorithm;
import com.nortal.banklink.core.log.PacketForwardLog;
import com.nortal.banklink.core.log.PacketLog;
import com.nortal.banklink.core.log.PacketSignLog;
import com.nortal.banklink.core.log.PacketVerifyLog;
import com.nortal.banklink.core.packet.param.PacketParameter;
import com.nortal.banklink.core.packet.verify.PacketDateAndTimeVerifier;
import com.nortal.banklink.core.packet.verify.PacketDateTimeVerifier;
import com.nortal.banklink.core.packet.verify.PacketNonceVerifier;
import com.nortal.banklink.core.packet.verify.PacketVerifier;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

/*
 *  NR    DATE        AUTHOR									DESCRIPTION
 *  4     06.02.14    Lauri Lättemäe          Replaced logging logic with log4j implementation. Log messages are constructed by 
 *                                            {@link PacketLog} keeping same format as FileLogger or BanklinkCategory 
 *                                            for backwards compatibility. To enable operations logs globally add log4j configuration 
 *                                            for package com.nortal.banklink.core.log with debug level DEBUG. To enable operation 
 *                                            specific logging add separate log4j configurations with debug level DEBUG for 
 *                                            {@link PacketSignLog}, {@link PacketVerifyLog} or {@link PacketForwardLog} as necessary.
 *	3			22.04.02		Ago Meister							Removed config object from class.
 *  2			07.08.01		Vladimir Tsastsin				added getInstance
 *  1			07.08.01		Vladimir Tsastsin				Class finished
 */
/**
 * A Packet class. Abstract class of all Packets.
 * 
 * @author Vladimir Tsastsin
 * @author Alrik Peets
 */
public class Packet {
    /** The Constant DEFAULT_VERIFIERS. */
    private static final List<PacketVerifier> DEFAULT_VERIFIERS = new ArrayList<>();
    static {
        DEFAULT_VERIFIERS.add(new PacketDateAndTimeVerifier());
        DEFAULT_VERIFIERS.add(new PacketNonceVerifier());
        DEFAULT_VERIFIERS.add(new PacketDateTimeVerifier());
    }

    /** All PacketParameters of Packet. */
    private final PacketParameterMap parameters = new PacketParameterMap();

    /** Crypto algorithm. */
    protected final Algorithm<?, ?> algorithm;

    /** The nonce manager. */
    protected final NonceManager nonceManager;

    /** Headers which will be sent (forward) to some server. */
    private final String mainHeader[] = new String[13];

    /** Answer from server to which query was sent (Headers). */
    private String serverHeader;

    private String reEncoding;

    /** The packet id. */
    private final String packetId;

    public String getPacketId() {
        return packetId;
    }

    /** The mac name. */
    private String macName = "VK_MAC";

    /**
     * Contractor. Create Packet with specified algorithm and conguration
     * 
     * @param packetId
     *            the packet id
     * @param algorithm
     *            Packet's algorithm
     */
    protected Packet(String packetId, Algorithm<?, ?> algorithm) {
        this(packetId, algorithm, (NonceManager) null);
    }

    /**
     * Contractor. Create Packet with specified algorithm and conguration
     * 
     * @param packetId
     *            the packet id
     * @param algorithm
     *            Packet's algorithm
     * @param nonceManager
     *            the nonce manager
     */
    protected Packet(String packetId, Algorithm<?, ?> algorithm, NonceManager nonceManager) {
        this.algorithm = algorithm;
        this.packetId = packetId;
        this.nonceManager = nonceManager;
        init();
    }

    /**
     * Convert String to HEX String for sending using HTTP or HTTPS.
     * 
     * @param sNormal
     *            where all char will be replaced with their HEX
     * @return String where ALL char in HEX
     */
    private static String convertToHex(String sNormal) {
        String rString = "";
        byte[] b = sNormal.getBytes();
        for (int x = 0; x < sNormal.length(); x++) {
            String tempString = Integer.toHexString(b[x]);
            if (tempString.length() == 8) {
                tempString = tempString.substring(6, 8);
            }
            rString += "%" + tempString;
        }
        return rString;
    }

    /**
     * Generate heagers, which will be sent.
     * 
     * @param queryType
     *            POST/GET = True/False
     * @param path
     *            URL to which sent all data
     * @param server
     *            server to which all data must be sent.
     */
    private void generateHeader(boolean queryType, String path, String server) {
        String postString = "";
        for (Enumeration<PacketParameter> e = parameters(); e.hasMoreElements();) {
            PacketParameter packet = e.nextElement();
            postString += packet.getName() + "=" + convertToHex(packet.getValue()) + "&";
        }
        postString = postString.substring(0, postString.length() - 1);
        if (queryType) {
            mainHeader[0] = new String("POST " + path + " HTTP/1.1");
        } else {
            if (postString.compareTo("") != 0) {
                path = path + "?" + postString;
            }
            mainHeader[0] = new String("GET " + path + " HTTP/1.1");
        }
        mainHeader[1] = new String("Accept: image/gif, image/x-xbitmap, image/jpeg, "
                + "image/pjpeg, application/vnd.ms-excel," + "application/msword, */*");
        mainHeader[2] = new String("Referer: www.mebmedia.ee");
        mainHeader[3] = new String("Accept-Language: en;q=0.5");
        mainHeader[4] = new String("Accept-Encoding: gzip, deflate");
        mainHeader[5] = new String("User-Agent: Mozilla/4.0 (compatible; MSIE 5.0; " + "Windows NT; DigExt)");
        mainHeader[6] = new String("Host: " + server);
        mainHeader[7] = new String("Via: ");
        mainHeader[8] = new String("X-Forwarded-For: ");
        mainHeader[9] = new String("Connection: keep-alive"); // -- mainHeader
                                                              // [9] = new
                                                              // String
                                                              // ("Connection: close");
        mainHeader[10] = new String("Content-type: application/x-www-form-urlencoded");
        mainHeader[11] = new String("Content-length: " + postString.length());
        mainHeader[12] = new String(postString);
    }

    /**
     * Sign m_parameters (which m_isMac) and write the signature to m_parameters
     * to MAC key.
     * 
     * @throws BanklinkException
     *             the banklink exception
     */
    public void sign() throws BanklinkException {
        try {
            String MAC = algorithm.sign(parameters());
            // begin logging
            PacketLog pl = new PacketSignLog();
            for (Enumeration<PacketParameter> e = parameters(); e.hasMoreElements();) {
                PacketParameter parameter = e.nextElement();
                pl.setParameter(parameter.getName(), parameter.getValue());
            }
            pl.setParameter("STRING", algorithm.getMacString(parameters()));
            pl.setParameter("SIGNATURE", MAC);
            Logger.getLogger(pl.getClass()).debug(pl);
            // end logging
            setParameter(getMacName(), MAC);
        } catch (Exception e) {
            throw new BanklinkException(e.toString(), e);
        }
    }

    /**
     * Check if the MAC value is equal to signature of m_parameters.
     * 
     * @return <PRE>
     * true
     * </PRE>
     * 
     *         if digital signature is correct and
     * 
     *         <PRE>
     * false
     * </PRE>
     * 
     *         otherwise
     * @throws BanklinkException
     *             the banklink exception
     */

    public boolean verify() throws BanklinkException {
        return verify(DEFAULT_VERIFIERS);
    }

    /**
     * Verify.
     * 
     * @param verifiers
     *            the verifiers
     * @return true, if successful
     * @throws BanklinkException
     *             the banklink exception
     */
    public boolean verify(List<PacketVerifier> verifiers) throws BanklinkException {
        try {
            String MAC = getParameterValue(getMacName());
            boolean answer = algorithm.verify(parameters(), MAC);

            if (!answer) {
                logPacketVerifyAttempt(answer);
                return false;
            }

            if (CollectionUtils.isNotEmpty(verifiers))
                for (PacketVerifier verifier : verifiers)
                    answer &= verifier.verify(this);

            logPacketVerifyAttempt(answer);

            return answer;
        } catch (Exception e) {
            if (e instanceof BanklinkException) {
                throw (BanklinkException) e;
            }
            throw new BanklinkException("Packet verify failed. Cause: " + e.toString(), e);
        }
    }

    private void logPacketVerifyAttempt(boolean answer) {
        // begin logging
        PacketLog pl = new PacketVerifyLog();
        pl.setParameter("RECODEDFROM", reEncoding);

        for (Enumeration<PacketParameter> e = parameters(); e.hasMoreElements();) {
            PacketParameter parameter = e.nextElement();
            pl.setParameter(parameter.getName(), parameter.getValue());
        }

        pl.setParameter("STRING", algorithm.getMacString(parameters()));
        pl.setParameter("RESULTCODE", (new Boolean(answer).toString()));
        Logger.getLogger(pl.getClass()).debug(pl);
        // end logging
    }

    /**
     * Gets the mac name.
     * 
     * @return MAC parameter name
     */
    public String getMacName() {
        return macName;
    }

    /**
     * Sets the mac name.
     * 
     * @param macName
     *            the new mac name
     */
    public void setMacName(String macName) {
        this.macName = macName;
    }

    /**
     * Generate nonce.
     * 
     * @return the string
     */
    public String generateNonce() {
        if (nonceManager != null) {
            return nonceManager.generateNonce();
        }
        return null;
    }

    /**
     * Verify nonce.
     * 
     * @param nonce
     *            the nonce
     * @return true, if successful
     */
    public boolean verifyNonce(String nonce) {
        if (nonceManager != null) {
            return nonceManager.verifyNonce(nonce);
        }
        return false;
    }

    /** Initialization. */
    public void init() {
        parameters.reset();
    }

    /**
     * Initialization. Get from request all require paramerets and save their
     * values to PacketParameters (can be any extended class).
     * 
     * @param request
     *            HttpServletRequest from which all parameters will be received
     * @throws InvalidParameterException
     *             the invalid parameter exception
     */
    public void init(HttpServletRequest request) throws InvalidParameterException {
        init(request, "UTF-8");
    }

    public void init(HttpServletRequest request, String reencodeFrom) throws InvalidParameterException {
        init();
        this.reEncoding = reencodeFrom;
        parameters.init(request, getMacName(), reencodeFrom);
    }

    /**
     * Parameters.
     * 
     * @return the enumeration
     */
    public Enumeration<PacketParameter> parameters() {
        return parameters.parameters();
    }

    /**
     * Return value of specified key.
     * 
     * @param key
     *            name of key
     * @return value value of specified key or null if the parameter does not
     *         exist or the key is null
     */

    public String getParameterValue(String key) {
        return parameters.getParameterValue(key);
    }

    /**
     * Set value of specified key.
     * 
     * @param key
     *            name of key
     * @param value
     *            value which to set to specified key
     * @throws InvalidParameterException
     *             the invalid parameter exception
     */
    public void setParameter(String key, String value) throws InvalidParameterException {
        parameters.setParameter(key, value);
    }

    /**
     * Checks for parameter.
     * 
     * @param key
     *            the key
     * @return true, if successful
     */
    public boolean hasParameter(String key) {
        return parameters.containsKey(key);
    }

    /**
     * Adds the packet parameter.
     * 
     * @param packetParameter
     *            the packet parameter
     */
    protected void addPacketParameter(PacketParameter packetParameter) {
        parameters.put(packetParameter);
    }

    /**
     * Gets the server header.
     * 
     * @return the server header
     */
    public String getServerHeader() {
        return serverHeader;
    }

    /**
     * Sets the server header.
     * 
     * @param serverHeader
     *            the new server header
     */
    public void setServerHeader(String serverHeader) {
        this.serverHeader = serverHeader;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Packet" + packetId + " " + parameters.toString();
    }

    /**
     * Send request to server using HTTPS protocol.
     * 
     * @param queryType
     *            the type of request POST/GET
     * @param path
     *            the URL to which data must be send(format
     *            "/directory/filename.ext").
     * @param remoteName
     *            server name to which we connect.
     * @param remotePort
     *            server port to which we connect.
     * @param proxyName
     *            proxy name or "" if not to use proxy.
     * @param proxyPort
     *            proxy port number.
     * @throws BanklinkException
     *             the banklink exception
     */
    public void sendHttps(boolean queryType, String path, String remoteName, int remotePort, String proxyName,
            int proxyPort) throws BanklinkException {

        String url = remoteName;
        if (remotePort != 80) {
            url += ":" + Integer.toString(remotePort);
        }
        url += path;

        generateHeader(queryType, path, remoteName);
        logForward("HTTPS", url);

        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            SSLSocket remoteServer = (SSLSocket) factory.createSocket(remoteName, remotePort);

            if (proxyName.equals("")) {
                remoteServer = (SSLSocket) factory.createSocket(remoteName, remotePort);
            }

            else {
                Socket tunnel = new Socket(proxyName, proxyPort);
                doTunnelHandshake(tunnel, remoteName, remotePort);
                remoteServer = (SSLSocket) factory.createSocket(tunnel, remoteName, remotePort, true);
            }

            long time;

            MakeHandShake tryMake = new MakeHandShake(remoteServer);
            tryMake.start();
            time = System.currentTimeMillis();

            while ((tryMake.done == 0) && ((System.currentTimeMillis() - time) < 20000)) {
                // making handshakes
            }

            if (tryMake.done == 0) {
                remoteServer.close();
                throw new IOException("Banklink " + Version.version + ": can not make handshakes.");
            }

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(remoteServer.getOutputStream())));
            InputStream in = remoteServer.getInputStream();

            // 10 first headers require for both method GET/POST.
            for (int i = 0; i < 2; i++) {
                out.println(mainHeader[i]);
            }

            // 3 last header require only in we use POST method
            if (!mainHeader[4].equals("")) {
                out.println(mainHeader[2]);
                out.println(mainHeader[3]); // length of POST data
                out.println();
                out.println(mainHeader[4]); // POST data
            }
            out.println();
            out.println();
            out.flush();

            setServerHeader(null);

            ReadVarStream tryRead = new ReadVarStream(in, this);

            tryRead.start();

            time = System.currentTimeMillis();

            while ((tryRead.done == 0) && ((System.currentTimeMillis() - time) < 30000)) {

                // waiting for input stream.

            }

            remoteServer.close();
            in.close();
            out.close();

            if (getServerHeader() != null) {
                throw new IOException("Server doesn't return any headers.");
            }

            if (getServerHeader().indexOf("200") < 0) {
                throw new IOException("Server returns error.");
            }
        } catch (Exception e) {
            throw new BanklinkException(e.toString(), e);
        }
    }

    /**
     * Tell our tunnel where we want to CONNECT, and look for the right reply.
     * Throw IOException if anything goes wrong. Used just for HTTPS connections
     * 
     * @param tunnel
     *            socket to proxy, which is used for receiving some URL.
     * @param host
     *            name of host to which we want to connect(make tunnel)
     * @param port
     *            port number to which we want to connect(make tunnel)
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static void doTunnelHandshake(Socket tunnel, String host, int port) throws IOException {
        OutputStream out = tunnel.getOutputStream();
        String msg = "CONNECT " + host + ":" + port + " HTTP/1.0\n" + "User-Agent: nortal-banklink" + "\r\n\r\n";

        byte b[];

        b = msg.getBytes();

        out.write(b);
        out.flush();

        byte reply[] = new byte[200];
        int replyLen = 0;
        int newlinesSeen = 0;
        boolean headerDone = false;

        InputStream in = tunnel.getInputStream();

        // read header (all) till the end.

        while (newlinesSeen < 2) {
            int i = in.read();
            if (i < 0) {
                throw new IOException("Banklink " + Version.version + ": unexpected EOF from proxy.");
            }

            if (i == '\n') {
                headerDone = true;
                ++newlinesSeen;
            } else if (i != '\r') {
                newlinesSeen = 0;
                if (!headerDone && replyLen < reply.length) {
                    reply[replyLen++] = (byte) i;
                }
            }
        } // end while

        String replyStr = new String(reply, 0, replyLen);

        // check if the returned headers is OK.
        if (!replyStr.startsWith("HTTP/1.0 200")) {
            throw new IOException("Banklink " + Version.version + ": unable to tunnel through proxy." + "Proxy returned \""
                    + replyStr + "\"");
        }

        // tunneling Handshake was successful
    }

    /**
     * Log forward requests.
     * 
     * @param channel
     *            HTTP or HTTPS
     * @param destination
     *            url to which data must be sent
     * @throws BanklinkException
     *             the banklink exception
     */
    public void logForward(String channel, String destination) throws BanklinkException {
        // begin logging
        PacketLog pl = new PacketForwardLog();
        for (Enumeration<PacketParameter> e = parameters(); e.hasMoreElements();) {
            PacketParameter parameter = e.nextElement();
            pl.setParameter(parameter.getName(), parameter.getValue());
        }
        pl.setParameter("STRING", algorithm.getMacString(parameters()));
        pl.setParameter("CHANNEL", channel);
        pl.setParameter("DESTINATION", destination);
        Logger.getLogger(pl.getClass()).debug(pl);
        // end logging
    }

    /**
     * Return string as HTML formated form with all necessary field which must be
     * in that kind of form.
     * 
     * @return String as HTML formated form with all necessary field which must be
     *         in that kind of form.
     */
    public String html() {
        PacketParameter packetParameter = null;
        String formString = "";
        for (Enumeration<PacketParameter> e = parameters(); e.hasMoreElements();) {
            packetParameter = e.nextElement();
            formString += " <input type=\"hidden\" name=\"" + packetParameter.getName() + "\" value=\""
                    + packetParameter.getValue() + "\"/>\n";
        }
        return formString;
    }

    /**
     * Json.
     * 
     * @return the string
     */
    public String json() {
        String json = "{";
        for (Enumeration<PacketParameter> e = parameters(); e.hasMoreElements();) {
            PacketParameter par = e.nextElement();
            json += "\"" + par.getName() + "\":\"" + StringEscapeUtils.escapeJson(par.getValue()) + "\"";
            if (e.hasMoreElements())
                json += ",";
        }
        json += "}";
        return json;
    }

    /**
     * Send request to server using HTTP protocol.
     * 
     * @param queryType
     *            the type of request POST/GET
     * @param path
     *            the URL to which data must be send(format
     *            "/directory/filename.ext").
     * @param remoteName
     *            server name to which we connect.
     * @param remotePort
     *            server port to which we connect.
     * @param proxyName
     *            proxy name or "" if not to use proxy.
     * @param proxyPort
     *            proxy port number.
     * @throws BanklinkException
     *             the banklink exception
     */

    public void sendHttp(boolean queryType, String path, String remoteName, int remotePort, String proxyName,
            int proxyPort) throws BanklinkException {
        String url = remoteName;
        if (remotePort != 80) {
            url += ":" + Integer.toString(remotePort);
        }
        url += path;

        if (proxyName.compareTo("") != 0) {
            path = "http://" + url;
        }

        generateHeader(queryType, path, remoteName);

        if (proxyName.compareTo("") != 0) {
            remoteName = proxyName;
            remotePort = proxyPort;
        }

        logForward("HTTP", url);
        try {
            Socket remoteServer = new Socket(remoteName, remotePort);
            InputStream in = remoteServer.getInputStream();
            DataOutputStream out = new DataOutputStream(remoteServer.getOutputStream());

            for (int i = 0; i < 2; i++) {
                // mainHeader[i] = mainHeader[i] + "\r\n";
                out.writeBytes(mainHeader[i] + "\r\n");
            }

            // 3 last header require only in we use POST method
            if (!mainHeader[12].equals("")) {
                // out.writeBytes (mainHeader[2]+ "\r\n"); // require for POST
                // out.writeBytes (mainHeader[3]+ "\r\n"); // length of POST
                // data
                for (int x = 3; x < 12; x++) {
                    out.writeBytes(mainHeader[x] + "\r\n"); // length of POST
                                                            // data
                }
                out.writeBytes("\r\n");
                out.writeBytes(mainHeader[12] + "\r\n"); // POST data
            }

            else {
                out.writeBytes("\r\n");
                out.writeBytes("\r\n");
            }

            setServerHeader(null);
            ReadVarStream tryRead = new ReadVarStream(in, this);
            tryRead.start();
            long time = System.currentTimeMillis();
            while ((tryRead.done == 0) && ((System.currentTimeMillis() - time) < 30000)) {
                // waiting for input stream.
            }

            remoteServer.close();
            in.close();
            out.close();

            if (getServerHeader() == null) {
                throw new IOException("Banklink " + Version.version + ": server did not return any headers.");
            }

            if (getServerHeader().indexOf("200") < 0) {
                throw new IOException("Banklink " + Version.version + ": server returned error.");
            }
        } catch (Exception e) {
            throw new BanklinkException(e.toString(), e);
        }
    }
}
