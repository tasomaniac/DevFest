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

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.commonsware.cwac.endless.EndlessAdapter;
import com.metrekare.devfest.event.BusProvider;
import com.metrekare.devfest.event.ListItemClickEvent;
import com.netmera.mobile.NetmeraContent;
import com.netmera.mobile.NetmeraException;
import com.netmera.mobile.NetmeraService;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

/**
 * A list fragment representing a list of Articles. This fragment also supports tablet devices by
 * allowing list items to be given an 'activated' state upon selection. This helps indicate which
 * item is currently being viewed in a {@link ArticleDetailFragment}. <p> Activities containing this
 * fragment MUST implement the {@link Callbacks} interface.
 */
public class ArticleListFragment extends SherlockListFragment implements 
		OnRefreshListener{

    /**
     * The serialization (saved instance state) Bundle key representing the activated item position.
     * Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";


    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;
    
    private MyAdapter adapter;
    private List<NetmeraContent> cachedData;
    
    private int pageCount = 0;
    


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
     * screen orientation changes).
     */
    public ArticleListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new MyAdapter(getActivity());
        setListAdapter(new MyEndlessAdapter(getActivity(), adapter));
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState
                    .getInt(STATE_ACTIVATED_POSITION));
        }
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	
        ((ArticleListActivity) getActivity()).attacher.addRefreshableView(getListView(), this);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position,
                                long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        NetmeraContent content = adapter.getItem(position);
        try {
			BusProvider.getInstance().post(new ListItemClickEvent().setId(content.getPath()));
		} catch (NetmeraException e) {
		}
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be given the
     * 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
    
    private class MyEndlessAdapter extends EndlessAdapter {

		public MyEndlessAdapter(Context context, ListAdapter wrapped) {
			super(context, wrapped, R.layout.pending);
		}

		@Override
		protected boolean cacheInBackground() throws Exception {

			NetmeraService netmeraService = new NetmeraService("article");
			netmeraService.setMax(10);
			netmeraService.setPage(pageCount);

			cachedData = netmeraService.search();
			pageCount++;
			
			return cachedData.size() >= 10;
		}

		@Override
		protected void appendCachedData() {

	        ((ArticleListActivity) getActivity()).attacher.setRefreshComplete();
			
			for(NetmeraContent content : cachedData) {
				adapter.add(content);
			}
		}
    	
    }

    private class MyAdapter extends ArrayAdapter<NetmeraContent> {
        

        public MyAdapter(Context context) {
			super(context, android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<NetmeraContent>());
		}

		@Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.list_item_article, container, false);
            }

            try {
	            final NetmeraContent item = (NetmeraContent) getItem(position);
	            ((TextView) convertView.findViewById(R.id.article_title)).setText(item.getString("title"));
	            ((TextView) convertView.findViewById(R.id.article_subtitle)).setText(item.getString("article_date") + " by " + item.getString("author"));
	
	            final ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
	            Picasso.with(getActivity()).load(item.getString("imageUrl")).into(thumbnail);
            } catch(NetmeraException e) {
            	
            }

            return convertView;
        }
    }

	@Override
	public void onRefreshStarted(View view) {
		adapter.clear();
	}

    
}
