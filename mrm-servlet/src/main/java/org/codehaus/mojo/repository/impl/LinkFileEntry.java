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

package org.codehaus.mojo.repository.impl;

import org.codehaus.mojo.repository.api.BaseFileEntry;
import org.codehaus.mojo.repository.api.DirectoryEntry;
import org.codehaus.mojo.repository.api.FileEntry;
import org.codehaus.mojo.repository.api.FileSystem;

import java.io.IOException;
import java.io.InputStream;

public class LinkFileEntry
    extends BaseFileEntry
{

    private final FileEntry entry;

    public LinkFileEntry( FileSystem fileSystem, DirectoryEntry parent, FileEntry entry )
    {
        super( fileSystem, parent, entry.getName() );
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
        return entry.getSize();
    }

    public InputStream getInputStream()
        throws IOException
    {
        return entry.getInputStream();
    }

}
