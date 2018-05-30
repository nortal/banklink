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

/**
 * The Class InvalidParameterException.
 */
public class InvalidParameterException extends BanklinkException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * The constructor that sets the error message accompanied with exception.
     */
    public InvalidParameterException() {
        super();
    }

    /**
     * The constructor that sets the error message accompanied with exception.
     * 
     * @param error
     *            the error
     */
    public InvalidParameterException(String error) {
        super(error);
    }

    /**
     * The constructor that sets the error message accompanied with exception.
     * 
     * @param error
     *            the error
     * @param e
     *            the e
     */
    public InvalidParameterException(String error, Exception e) {
        super(error, e);
    }
}
