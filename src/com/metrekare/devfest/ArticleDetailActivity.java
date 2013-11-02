/*
 * Copyright 2013 The Android Open Source Project
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

package com.metrekare.devfest;

import android.content.res.Configuration;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * An activity representing a single Article detail screen. This activity is
 * only used on handset devices. On tablet-size devices, item details are
 * presented side-by-side with a list of items in a {@link ArticleListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ArticleDetailFragment}.
 */
public class ArticleDetailActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        // Show the Up button in the action bar.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ArticleDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(ArticleDetailFragment.ARG_ITEM_ID));
        fragment.setArguments(arguments);
        
        getSupportFragmentManager().beginTransaction()
        	.add(R.id.article_detail_container, fragment).commit();
        
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
