package com.pepedevs.radium.utils.reflection.resolver.minecraft;

import com.pepedevs.radium.utils.reflection.resolver.ClassResolver;
import com.pepedevs.radium.utils.version.Version;

/** {@link ClassResolver} for <code>net.minecraft.server.*</code> classes */
public class NMSClassResolver extends ClassResolver {

    @Override
    public Class resolve(String... names) throws ClassNotFoundException {
        for (int i = 0; i < names.length; i++) {
            if (names[i].startsWith("net.minecraft")) continue;

            /* use the whole name */
            names[i] = Version.SERVER_VERSION.getNmsPackage() + "." + names[i];
        }
        return super.resolve(names);
    }
}
