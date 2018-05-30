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
package com.nortal.banklink.core;

// TODO: Auto-generated Javadoc
/**
 * This class is used to hold version information.
 * <P/>
 * The version number is made up of three dot-separated fields:
 * 
 * <P>
 * 
 * &quot;<b>major.minor.revision</b>&quot;
 * 
 * <P>
 * 
 * The <B>major</B>, <B>minor</B> and <B>revision</B> fields are <I>integer</I> numbers represented in decimal notation and have
 * the following meaning:
 * <UL>
 * 
 * <P/>
 * <LI><B>major</B> - When the major version changes (in ex. from
 * 
 * &quot;1.5.12&quot; to &quot;2.0.0&quot;), then backward compatibility
 * 
 * with previous releases is not granted.</LI>
 * <P/>
 * 
 * 
 * 
 * <P/>
 * <LI><B>minor</B> - When the minor version changes (in ex. from &quot;1.5.12&quot; to &quot;1.6.0&quot;), then backward
 * compatibility with previous releases is granted, but something changed in the implementation. (ie it methods could have been
 * added)</LI>
 * <P/>
 * 
 * 
 * 
 * <P/>
 * <LI><B>revision</B> - When the revision version changes (in ex. from &quot;1.5.12&quot; to &quot;1.5.13&quot;), then the the
 * changes are small forward compatible bug fixes or documentation modifications etc.</LI>
 * 
 * </UL>
 * 
 * @author <A HREF="mailto:ago.meister@webmedia.ee">Ago Meister</A>
 */
public final class Version {

    /**
     * Instantiates a new version.
     */
    private Version() {
    }

    /** The Constant major. */
    public static final int major = 1;

    /** The Constant minor. */
    public static final int minor = 1;

    /** The Constant revision. */
    public static final int revision = 5;

    /** The Constant build. */
    public static final int build = 1;

    /** The Constant version. */
    public static final String version = major + "." + minor + "." + revision + "." + build;
}