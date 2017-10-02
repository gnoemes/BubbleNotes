package com.gnoemes.bubblenotes.repo.local;

import io.objectbox.BoxStore;

/**
 * Created by kenji1947 on 02.10.2017.
 */

public class RepoDi {
    private static LocalRepository localRepository;

    public static LocalRepository getLocalRepo(BoxStore boxStore) {
        if (localRepository == null) {
            localRepository = new LocalRepositoryImpl(boxStore);
        }
        return localRepository;
    }
}
