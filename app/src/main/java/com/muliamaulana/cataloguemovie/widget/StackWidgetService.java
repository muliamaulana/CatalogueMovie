package com.muliamaulana.cataloguemovie.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by muliamaulana on 07/04/18.
 */

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MovieRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}