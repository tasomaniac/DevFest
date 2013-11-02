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

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.metrekare.devfest.event.BusProvider;
import com.metrekare.devfest.event.ListItemClickEvent;
import com.netmera.mobile.Netmera;
import com.squareup.otto.Subscribe;

/**
 * An activity representing a list of Articles. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ArticleDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ArticleListFragment} and the item details (if present) is a
 * {@link ArticleDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ArticleListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class ArticleListActivity extends SherlockFragmentActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    
    public PullToRefreshAttacher attacher;
    
    
    @Override
    protected void onStart() {
    	super.onStart();
    	BusProvider.getInstance().register(this);
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        attacher = PullToRefreshAttacher.get(this);
        
        Netmera.init(getApplicationContext(), "ZGoweEpuVTlOVEl6TWpjeU1HVmxOR0l3WWpjMFlUQm1NbVUwT1RWakptRTlaR1YyWm1WemRDMHlKZw==");
        
//        new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				
//				try {
//				{
//				NetmeraContent service = new NetmeraContent("article");
//				service.add("author", "Isaac Newton");
//				service.add("article_date", "2 minutes ago");
//				service.add("title", "Bacon ipsum dolor sit amet");
//				service.add("imageUrl", "https://raw.github.com/ankurkotwal/making-apps-beautiful/master/XYZReader_Final/src/main/res/drawable-nodpi/p1.jpeg");
//				
//				
//				service.create();
//				}
//
//				{
//				NetmeraContent service = new NetmeraContent("article");
//				service.add("author", "Margaret Thatcher");
//				service.add("article_date", "18 minutes ago");
//				service.add("title", "Tri-tip spare ribs pancetta t-bone short ribs meatball");
//				service.add("imageUrl", "https://raw.github.com/ankurkotwal/making-apps-beautiful/master/XYZReader_Final/src/main/res/drawable-nodpi/p2.jpeg");
//				
//				service.create();
//				}
//
//				{
//				NetmeraContent service = new NetmeraContent("article");
//				service.add("author", "Vincent Van");
//				service.add("article_date", "5 hours ago");
//				service.add("title", "Strip steak ground round doner shankle");
//				service.add("imageUrl", "https://raw.github.com/ankurkotwal/making-apps-beautiful/master/XYZReader_Final/src/main/res/drawable-nodpi/p3.jpeg");
//				
//				service.create();
//				}
//
//				{
//				NetmeraContent service = new NetmeraContent("article");
//				service.add("author", "Tom Brokaw");
//				service.add("article_date", "1 day ago");
//				service.add("title", "Corned beef drumstick tri-tip flank ribeye tongue");
//				service.add("imageUrl", "https://raw.github.com/ankurkotwal/making-apps-beautiful/master/XYZReader_Final/src/main/res/drawable-nodpi/p4.jpeg");
//				
//				service.create();
//				}
//
//				{
//				NetmeraContent service = new NetmeraContent("article");
//				service.add("author", "Thomas Edison");
//				service.add("article_date", "1 day ago");
//				service.add("title", "Pastrami ham hock short ribs turkey boudin hamburger bresaola shank");
//				service.add("imageUrl", "https://raw.github.com/ankurkotwal/making-apps-beautiful/master/XYZReader_Final/src/main/res/drawable-nodpi/p5.jpeg");
//				
//				service.create();
//				}
//
//				{
//				NetmeraContent service = new NetmeraContent("article");
//				service.add("author", "Edgar Allen Poe");
//				service.add("article_date", "2 days ago");
//				service.add("title", "Spare ribs drumstick kielbasa, ground round ball tip pork shoulder short ribs");
//				service.add("imageUrl", "https://raw.github.com/ankurkotwal/making-apps-beautiful/master/XYZReader_Final/src/main/res/drawable-nodpi/p6.jpeg");
//				
//				service.create();
//				}
//
//				{
//				NetmeraContent service = new NetmeraContent("article");
//				service.add("author", "Plato");
//				service.add("article_date", "2 days ago");
//				service.add("title", "Shankle venison shank strip");
//				service.add("imageUrl", "https://raw.github.com/ankurkotwal/making-apps-beautiful/master/XYZReader_Final/src/main/res/drawable-nodpi/p7.jpeg");
//
//				
//				service.create();
//				}
//
//				{
//				NetmeraContent service = new NetmeraContent("article");
//				service.add("author", "Robin Williams");
//				service.add("article_date", "4 days ago");
//				service.add("title", "Steak tongue; pig shank t-bone, sausage hamburger ham venison");
//				service.add("imageUrl", "https://raw.github.com/ankurkotwal/making-apps-beautiful/master/XYZReader_Final/src/main/res/drawable-nodpi/p8.jpeg");
//				
//				service.create();
//				}
//				} catch(NetmeraException e) {}
//
//			}
//		}).start();;
        
        
        setContentView(R.layout.activity_article_list);

        if (findViewById(R.id.article_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ArticleListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.article_list))
                    .setActivateOnItemClick(true);
        }

        if (savedInstanceState == null && mTwoPane) {
            Bundle arguments = new Bundle();
//            arguments.putString(ArticleDetailFragment.ARG_ITEM_ID, DummyContent.ITEMS.get(0).id);
            ArticleDetailFragment fragment = new ArticleDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.article_detail_container, fragment).commit();
        }
    }

    /**
     * Callback method from {@link ArticleListFragment.Callbacks} indicating
     * that the item with the given ID was selected.
     */
    //TODO
    @Subscribe
    public void onItemSelected(ListItemClickEvent event) {
 
    	String id = event.getId();
    	
          if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            ArticleDetailFragment fragment = new ArticleDetailFragment();
            Bundle arguments = new Bundle();
            arguments.putString(ArticleDetailFragment.ARG_ITEM_ID, id);
            fragment.setArguments(arguments);
            
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.article_detail_container, fragment).commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ArticleDetailActivity.class);
            detailIntent.putExtra(ArticleDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
