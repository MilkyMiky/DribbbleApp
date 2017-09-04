package com.miky.dev.dribbbleapp.ui.shots;


import android.content.Context;
import android.util.Log;

import com.miky.dev.dribbbleapp.logic.di.ShotRepository;
import com.miky.dev.dribbbleapp.ui.App;

import javax.inject.Inject;

public class ShotsPresenter {
    private IShotsView view;

    @Inject
    Context context;

    @Inject
    ShotRepository repository;

    ShotsPresenter() {
        App.getInstance().getComponent().inject(this);
    }

    void setView(IShotsView view) {
        this.view = view;
    }

    void getData(boolean update) {

        view.showProgress(true);

        repository.getShots(update).subscribe(shotsResponse -> {
            Log.d("log", "OK");
        }, throwable -> {

            Log.d("log", "ERROR " + throwable.getMessage());
            view.showProgress(false);
        });
        view.showProgress(false);
    }
}
