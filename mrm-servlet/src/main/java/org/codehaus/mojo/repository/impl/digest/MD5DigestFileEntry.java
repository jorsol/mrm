/*
 * Copyright 2011 Stephen Connolly
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.mojo.repository.impl.digest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.mojo.repository.api.BaseFileEntry;
import org.codehaus.mojo.repository.api.DirectoryEntry;
import org.codehaus.mojo.repository.api.FileEntry;
import org.codehaus.mojo.repository.api.FileSystem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5DigestFileEntry
    extends BaseFileEntry
{

    private final FileEntry entry;

    public MD5DigestFileEntry( FileSystem fileSystem, DirectoryEntry parent, FileEntry entry )
    {
        super( fileSystem, parent, entry.getName() + ".md5" );
        this.entry = entry;
    }

    public long getLastModified()
        throws IOException
    {
        return entry.getLastModified();
    }

    public long getSize()
        throws IOException
    {
        return 32;
    }

    public InputStream getInputStream()
        throws IOException
    {
        return new ByteArrayInputStream( getContent() );
    }

    private byte[] getContent()
        throws IOException
    {
        InputStream is = entry.getInputStream();
        try
        {
            MessageDigest digest = MessageDigest.getInstance( "MD5" );
            digest.reset();
            byte[] buffer = new byte[8192];
            int read;
            while ( ( read = is.read( buffer ) ) > 0 )
            {
                digest.update( buffer, 0, read );
            }
            final String md5 = StringUtils.leftPad( new BigInteger( 1, digest.digest() ).toString( 16 ), 32, "0" );
            return md5.getBytes();
        }
        catch ( NoSuchAlgorithmException e )
        {
            IOException ioe = new IOException( "Unable to calculate hash" );
            ioe.initCause( e );
            throw ioe;
        }

    }
}
