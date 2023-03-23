/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib.internal.cherokee;

/**
 * <p>Thrown to indicate that a block of code has not been implemented.
 * This exception supplements {@code UnsupportedOperationException}
 * by providing a more semantically rich description of the problem.</p>
 *
 * <p>{@code NotImplementedException} represents the case where the
 * author has yet to implement the logic at this point in the program.
 * This can act as an exception based TODO tag. </p>
 *
 * <pre>
 * public void foo() {
 *   try {
 *     // do something that throws an Exception
 *   } catch (Exception ex) {
 *     // don't know what to do here yet
 *     throw new NotImplementedException("TODO", ex);
 *   }
 * }
 * </pre>
 * <p>
 * This class was originally added in Lang 2.0, but removed in 3.0.
 *
 * @since 3.2
 */
public class NotImplementedException extends UnsupportedOperationException {

    private static final long serialVersionUID = 20131021L;

    private final String code;

    /**
     * Constructs a NotImplementedException.
     *
     * @since 3.10
     */
    public NotImplementedException() {
        this.code = null;
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param message description of the exception
     * @since 3.2
     */
    public NotImplementedException(final String message) {
        this(message, (String) null);
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param message description of the exception
     * @param code    code indicating a resource for more information regarding the lack of implementation
     * @since 3.2
     */
    public NotImplementedException(final String message, final String code) {
        super(message);
        this.code = code;
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param cause cause of the exception
     * @since 3.2
     */
    public NotImplementedException(final Throwable cause) {
        this(cause, null);
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param cause cause of the exception
     * @param code  code indicating a resource for more information regarding the lack of implementation
     * @since 3.2
     */
    public NotImplementedException(final Throwable cause, final String code) {
        super(cause);
        this.code = code;
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param message description of the exception
     * @param cause   cause of the exception
     * @since 3.2
     */
    public NotImplementedException(final String message, final Throwable cause) {
        this(message, cause, null);
    }

    /**
     * Constructs a NotImplementedException.
     *
     * @param message description of the exception
     * @param cause   cause of the exception
     * @param code    code indicating a resource for more information regarding the lack of implementation
     * @since 3.2
     */
    public NotImplementedException(final String message, final Throwable cause, final String code) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Obtain the not implemented code. This is an unformatted piece of text intended to point to
     * further information regarding the lack of implementation. It might, for example, be an issue
     * tracker ID or a URL.
     *
     * @return a code indicating a resource for more information regarding the lack of implementation
     */
    public String getCode() {
        return this.code;
    }
}