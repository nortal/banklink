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

import com.nortal.banklink.core.BanklinkException;

// TODO: Auto-generated Javadoc
/**
 * This exception is thrown when a requested parameter is not found.
 * 
 * @author Imre Lumiste
 */
public class LogParameterException extends BanklinkException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * The default constructor.
     * 
     * @since 02.08.2001
     */
    public LogParameterException() {
        super();
    }

    /**
     * The constructor that sets the error message accompanied with exception.
     * 
     * @since 02.08.2001
     * 
     * @param s
     *            the s
     */
    public LogParameterException(String s) {
        super(s);
    }

    /**
     * The constructor that sets the error message accompanied with exception.
     * 
     * @param s
     *            the s
     * @param ex
     *            the ex
     */
    public LogParameterException(String s, Exception ex) {
        super(s, ex);
    }
}