package com.pepedevs.corelib.utils.reflection.resolver.minecraft;

import com.pepedevs.corelib.utils.reflection.resolver.ClassResolver;
import com.pepedevs.corelib.utils.version.Version;

public class CraftClassResolver extends ClassResolver {

    @Override
    public Class resolve(String... names) throws ClassNotFoundException {
        for (int i = 0; i < names.length; i++) {
            if (!names[i].startsWith("org.bukkit")) {
                names[i] = Version.SERVER_VERSION.getObcPackage() + "." + names[i];
            }
        }
        return super.resolve(names);
    }

}
